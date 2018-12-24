package io.wxwobot.admin.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.api.MessageTools;
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
         */
        Boolean toGroup = relationRecord.getToGroup();
        String uniqueKey = relationRecord.getUniqueKey();
        String nickName = relationRecord.getNickName();
        String realImgUploadPath = PropKit.use("appConfig.properties").get("realImgUploadPath")+ File.separator;
        String realFileUploadPath = PropKit.use("appConfig.properties").get("realFileUploadPath")+ File.separator;
        // 单次请求最大消息数
        int maxMessages = 5;
        int msgLength = msgs.size();
        if (msgLength<maxMessages){
            maxMessages = msgLength;
        }
        for (int i=0;i<maxMessages;i++){
            JSONObject message = msgs.getJSONObject(i);
            String type = message.getString("type");
            String body = message.getString("body");

            if (KeyMsgValueType.TEXT.toValue().equals(type)){
                MessageTools.sendMsgByNickNameApi(body,nickName,uniqueKey,toGroup);
            }else if (KeyMsgValueType.IMG.toValue().equals(type)){
                MessageTools.sendPicMsgByNickNameApi(nickName,realImgUploadPath+body,uniqueKey,toGroup);
            }else if (KeyMsgValueType.FILE.toValue().equals(type)){
                MessageTools.sendFileMsgByNickNameApi(nickName,realFileUploadPath+body,uniqueKey,toGroup);
            }
            SleepUtils.sleep(200);
        }

        renderJson();
    }

}
