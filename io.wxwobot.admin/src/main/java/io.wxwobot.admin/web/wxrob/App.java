package io.wxwobot.admin.web.wxrob;

import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.Wechat;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;

/**
 * @author WesleyOne
 * @create 2018/12/11
 */
public class App {

    public static void main(String[] args) {

        String qrPath = PropKit.use("appConfig.properties").get("realUploadPath");
        String coreKey = "24_100_1";
        IMsgHandlerFace msgHandler = new MyMsgHandler(coreKey); // 实现IMsgHandlerFace接口的类
        Wechat wechat = new Wechat(msgHandler, qrPath,coreKey); // 【注入】
        wechat.start();
    }


}
