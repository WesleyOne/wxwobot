package io.wxwobot.admin.itchat4j.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.beans.SendMsg;
import io.wxwobot.admin.itchat4j.client.HttpClientManage;
import io.wxwobot.admin.itchat4j.controller.LoginController;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.enums.SendMsgType;
import io.wxwobot.admin.itchat4j.utils.tools.CommonTools;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.*;
import java.util.*;

/**
 * 多开管理
 * @author WesleyOne
 * @create 2018/12/13
 */
public class CoreManage implements LogInterface {

    static int MAX_CORE_NUM = 50;
    private static HashMap<String,Core> coreMap = new HashMap<>(MAX_CORE_NUM/3*4+1);

    public static boolean USE_HOT_RELOAD = PropKit.use("appConfig.properties").getBoolean("useHotReload",false);
    public static String HOT_RELOAD_DIR = PropKit.use("appConfig.properties").get("hotReloadDir")+"/wxwobot.hot";

    public static Core getInstance(String uniqueKey) {
        if (StringUtils.isEmpty(uniqueKey)){
            return null;
        }
        Core core;
        if ( !coreMap.containsKey(uniqueKey) || coreMap.get(uniqueKey) == null ){
            core = Core.getInstance(uniqueKey);
            coreMap.put(uniqueKey, core);
        }
        return coreMap.get(uniqueKey);
    }

    /**
     * 移除
     * @param uniqueKey
     */
    public static void remove(String uniqueKey){
        if (coreMap.containsKey(uniqueKey)){
            coreMap.remove(uniqueKey);
        }
    }

    /**
     * 查询是否在线
     * @param uniqueKey
     * @return
     */
    public static boolean isActive(String uniqueKey) {
        if (StringUtils.isNotEmpty(uniqueKey) && coreMap.containsKey(uniqueKey) && coreMap.get(uniqueKey).isAlive()){
            return true;
        }
        return false;
    }

