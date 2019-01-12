package io.wxwobot.admin.web.msghandlers;


import io.wxwobot.admin.itchat4j.beans.BaseMsg;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.enums.SendMsgType;
import io.wxwobot.admin.itchat4j.utils.tools.CommonTools;
import io.wxwobot.admin.web.constant.ConfigKeys;
import io.wxwobot.admin.web.model.WxRobConfig;
import io.wxwobot.admin.web.model.WxRobKeyword;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;

/**
 * 消息处理实现 默认方案
 * @author WesleyOne
 * @create 2018/12/11
 */
public class MyMsgHandler implements IMsgHandlerFace,LogInterface {

    private String uniqueKey;

    public MyMsgHandler(String uniqueKey){
        this.uniqueKey = uniqueKey;
    }

    @Override
    public void textMsgHandle(BaseMsg msg) {

        String text = msg.getText();
        String fromNickName = msg.getFromNickName();
        String fromUserName = msg.getFromUserName();
        boolean groupMsg = msg.isGroupMsg();
        /**
         * 规则
         * 1.查看机器人配置开关
         * 2.优先昵称关键字，没有则查看默认全局关键字
         * 3.同个关键字只发最新创建的
         */

        WxRobConfig robConfig = WxRobConfig.dao.findFirst("SELECT * FROM wx_rob_config WHERE unique_key = ? LIMIT 1", uniqueKey);
        if(robConfig != null && robConfig.getEnable()){
            // 判断是否要回复
            boolean isOpen = false;
            // 判断是群聊的话是否允许回复 昵称关键字
            if (robConfig.getToGroup() && groupMsg){
                isOpen = true;
            }
            // 判断不是群聊的话是否允许回复 昵称关键字
            if (!isOpen && robConfig.getToFriend() && !groupMsg){
                isOpen = true;
            }
            if (isOpen){
                WxRobKeyword robKeyword = WxRobKeyword.dao.findFirst("SELECT * FROM wx_rob_keyword WHERE unique_key = ? AND key_data = ? AND nick_name = ? AND enable = 1 AND to_group = ? ORDER BY id DESC LIMIT 1", uniqueKey, text,fromNickName,msg.isGroupMsg()?1:0);
                if (sendDataByType(fromUserName, robKeyword,groupMsg)) {
                    return;
                }
            }

            // 没有昵称关键字，则使用默认关键字
            isOpen = false;
            // 判断是群聊的话是否允许回复 昵称关键字
            if (robConfig.getDefaultGroup() && groupMsg){
                isOpen = true;
            }
            // 判断不是群聊的话是否允许回复 昵称关键字
            if (!isOpen && robConfig.getDefaultFriend() && !groupMsg){
                isOpen = true;
            }

            if (isOpen){
                WxRobKeyword defaultRobKeyword = WxRobKeyword.dao.findFirst("SELECT * FROM wx_rob_keyword WHERE unique_key = ? AND key_data = ? AND nick_name = ? AND enable = 1 AND to_group = ? ORDER BY id DESC LIMIT 1", uniqueKey, text,ConfigKeys.DEAFAULT_KEYWORD,msg.isGroupMsg()?1:0);
                if (sendDataByType(fromUserName, defaultRobKeyword, groupMsg)) {
                    return;
                }
            }
        }
        return;
    }

    private boolean sendDataByType(String fromUserName, WxRobKeyword robKeyword, boolean isGroup) {
        String data;
        String type;
        if (robKeyword != null){
            data = robKeyword.getValueData();
            type = robKeyword.getTypeData();
            CoreManage.addSendMsg(uniqueKey,fromUserName,data,SendMsgType.fromValue(type),isGroup);
            return true;
        }
        return false;
    }


