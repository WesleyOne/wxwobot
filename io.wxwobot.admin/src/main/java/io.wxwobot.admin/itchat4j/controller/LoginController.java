package io.wxwobot.admin.itchat4j.controller;

import io.wxwobot.admin.itchat4j.api.WechatTools;
import io.wxwobot.admin.itchat4j.service.ILoginService;
import io.wxwobot.admin.itchat4j.service.impl.LoginServiceImpl;
import io.wxwobot.admin.itchat4j.thread.CheckLoginStatusThread;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.SleepUtils;

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
	private String coreKey;

	public LoginController(String coreKey){
		this.coreKey = coreKey;
		this.loginService = new LoginServiceImpl(coreKey);
	}

	public void login(String qrPath) {
		if (loginService.getCore().isAlive()) { // 已登陆
			LOG.info("微信已登陆");
			return;
		}
		while (true) {
			for (int count = 0; count < 10; count++) {
				LOG.info("获取UUID");
				while (loginService.getUuid() == null) {
					LOG.info("1. 获取微信UUID");
					while (loginService.getUuid() == null) {
						LOG.warn("1.1. 获取微信UUID失败，两秒后重新获取");
						SleepUtils.sleep(2000);
					}
				}
				LOG.info("2. 获取登陆二维码图片");
				if (loginService.getQR(qrPath)) {
					break;
				} else if (count == 10) {
					LOG.error("2.2. 获取登陆二维码图片失败，系统退出");
					System.exit(0);
				}
			}
			LOG.info("3. 请扫描二维码图片，并在手机上确认");
			if (!loginService.getCore().isAlive()) {
				loginService.login();
				loginService.getCore().setAlive(true);
				LOG.info(("登陆成功"));
				break;
			}
			LOG.info("4. 登陆超时，请重新扫描二维码图片");
		}

		LOG.info("5. 登陆成功，微信初始化");
		if (!loginService.webWxInit()) {
			LOG.info("6. 微信初始化异常");
			System.exit(0);
		}

		LOG.info("6. 开启微信状态通知");
		loginService.wxStatusNotify();

		LOG.info("7. 清除。。。。");
//		CommonTools.clearScreen();
		LOG.info(String.format("欢迎回来， %s", loginService.getCore().getNickName()));

		LOG.info("8. 开始接收消息");
		loginService.startReceiving();

		LOG.info("9. 获取联系人信息");
		loginService.webWxGetContact();

		LOG.info("10. 获取群好友及群好友列表");
		loginService.WebWxBatchGetContact();

		LOG.info("11. 缓存本次登陆好友相关消息");
		WechatTools.setUserInfo(coreKey); // 登陆成功后缓存本次登陆好友相关消息（NickName, UserName）

		LOG.info("12.开启微信状态检测线程");
		Thread thread = new Thread(new CheckLoginStatusThread(coreKey));
		thread.setName("WXROB-STA-"+coreKey);
		thread.start();
	}

	/**
	 * 分步登录
	 */
	public void login_1(String qrPath) {
		if (loginService.getCore().isAlive()) { // 已登陆
			LOG.info("itchat4j已登陆");
			return;
		}

		for (int count = 0; count < 10; count++) {
			LOG.info("获取UUID");
			while (loginService.getUuid() == null) {
				LOG.info("1. 获取微信UUID");
				while (loginService.getUuid() == null) {
					LOG.warn("1.1. 获取微信UUID失败，两秒后重新获取");
					SleepUtils.sleep(2000);
				}
			}
			LOG.info("2. 获取登陆二维码图片");
			if (loginService.getQR(qrPath)) {
				break;
			} else if (count == 10) {
				LOG.error("2.2. 获取登陆二维码图片失败，系统退出");
				throw new RuntimeException("获取登陆二维码图片失败");
			}
		}
	}

	public void login_2() {

		LOG.info("3. 请扫描二维码图片，并在手机上确认");
		if (!loginService.getCore().isAlive()) {
			loginService.login();
			loginService.getCore().setAlive(true);
			LOG.info(("登陆成功"));
		}

		LOG.info("5. 登陆成功，微信初始化");
		if (!loginService.webWxInit()) {
			LOG.info("6. 微信初始化异常");
			System.exit(0);
		}

		LOG.info("6. 开启微信状态通知");
		loginService.wxStatusNotify();

		LOG.info("7. 清除。。。。");
//		CommonTools.clearScreen();
		LOG.info(String.format("欢迎回来， %s", loginService.getCore().getNickName()));

		LOG.info("8. 开始接收消息");
		loginService.startReceiving();

		LOG.info("9. 获取联系人信息");
		loginService.webWxGetContact();

		LOG.info("10. 获取群好友及群好友列表");
		loginService.WebWxBatchGetContact();

		LOG.info("11. 缓存本次登陆好友相关消息");
		WechatTools.setUserInfo(coreKey); // 登陆成功后缓存本次登陆好友相关消息（NickName, UserName）

		LOG.info("12.开启微信状态检测线程");
		Thread thread = new Thread(new CheckLoginStatusThread(coreKey));
		thread.setName("WXROB-STATUS-"+coreKey);
		thread.start();
	}

}