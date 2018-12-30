package io.wxwobot.admin.itchat4j.controller;

import io.wxwobot.admin.itchat4j.core.Core;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.core.MsgCenter;
import io.wxwobot.admin.itchat4j.service.ILoginService;
import io.wxwobot.admin.itchat4j.service.impl.LoginServiceImpl;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.SleepUtils;
import io.wxwobot.admin.itchat4j.utils.enums.URLEnum;
import io.wxwobot.admin.web.base.BaseException;

/**
 * 登陆控制器
 * 
 * @author https://github.com/yaphone
 * @date 创建时间：2017年5月13日 下午12:56:07
 * @version 1.0
 *
 */
public class LoginController  implements LogInterface {

	private ILoginService loginService;
	private String uniqueKey;
	private Core core;

	public LoginController(String uniqueKey){
		this.uniqueKey = uniqueKey;
		this.loginService = new LoginServiceImpl(uniqueKey);
		this.core = CoreManage.getInstance(uniqueKey);
	}

    /**
     * 获取二维码地址
     * @return
     */
	public String login_1() throws BaseException {
        if (core.isAlive()) {
			LOG.warn("微信已登陆");
            throw new BaseException("微信已登陆");
        }
        LOG.info("1.获取微信UUID");
        while (loginService.getUuid() == null) {
            LOG.warn("1.1. 获取微信UUID失败，一秒后重新获取");
            SleepUtils.sleep(1000);
        }
        LOG.info("2. 获取登陆二维码图片");
        return URLEnum.QRCODE_URL.getUrl() + core.getUuid();
    }

	/**
	 * 确认登录
	 * @return
	 */
    public  boolean login_2(){

		boolean result = false;

		LOG.info("3. 请扫描二维码图片，并在手机上确认");
		if (!core.isAlive()) {
			if (loginService.login()){
				core.setAlive(true);
				LOG.info(("3.1登陆成功"));
				result = true;
			}
		}
		return result;
	}

	/**
	 * 加载数据
	 * @return
	 */
	public boolean login_3() {

		boolean result = true;

		LOG.info("4.微信初始化");
		if (!loginService.webWxInit()) {
			LOG.info("4.1 微信初始化异常");
			result = false;
		}

		if (result){

			LOG.info("5. 开启微信状态通知");
			loginService.wxStatusNotify();

			LOG.info(String.format("欢迎回来， %s", core.getNickName()));

			LOG.info("6. 开始接收消息");
			loginService.startReceiving();

			LOG.info("7. 获取联系人信息");
			loginService.webWxGetContact();

			LOG.info("8. 获取群好友及群好友列表及缓存");
			loginService.WebWxBatchGetContact();

			LOG.info("9.+++开始消息处理+++++++"+uniqueKey+"+++++++");
			Thread msgThread = new Thread(core.getThreadGroup(),new Runnable() {
				@Override
				public void run() {
					MsgCenter.handleMsg(uniqueKey);
				}
			},"WXBOT-MSG-"+uniqueKey);
			msgThread.start();

		}

		if (!result){
			core.setAlive(false);
			return false;
		}

		core.setFinishInit(true);

		return true;
	}

	public boolean reboot(){

		core.setFinishInit(false);
		// 重新加载数据
		boolean result = true;
		LOG.info("1.刷新初始化信息");
		if (!loginService.webWxInit()) {
			LOG.info("1.1 微信初始化异常");
			result = false;
		}

		if (result){
			LOG.info("2. 刷新开启微信状态通知");
			loginService.wxStatusNotify();

			LOG.info("3. 刷新获取联系人信息");
			loginService.webWxGetContact();

			LOG.info("4. 刷新获取群好友及群好友列表");
			loginService.WebWxBatchGetContact();
		}

		if (!result){
			core.setAlive(false);
			return false;
		}

		core.setFinishInit(true);
		return true;
	}

}