    /**
     * 持久化
     */
    public static void persistence(){

        // 格式化数据
        Collection<Core> valueCollection = coreMap.values();
        int size = valueCollection.size();
        // 没有数据不操作
        if (size<=0){
            return;
        }
        LOG.info("登录数据持久化中");
        Iterator<Core> iterator = valueCollection.iterator();
        JSONArray jsonArray = new JSONArray();
        while (iterator.hasNext()){
            Core core = iterator.next();
            if (core.isAlive()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("core",core);
                jsonObject.put("cookies",core.getMyHttpClient().getCookieStore().getCookies());
                jsonArray.add(jsonObject);
            }
        }

        try {
            File file =new File(HOT_RELOAD_DIR);
            if (!file.exists()){
                file.createNewFile();
            }
            // 每次覆盖
            FileWriter fileWritter = new FileWriter(HOT_RELOAD_DIR,false);
            fileWritter.write(jsonArray.toJSONString());
            fileWritter.close();

            LOG.info("登录数据持久化完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动加载持久化文件
     */
    public static void reload(){
        if (USE_HOT_RELOAD){
            File file =new File(HOT_RELOAD_DIR);
            if (file.exists()){
                LOG.info("登录数据热加载中");
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    FileReader fr = new FileReader(HOT_RELOAD_DIR);
                    BufferedReader bf = new BufferedReader(fr);
                    String str;
                    // 按行读取字符串
                    while ((str = bf.readLine()) != null) {
                        stringBuilder.append(str);
                    }
                    bf.close();
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                String result = stringBuilder.toString();
                if (StringUtils.isEmpty(result)){
                    return;
                }

                JSONArray jsonArray = JSONArray.parseArray(result);
                int size = jsonArray.size();
                if (size > 0){
                    // 封装成线程操作
                    for (int i=0;i<size;i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ReloadThread reloadThread = new ReloadThread(jsonObject);
                        Thread thread = new Thread(reloadThread);
                        thread.start();
                    }
                }

                LOG.info("登录数据热加载完成");
            }
        }
    }

    private static class ReloadThread implements Runnable{

        private JSONObject reloadObject;

        public ReloadThread(JSONObject object){
            this.reloadObject = object;
        }

        @Override
        public void run() {
            Core core = null;
            try {
                /**
                 * 初始化Core,
                 * 1.获取登入的状态信息并装入CoreManage
                 * 2.构建ThreadGroup
                 * 3.获取Cookies并装入HttpClientManage
                 * 4.获取信息及启动线程
                 */
                JSONObject jsonObject = this.reloadObject;
                core = jsonObject.getObject("core",Core.class);
                String uniqueKey = core.getUniqueKey();
                if (core.isAlive()){
                    core.setThreadGroup(new ThreadGroup(uniqueKey));
                    coreMap.put(uniqueKey,core);

                    JSONArray cookiesJsonArray = jsonObject.getJSONArray("cookies");
                    int arraySize = cookiesJsonArray.size();
                    if (arraySize<=0){
                        return;
                    }

                    // 装载原cookie信息,json解析cookie异常，干脆手动封装
                    BasicCookieStore cookieStore = new BasicCookieStore();
                    for (int ci=0;ci<arraySize;ci++){
                        JSONObject cookieJson = cookiesJsonArray.getJSONObject(ci);
                        String name = cookieJson.getString("name");
                        String value = cookieJson.getString("value");
                        String domain = cookieJson.getString("domain");
                        String path = cookieJson.getString("path");
                        Boolean persistent = cookieJson.getBoolean("persistent");
                        Boolean secure = cookieJson.getBoolean("secure");
                        Long expiryDate = cookieJson.getLong("expiryDate");
                        Integer version = cookieJson.getInteger("version");

                        BasicClientCookie cookie = new BasicClientCookie(name,value);
                        cookie.setDomain(domain);
                        cookie.setPath(path);
                        cookie.setSecure(secure);
                        cookie.setExpiryDate(new Date(expiryDate));
                        cookie.setVersion(version);

                        cookieStore.addCookie(cookie);
                    }
                    // 必须在构建client时就放入cookie
                    HttpClientManage.getInstance(uniqueKey,cookieStore);
                    //装载core信息及启动线程
                    LoginController login = new LoginController(uniqueKey);
                    if (!login.login_3()){
                        // 加载失败退出
                        core.setAlive(false);
                        return;
                    }
                    LOG.info("热登录成功: {}", uniqueKey);
                }
            }catch (Exception e){
                e.printStackTrace();
                if (core != null){
                    core.setAlive(false);
                    core = null;
                }
            }
        }
    }


    /**
     * 存放新的群,昵称emoji处理
     * @param core
     * @param jsonObject
     */
    public static void addNewGroup(Core core,JSONObject jsonObject){
        String userName = jsonObject.getString("UserName");

        CommonTools.emojiFormatter2(jsonObject, "NickName");
        // 删除重复的
        core.getGroupList().removeIf(group->userName.equals(group.getString("UserName")));

        core.getGroupList().add(jsonObject);
        core.getGroupInfoMap().put(jsonObject.getString("NickName"), jsonObject);
        core.getGroupInfoMap().put(userName, jsonObject);
    }



    /**
     * 存放新的联系人,昵称emoji处理
     * @param core
     * @param jsonObject
     */
    public static void addNewContact(Core core,JSONObject jsonObject){
        String userName = jsonObject.getString("UserName");

        CommonTools.emojiFormatter2(jsonObject, "NickName");
        // 删除重复的
        core.getContactList().removeIf(contact->userName.equals(contact.getString("UserName")));

        core.getContactList().add(jsonObject);
        core.getUserInfoMap().put(jsonObject.getString("NickName"), jsonObject);
        core.getUserInfoMap().put(userName, jsonObject);
    }

    /**
     * 消息统一加到队列里处理
     * @param uniqueKey
     * @param toUserName
     * @param data
     * @param type
     */
    public static void addSendMsg(String uniqueKey, String toUserName, String toNickName,String data, SendMsgType type){
        SendMsg sendMsg = new SendMsg();
        sendMsg.setUserName(toUserName);
        sendMsg.setNickName(toNickName);
        sendMsg.setMessage(data);
        sendMsg.setMsgType(type);
        boolean isGroup = false;
        if (toUserName.startsWith("@@")){
            isGroup = true;
        }
        sendMsg.setGroup(isGroup);
        CoreManage.getInstance(uniqueKey).getSendList().add(sendMsg);
    }

}
