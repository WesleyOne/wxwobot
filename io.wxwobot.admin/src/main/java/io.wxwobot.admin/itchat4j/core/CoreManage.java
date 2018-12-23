package io.wxwobot.admin.itchat4j.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.api.WechatTools;
import io.wxwobot.admin.itchat4j.client.HttpClientManage;
import io.wxwobot.admin.itchat4j.client.SingleHttpClient;
import io.wxwobot.admin.itchat4j.service.ILoginService;
import io.wxwobot.admin.itchat4j.service.impl.LoginServiceImpl;
import io.wxwobot.admin.itchat4j.thread.CheckLoginStatusThread;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.tools.CommonTools;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.BasicClientCookie2;

import java.io.*;
import java.util.*;

/**
 * 多开管理
 * @author WesleyOne
 * @create 2018/12/13
 */
public class CoreManage implements LogInterface {

    public static HashMap<String,Core> coreMap = new HashMap<>(32);

    public static boolean USE_HOT_RELOAD = PropKit.use("appConfig.properties").getBoolean("useHotReload",false);
    public static String HOT_RELOAD_DIR = PropKit.use("appConfig.properties").get("hotReloadDir")+"/wxwobot.pkl";

    public static Core getInstance(String uniqueKey) {
        if (StringUtils.isEmpty(uniqueKey)){
            return null;
        }
        Core core;
        if (coreMap.containsKey(uniqueKey) && coreMap.get(uniqueKey) != null ){
            core = coreMap.get(uniqueKey);
        }else {
            core = Core.getInstance(uniqueKey);
            coreMap.put(uniqueKey, core);
        }
        return core;
    }

    /**
     * 查询是否在线
     * @param uniqueKey
     * @return
     */
    public static boolean isActive(String uniqueKey) {
        if (StringUtils.isEmpty(uniqueKey)){
            return false;
        }
        if (coreMap.containsKey(uniqueKey) && coreMap.get(uniqueKey).isAlive()){
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
            System.out.println(HOT_RELOAD_DIR);
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
                        // TODO 封装成线程同时操作
                        for (int i=0;i<size;i++){
                            Core core = null;
                            try {

                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                core = jsonObject.getObject("core",Core.class);
                                long lastNormalRetcodeTime = core.getLastNormalRetcodeTime();
//                                if ( System.currentTimeMillis() -lastNormalRetcodeTime > 1000 * 180){
//                                    core.setAlive(false);
//                                    continue;
//                                }
                                if (core.isAlive()){
                                    String uniqueKey = core.getUniqueKey();
                                    CoreManage.coreMap.put(uniqueKey,core);
                                    JSONArray cookiesJsonArray = jsonObject.getJSONArray("cookies");
                                    int arraySize = cookiesJsonArray.size();
                                    if (arraySize<=0){
                                        continue;
                                    }

                                    // 装载原cookie信息
                                    BasicCookieStore cookieStore = new BasicCookieStore();
                                    // json解析cookie异常，干脆手动封装
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
                                    // 启动线程
                                    ILoginService loginService = new LoginServiceImpl(uniqueKey);

                                    LOG.info("5. 登陆成功，微信初始化");
                                    if (!loginService.webWxInit()) {
                                        LOG.info("6. 微信初始化异常");
                                        core.setAlive(false);
                                        continue;
                                    }

                                    LOG.info("6. 开启微信状态通知");
                                    loginService.wxStatusNotify();

                                    LOG.info(String.format("欢迎回来， %s", core.getNickName()));

                                    LOG.info("8. 开始接收消息");
                                    loginService.startReceiving();

                                    LOG.info("9. 获取联系人信息");
                                    loginService.webWxGetContact();

                                    LOG.info("10. 获取群好友及群好友列表");
                                    loginService.WebWxBatchGetContact();

                                    LOG.info("11. 缓存本次登陆好友相关消息");
                                    // 登陆成功后缓存本次登陆好友相关消息（NickName, UserName）
                                    WechatTools.setUserInfo(uniqueKey);

                                    LOG.info("12.开启微信状态检测线程");
                                    Thread thread = new Thread(new CheckLoginStatusThread(uniqueKey));
                                    thread.setName("WXROB-STATUS-"+uniqueKey);
                                    thread.start();

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                                if (core != null){
                                    core.setAlive(false);
                                }
                            }

                        }
                    }

                }
            LOG.info("登录数据热加载完成");
        }
    }


    /**
     * 存放新的群,昵称emoji处理
     * @param core
     * @param jsonObject
     */
    public static void addNewGroup(Core core,JSONObject jsonObject){
        String nickName = jsonObject.getString("NickName");
        String userName = jsonObject.getString("UserName");
        if (nickName.contains("<span class=\"emoji emoji")){
            CommonTools.emojiFormatter(jsonObject, "NickName");
        }

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
        String nickName = jsonObject.getString("NickName");
        String userName = jsonObject.getString("UserName");
        if (nickName.contains("<span class=\"emoji emoji")){
            CommonTools.emojiFormatter(jsonObject, "NickName");
        }
        // 删除重复的
        core.getContactList().removeIf(contact->userName.equals(contact.getString("UserName")));

        core.getContactList().add(jsonObject);
        core.getUserInfoMap().put(jsonObject.getString("NickName"), jsonObject);
        core.getUserInfoMap().put(userName, jsonObject);
    }

}
