package io.wxwobot.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.api.WechatTools;
import io.wxwobot.admin.itchat4j.controller.LoginController;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.service.impl.LoginServiceImpl;
import io.wxwobot.admin.web.base.BaseException;
import io.wxwobot.admin.web.utils.FileUtil;

import java.io.*;
import java.util.List;
import java.util.Set;

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
    }

    /**
     * 登录第二步,确认登录状态
     * @throws BaseException
     */
    public void login() throws BaseException {
        String coreKey = getCoreKey();
        LoginController login = new LoginController(coreKey);
        boolean result = login.login_2();
        if (result){

        }else{
            setOperateErr();
        }
        renderJson();
    }

    /**
     * 登录第三步，确认登录及初始化信息
     * @throws BaseException
     */
    public void init() throws BaseException {
        String coreKey = getCoreKey();
        LoginController login = new LoginController(coreKey);
        boolean loginResult = login.login_3();
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

    public void getCore() throws BaseException {
        String coreKey = getCoreKey();
        setData(CoreManage.getInstance(coreKey));
        renderJson();
    }

    /**
     * 模拟个环节
     * 强制刷新联系人
     */
    public void gct() throws BaseException {
        String coreKey = getCoreKey();
        LoginServiceImpl loginService = new LoginServiceImpl(coreKey);
        loginService.webWxGetContact();
        renderJson();
    }

    /**
     * 刷新群组
     * @throws BaseException
     */
    public void ggp() throws BaseException {
        String coreKey = getCoreKey();
        LoginServiceImpl loginService = new LoginServiceImpl(coreKey);
        loginService.WebWxBatchGetContact();
        renderJson();
    }

    /**
     * 获取联系人昵称
     */
    public void getContactsNickName() throws BaseException {
        String coreKey = getCoreKey();
        Set<String> keySet = CoreManage.getInstance(coreKey).getUserInfoMap().keySet();
        setData(keySet);
        renderJson();
    }

    /**
     * 获取群昵称
     */
    public void getGroupsNickName() throws BaseException {
        String coreKey = getCoreKey();
        Set<String> keySet = CoreManage.getInstance(coreKey).getGroupInfoMap().keySet();
        setData(keySet);
        renderJson();
    }

    /**
     * 重启
     * @throws BaseException
     */
    public void reboot() throws BaseException {
        String coreKey = getCoreKey();
        LoginController login = new LoginController(coreKey);
        boolean loginResult = login.reboot();
        setData(loginResult);
        renderJson();
    }


    /**
     * 手动备份热登录信息
     */
    public void manualCopy(){
        CoreManage.persistence();
        renderJson();
    }

}
