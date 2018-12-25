package io.wxwobot.admin.web.wxrob;


import com.alibaba.fastjson.JSON;
import io.wxwobot.admin.itchat4j.api.MessageTools;
import io.wxwobot.admin.itchat4j.beans.BaseMsg;
import io.wxwobot.admin.itchat4j.core.Core;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.tools.CommonTools;
import io.wxwobot.admin.web.constant.ConfigKeys;
import io.wxwobot.admin.web.model.WxRobConfig;
import io.wxwobot.admin.web.model.WxRobKeyword;

import java.lang.reflect.Type;
import java.util.regex.Matcher;

/**
 * @author WesleyOne
 * @create 2018/12/11
 */
public class MyMsgHandler implements IMsgHandlerFace,LogInterface {

    private String uniqueKey;
    private Core core;

    public MyMsgHandler(String uniqueKey){
        this.uniqueKey = uniqueKey;
        this.core = CoreManage.getInstance(uniqueKey);
    }

    @Override
    public String textMsgHandle(BaseMsg msg) {

        String text = msg.getText();
        String fromNickName = msg.getFromNickName();
        String fromUserName = msg.getFromUserName();
        /**
         * 规则
         * 1.查看机器人配置开关
         * 2.优先昵称关键字，没有则查看默认全局关键字
         * 3.同个关键字只发最新创建的
         */

        WxRobConfig robConfig = WxRobConfig.dao.findFirst("SELECT TOP 1 * FROM wx_rob_config WHERE unique_key = ?", uniqueKey);
        if(robConfig != null && robConfig.getEnable()){
            // 判断是否要回复
            boolean isOpen = false;
            // 判断是群聊的话是否允许回复 昵称关键字
            if (robConfig.getToGroup() && msg.isGroupMsg()){
                isOpen = true;
            }
            // 判断不是群聊的话是否允许回复 昵称关键字
            if (!isOpen && robConfig.getToFriend() && !msg.isGroupMsg()){
                isOpen = true;
            }
            if (isOpen){
                WxRobKeyword robKeyword = WxRobKeyword.dao.findFirst("SELECT TOP 1 * FROM wx_rob_keyword WHERE unique_key = ? AND key_data = ? AND nick_name = ? AND enable = 1 AND to_group = ? ORDER BY id DESC", uniqueKey, text,fromNickName,msg.isGroupMsg()?1:0);
                if (sendDataByType(fromUserName, robKeyword)) {
                    return null;
                }
            }

            // 没有昵称关键字，则使用默认关键字
            isOpen = false;
            // 判断是群聊的话是否允许回复 昵称关键字
            if (robConfig.getDefaultGroup() && msg.isGroupMsg()){
                isOpen = true;
            }
            // 判断不是群聊的话是否允许回复 昵称关键字
            if (!isOpen && robConfig.getDefaultFriend() && !msg.isGroupMsg()){
                isOpen = true;
            }

            if (isOpen){
                WxRobKeyword defaultRobKeyword = WxRobKeyword.dao.findFirst("SELECT TOP 1 * FROM wx_rob_keyword WHERE unique_key = ? AND key_data = ? AND nick_name = ? AND enable = 1 AND to_group = ? ORDER BY id DESC", uniqueKey, text,ConfigKeys.DEAFAULT_KEYWORD,msg.isGroupMsg()?1:0);
                if (sendDataByType(fromUserName, defaultRobKeyword)) {
                    return null;
                }
            }
        }
        return null;
    }

    private boolean sendDataByType(String fromUserName, WxRobKeyword robKeyword) {
        String data;
        String type;
        if (robKeyword != null){
            data = robKeyword.getValueData();
            type = robKeyword.getTypeData();
            MessageTools.send(fromUserName,uniqueKey,data,type);
            return true;
        }
        return false;
    }


    @Override
    public void sysMsgHandle(BaseMsg msg) {

        /**
         * 群里的系统新人进群消息处理
         */
        if (msg.isGroupMsg()){
            WxRobConfig robConfig = WxRobConfig.dao.findFirst("SELECT TOP 1 * FROM wx_rob_config WHERE unique_key = ?", uniqueKey);
            if(robConfig != null && robConfig.getEnable()){
                // 判断是群聊默认回复是否开启
                if (robConfig.getDefaultGroup()){

                    // 新人进群欢迎
                    String text = msg.getContent();
                    Matcher matcher = CommonTools.getMatcher("邀请\"(.+?)\"加入了群聊", text);
                    if (matcher.find()){
                        String group = matcher.group(1);
                        MessageTools.sendMsgById("@"+group+"\n欢迎进入本群",msg.getFromUserName(),uniqueKey);
                    }
                }
            }
        }

    }

    @Override
    public String picMsgHandle(BaseMsg msg) {
        return null;
//        if (NO_GROUP && msg.isGroupMsg()){
//            return null;
//        }
//        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg"; // 这里使用收到图片的时间作为文件名
//        String picPath = "D://cn.zhouyafeng.itchat4j/pic" + File.separator + fileName; // 保存图片的路径
//        DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 调用此方法来保存图片
//        return "图片保存成功";
    }

    @Override
    public String voiceMsgHandle(BaseMsg msg) {
        return null;
//        if (NO_GROUP && msg.isGroupMsg()){
//            return null;
//        }
//        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp3"; // 这里使用收到语音的时间作为文件名
//        String voicePath = "D://cn.zhouyafeng.itchat4j/voice" + File.separator + fileName; // 保存语音的路径
//        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath); // 调用此方法来保存语音
//        return "声音保存成功";
    }

    @Override
    public String videoMsgHandle(BaseMsg msg) {
        return null;

//        if (NO_GROUP && msg.isGroupMsg()){
//            return null;
//        }
//        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp4"; // 这里使用收到小视频的时间作为文件名
//        String viedoPath = "D://cn.zhouyafeng.itchat4j/video" + File.separator + fileName;// 保存小视频的路径
//        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);// 调用此方法来保存小视频
//        return "视频保存成功";
    }

    @Override
    public String nameCardMsgHandle(BaseMsg msg) {
        return null;
    }

    @Override
    public String verifyAddFriendMsgHandle(BaseMsg msg) {
        return null;
    }

    @Override
    public String mediaMsgHandle(BaseMsg msg) {
        return null;
    }
}