    @Override
    public void sysMsgHandle(BaseMsg msg) {


        String fromNickName = msg.getFromNickName();
        String fromUserName = msg.getFromUserName();
        boolean groupMsg = msg.isGroupMsg();
        /**
         * 群里的新人进群消息处理
         * 优先发专门这个群的欢迎词
         * 没有发通用的
         * 欢迎词内容实质就是在最前面加上@昵称\n
         *
         * 欢迎词的关键字
         * @see ConfigKeys#DEAFAULT_WELCOME
         */

        // 解析新人名字
        String text = msg.getContent();
        String newNickName = "";
        Matcher matcher = CommonTools.getMatcher("邀请\"(.+?)\"加入了群聊", text);
        if (matcher.find()){
            newNickName = matcher.group(1);
        }else{
            matcher = CommonTools.getMatcher("\"(.+?)\"通过扫描(.+?)分享的二维码加入群聊", text);
            if (matcher.find()){
                newNickName = matcher.group(1);
            }
        }

        if (StringUtils.isNotEmpty(newNickName)){

            WxRobConfig robConfig = WxRobConfig.dao.findFirst("SELECT * FROM wx_rob_config WHERE unique_key = ? LIMIT 1", uniqueKey);
            if(robConfig != null && robConfig.getEnable()){
                // 判断是否要回复
                boolean isOpen = false;
                // 判断是群聊的话是否允许回复 昵称关键字
                if (robConfig.getToGroup() && groupMsg){
                    isOpen = true;
                }
                if (isOpen){
                    WxRobKeyword robKeyword = WxRobKeyword.dao.findFirst("SELECT * FROM wx_rob_keyword WHERE unique_key = ? AND key_data = ? AND nick_name = ? AND enable = 1 AND to_group = ? ORDER BY id DESC LIMIT 1", uniqueKey, ConfigKeys.DEAFAULT_WELCOME,fromNickName,msg.isGroupMsg()?1:0);
                    if (sendSysWelcomeMsg(fromUserName, newNickName, robKeyword, groupMsg)){ return;}
                }

                // 没有专门的关键字，则使用默认关键字
                isOpen = false;
                // 判断是群聊的话是否允许回复 昵称关键字
                if (robConfig.getDefaultGroup() && groupMsg){
                    isOpen = true;
                }
                if (isOpen){
                    WxRobKeyword defaultRobKeyword = WxRobKeyword.dao.findFirst("SELECT * FROM wx_rob_keyword WHERE unique_key = ? AND key_data = ? AND nick_name = ? AND enable = 1 AND to_group = ? ORDER BY id DESC LIMIT 1", uniqueKey, ConfigKeys.DEAFAULT_WELCOME, ConfigKeys.DEAFAULT_KEYWORD,msg.isGroupMsg()?1:0);
                    if (sendSysWelcomeMsg(fromUserName, newNickName, defaultRobKeyword, groupMsg)){ return;}
                }
            }
        }

    }

    /**
     * 发送欢迎内容
     * @param fromUserName
     * @param newNickName
     * @param robKeyword
     * @return
     */
    private boolean sendSysWelcomeMsg(String fromUserName, String newNickName, WxRobKeyword robKeyword, boolean isGroup) {
        if (robKeyword != null){
            if (robKeyword.getTypeData().equals(SendMsgType.TEXT.toValue())){
                robKeyword.setValueData(String.format("@%s\n%s",newNickName,robKeyword.getValueData()));
            }
            if (sendDataByType(fromUserName, robKeyword,isGroup)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void picMsgHandle(BaseMsg msg) {
        return ;

//        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".jpg"; // 这里使用收到图片的时间作为文件名
//        String picPath = "D://cn.zhouyafeng.itchat4j/pic" + File.separator + fileName; // 保存图片的路径
//        DownloadTools.getDownloadFn(msg, MsgTypeEnum.PIC.getType(), picPath); // 调用此方法来保存图片
//        CoreManage.addSendMsg(uniqueKey,msg.getFromUserName(),"图片保存成功",SendMsgType.TEXT,msg.isGroupMsg());
//        return;
    }

    @Override
    public void voiceMsgHandle(BaseMsg msg) {
        return ;
//        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp3"; // 这里使用收到语音的时间作为文件名
//        String voicePath = "D://cn.zhouyafeng.itchat4j/voice" + File.separator + fileName; // 保存语音的路径
//        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VOICE.getType(), voicePath); // 调用此方法来保存语音
//        CoreManage.addSendMsg(uniqueKey,msg.getFromUserName(),"声音保存成功",SendMsgType.TEXT,msg.isGroupMsg());
//        return;
    }

    @Override
    public void videoMsgHandle(BaseMsg msg) {
        return;

//        String fileName = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date()) + ".mp4"; // 这里使用收到小视频的时间作为文件名
//        String viedoPath = "D://cn.zhouyafeng.itchat4j/video" + File.separator + fileName;// 保存小视频的路径
//        DownloadTools.getDownloadFn(msg, MsgTypeEnum.VIEDO.getType(), viedoPath);// 调用此方法来保存小视频
//        CoreManage.addSendMsg(uniqueKey,msg.getFromUserName(),"视频保存成功",SendMsgType.TEXT,msg.isGroupMsg());
//        return;
    }

    @Override
    public void nameCardMsgHandle(BaseMsg msg) {
        return ;
    }

    @Override
    public void verifyAddFriendMsgHandle(BaseMsg msg) {
        return ;
    }

    @Override
    public void mediaMsgHandle(BaseMsg msg) {
        return ;
    }
}
