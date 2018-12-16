package io.wxwobot.admin.web.controller;

import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.Wechat;
import io.wxwobot.admin.itchat4j.api.WechatTools;
import io.wxwobot.admin.itchat4j.controller.LoginController;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import io.wxwobot.admin.web.base.BaseException;
import io.wxwobot.admin.web.utils.FileUtil;
import io.wxwobot.admin.web.wxrob.MyMsgHandler;

import javax.servlet.ServletOutputStream;
import java.io.*;

/**
 * @author WesleyOne
 * @create 2018/12/16
 */
public class RobotOperationController extends _BaseController {

    /**
     * 获取状态
     * @throws BaseException
     */
    public void getStatus() throws BaseException {
        String coreKey = getCoreKey();
        boolean wechatStatus = WechatTools.getWechatStatus(coreKey);
        setData(wechatStatus);
        renderJson();
    }

    /**
     * 登录第一步，获取二维码
     * @throws BaseException
     */
    public void getQr() throws BaseException, IOException {
        String coreKey = getCoreKey();
        String realUploadPath = PropKit.get("realUploadPath");
        //先清除下旧二维码
        FileUtil.delFile(realUploadPath,"QR.jpg");

        String qrPath = realUploadPath;
        IMsgHandlerFace msgHandler = new MyMsgHandler(coreKey);
        Wechat wechat = new Wechat(msgHandler, qrPath, true,coreKey);
        LoginController login = new LoginController(coreKey);
        login.login_1(qrPath);

        // TODO 两次流处理,需要优化

        File file = new File(realUploadPath+ File.separator +"QR.jpg");
        if (file.exists()&&file.isFile()) {
            InputStream inputStream = new FileInputStream(file);
            ServletOutputStream outputStream = getResponse().getOutputStream();
            int length = -1;
            byte[] buffer = new byte[1024];
            while ((length=inputStream.read(buffer, 0, 1024))!=-1) {
                outputStream.write(buffer,0,length);
            }

            inputStream.close();
            outputStream.flush();
            outputStream.close();
        }
    }


    /**
     * 登录第二步，确认登录及初始化信息
     * @throws BaseException
     */
    public void init() throws BaseException {
        String coreKey = getCoreKey();
        IMsgHandlerFace msgHandler = new MyMsgHandler(coreKey);
        Wechat wechat = new Wechat(msgHandler,coreKey);
        wechat.wxInit();
        wechat.start();
        setMsg("登录成功");
        renderJson();
    }

    /**
     * 退出
     * @throws BaseException
     */
    public void logout() throws BaseException {
        String coreKey = getCoreKey();
        WechatTools.logout(coreKey);
        renderJson();
    }
}
