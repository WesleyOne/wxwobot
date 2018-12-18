package io.wxwobot.admin.web.wxrob;


import io.wxwobot.admin.itchat4j.api.MessageTools;
import io.wxwobot.admin.itchat4j.beans.BaseMsg;
import io.wxwobot.admin.itchat4j.core.Core;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.web.beans.CoreDefaultConfig;
import io.wxwobot.admin.web.common.MyCache;
import io.wxwobot.admin.web.enums.KeyMsgValueType;
import org.apache.commons.lang3.StringUtils;

/**
 * @author WesleyOne
 * @create 2018/12/11
 */
public class MyMsgHandler implements IMsgHandlerFace,LogInterface {

    private String coreKey;
    private Core core;

    public MyMsgHandler(String coreKey){
        this.coreKey = coreKey;
        this.core = CoreManage.getInstance(coreKey);
    }

    @Override
    public String textMsgHandle(BaseMsg msg) {
        CoreDefaultConfig config = MyCache.getCoreConfig(coreKey);
        if (config.isNoGroup() && msg.isGroupMsg()){
            return null;
        }

        if (config.isNoFriend() && !msg.isGroupMsg()){
            return null;
        }

        // 关键字业务
        String text = msg.getText();
        String answer = config.getKeywords().get(text);
        String fromUserName = msg.getFromUserName();
        if (StringUtils.isNotEmpty(answer)){

            // TODO 以下是为了外接做的测试
          if (answer.startsWith(KeyMsgValueType.IMG.toValue())){
                String filePath = answer.substring(KeyMsgValueType.IMG.toValue().length()).trim();
                MessageTools.sendPicMsgByUserId(fromUserName,filePath,coreKey);
            }else if (answer.startsWith(KeyMsgValueType.FILE.toValue())) {
                String filePath = answer.substring(KeyMsgValueType.FILE.toValue().length()).trim();
                MessageTools.sendFileMsgByUserId(fromUserName, filePath, coreKey);
//            }else if(answer.startsWith(KeyMsgValueType.CASH.toValue())){
//                String cashImgFilePath = config.getCashImg();
//                if (StringUtils.isNotEmpty(cashImgFilePath)){
//                    MessageTools.sendPicMsgByUserId(fromUserName,cashImgFilePath,coreKey);
//                }else{
//                    LOG.warn("未设置收款码：{}",coreKey);
//                }
            }else{
                MessageTools.sendMsgById(answer, fromUserName,coreKey);
            }
        }

        return null;
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
    public void sysMsgHandle(BaseMsg msg) {
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
