package io.wxwobot.admin.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.api.MessageTools;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.utils.SleepUtils;
import io.wxwobot.admin.web.enums.KeyMsgValueType;
import io.wxwobot.admin.web.model.WxRobRelation;
import io.wxwobot.admin.web.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

/**
 * 对外接口
 * @author WesleyOne
 * @create 2018/12/16
 */
public class ExtendController extends _BaseController {


    /**
     * 对外通用规则发消息
     * 参数:
     * ok       外接唯一码
     * msg     消息列表
     *         类型参考@see     io.wxwobot.admin.web.enums.KeyMsgValueType
     *          TEXT     文本消息串
     *          IMG      图片名串（需要后台上传获取）
     *          FILE     文件名串（需要后台上传获取）
     *  例子:
     *  {"ok":"test123",
     *   "msg":[
     *       {"type":"TEXT","body":"我是消息体"},
     *       {"type":"IMG","body":"ty6yLk3X_1545142908614.jpg"},
     *       {"type":"FILE","body":"ty6yLk3X_1545142537914.txt"},
     *   ]
     *  }
     *
     *  说明:
     *      发送顺序按照列表顺序从前往后发
     *
     * 返回:
     * 00   成功
     * 01   外接码不存在
     * 02   外接码失效
     * 03   IP未通过审核
     *
     */
    public void sendMsg(){

        JSONObject postParam = getPostParam();
        String outKey = postParam.getString("ok");
        String msgStr = postParam.getString("msg");
        JSONArray msgs = JSONArray.parseArray(msgStr);

        if (StringUtils.isEmpty(outKey)){
            setCode("01");
            setMsg("外接码不存在");
            renderJson();
            return;
        }

        WxRobRelation relationRecord = WxRobRelation.dao.findFirst("SELECT TOP 1 * FROM wx_rob_relation with(nolock) WHERE out_key = ? ", outKey);

        /**
         * 校验IP
         * 1.配置空则拒绝所有
         * 2.存在0.0.0.0不校验
         */
        String whiteList = relationRecord.getWhiteList();
        if (StringUtils.isEmpty(whiteList) || !relationRecord.getEnable()){
            setCode("02");
            setMsg("外接码失效或未配置白名单");
            renderJson();
            return;
        }
        String allPassIp = "0.0.0.0";
        // 不是完全开放IP并且访问IP不存在
        if (!whiteList.contains(allPassIp)){
            String outRealIp = IpUtil.getRealIp(getRequest());
            if (!whiteList.contains(outRealIp)){
                setCode("03");
                setMsg("IP未通过审核");
                renderJson();
                return;
            }
        }

        /**
         * 限制单次消息数量,防封,发送消息短暂间隔
         * 优先发文本，其次图片，最后文件
         *
         * 多条消息发送看最后一条成功与否
         */
        String uniqueKey = relationRecord.getUniqueKey();
        // 查看机器是否加载完成
        if (!CoreManage.getInstance(uniqueKey).isAlive() || !CoreManage.getInstance(uniqueKey).isFinishInit()){
            setCode("05");
            setMsg("机器未准备完成");
            renderJson();
            return;
        }

        Boolean toGroup = relationRecord.getToGroup();
        String nickName = relationRecord.getNickName();
        String realImgUploadPath = PropKit.use("appConfig.properties").get("realImgUploadPath")+ File.separator;
        String realFileUploadPath = PropKit.use("appConfig.properties").get("realFileUploadPath")+ File.separator;
        // 单次请求最大消息数
        int maxMessages = 5;
        int msgLength = msgs.size();
        if (msgLength<maxMessages){
            maxMessages = msgLength;
        }
        boolean result = false;
        for (int i=0;i<maxMessages;i++){
            JSONObject message = msgs.getJSONObject(i);
            String type = message.getString("type");
            String body = message.getString("body");

            if (KeyMsgValueType.TEXT.toValue().equals(type)){
                result = MessageTools.sendMsgByNickNameApi(body, nickName, uniqueKey, toGroup);
            }else if (KeyMsgValueType.IMG.toValue().equals(type)){
                result = MessageTools.sendPicMsgByNickNameApi(nickName, realImgUploadPath + body, uniqueKey, toGroup);
            }else if (KeyMsgValueType.FILE.toValue().equals(type)){
                result = MessageTools.sendFileMsgByNickNameApi(nickName,realFileUploadPath+body,uniqueKey,toGroup);
            }
            SleepUtils.sleep(500);
        }

        if (!result){
            setOperateErr();
        }

        renderJson();
    }

//    public void ss(){
//
//        String id = getPara("id");
//        String uniqueKey = getPara("uk");
//        Integer tid = getParaToInt("tid");
//
//        String title = "你好";
//        String desc = "描述";
//        String clickUrl = "https://m.xy7878.com/ff9806d4bcd1ef55/";
//        String viewUrl = "http://images2.fengying78.com/logo/fkhh.jpg";
//        String content = String.format("<msg><appmsg appid=\"\" sdkver=\"0\"><title>%s</title><des>%s</des><action>view</action><type>5</type><showtype>0</showtype><soundtype>0</soundtype><mediatagname></mediatagname><messageext></messageext><messageaction></messageaction><content></content><contentattr>0</contentattr><url>%s</url><lowurl></lowurl><dataurl></dataurl><lowdataurl></lowdataurl><appattach><totallen>0</totallen><attachid></attachid><emoticonmd5></emoticonmd5><fileext></fileext><cdnthumbaeskey></cdnthumbaeskey><aeskey></aeskey></appattach><extinfo></extinfo><sourceusername></sourceusername><sourcedisplayname></sourcedisplayname><thumburl>%s</thumburl><md5></md5><statextstr></statextstr></appmsg><fromusername></fromusername><scene>0</scene><appinfo><version>1</version><appname></appname></appinfo><commenturl></commenturl></msg>",
//                title, desc, clickUrl, viewUrl);
//
//        MessageTools.webWxSendMsg(tid,content,id,uniqueKey);
//        renderJson();
//    }

}
