package io.wxwobot.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.Wechat;
import io.wxwobot.admin.itchat4j.api.WechatTools;
import io.wxwobot.admin.itchat4j.controller.LoginController;
import io.wxwobot.admin.itchat4j.core.Core;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import io.wxwobot.admin.itchat4j.utils.SleepUtils;
import io.wxwobot.admin.itchat4j.utils.enums.URLEnum;
import io.wxwobot.admin.web.base.BaseException;
import io.wxwobot.admin.web.utils.FileUtil;
import io.wxwobot.admin.web.wxrob.MyMsgHandler;

import javax.servlet.ServletOutputStream;
import javax.swing.plaf.basic.BasicPopupMenuUI;
import java.io.*;
import java.util.List;

/**
 * @author WesleyOne
 * @create 2018/12/16
 */
public class RobotWorkController extends _BaseController {

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
        String realUploadPath = PropKit.get("realImgUploadPath");
        //先清除下旧二维码
        FileUtil.delFile(realUploadPath,"QR.jpg");

        String qrPath = realUploadPath;
        LoginController login = new LoginController(coreKey);
        String qrSrc = login.login_1_new();
        setData(qrSrc);
        renderJson();
//        login.login_1(qrPath);
        // TODO 两次流处理,需要优化

//        File file = new File(realUploadPath+ File.separator +"QR.jpg");
//        if (file.exists()&&file.isFile()) {
//            InputStream inputStream = new FileInputStream(file);
//            ServletOutputStream outputStream = getResponse().getOutputStream();
//            int length = -1;
//            byte[] buffer = new byte[1024];
//            while ((length=inputStream.read(buffer, 0, 1024))!=-1) {
//                outputStream.write(buffer,0,length);
//            }
//
//            inputStream.close();
//            outputStream.flush();
//            outputStream.close();
//        }
//        renderNull();
    }


    /**
     * 登录第二步，确认登录及初始化信息
     * @throws BaseException
     */
    public void init() throws BaseException {
        String coreKey = getCoreKey();
        IMsgHandlerFace msgHandler = new MyMsgHandler(coreKey);
        Wechat wechat = new Wechat(msgHandler,coreKey);
        boolean loginResult = wechat.wxInit();
        wechat.start();
        if (loginResult){
            setMsg("登录成功");
        }else{
            setMsg("登录失败，关闭二维码后重新打开");
        }
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

    /**
     * 获取群昵称
     */
    public void getGroupNickNames() throws BaseException {
        String coreKey = getCoreKey();
        List<String> groupNickNameList = WechatTools.getGroupNickNameList(coreKey);
        setData(groupNickNameList);
        renderJson();
    }

    /**
     * 获取好友昵称
     */
    public void getContactNickNames() throws BaseException {
        String coreKey = getCoreKey();
        List<String> contactNickNameList = WechatTools.getContactNickNameList(coreKey);
        setData(contactNickNameList);
        renderJson();
    }

    public void getGroups() throws BaseException {
        String coreKey = getCoreKey();
        List<JSONObject> groupList = WechatTools.getGroupList(coreKey);
        setData(groupList);
        renderJson();
    }

    public void getContacts() throws BaseException {
        String coreKey = getCoreKey();
        List<JSONObject> contactList = WechatTools.getContactList(coreKey);
        setData(contactList);
        renderJson();
    }

}
