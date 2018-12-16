package io.wxwobot.admin.itchat4j;

import io.wxwobot.admin.itchat4j.controller.LoginController;
import io.wxwobot.admin.itchat4j.core.MsgCenter;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wechat {
	private static final Logger LOG = LoggerFactory.getLogger(Wechat.class);
	private IMsgHandlerFace msgHandler;

	private LoginController login;
	private String coreKey;

	public Wechat(IMsgHandlerFace msgHandler,String coreKey){
        System.setProperty("jsse.enableSNIExtension", "false"); // 防止SSL错误
        this.msgHandler = msgHandler;
		this.coreKey = coreKey;
		this.login = new LoginController(coreKey);
    }

	public Wechat(IMsgHandlerFace msgHandler, String qrPath, String coreKey) {
        this(msgHandler, qrPath,false,coreKey);
	}

	public Wechat(IMsgHandlerFace msgHandler, String qrPath, boolean partLogin, String coreKey) {
	    System.setProperty("jsse.enableSNIExtension", "false"); // 防止SSL错误
        this.msgHandler = msgHandler;
		this.coreKey = coreKey;
        this.login = new LoginController(coreKey);
		if (partLogin){
			login.login_1(qrPath);
		}else {
            login.login(qrPath);
        }
	}

	public void wxInit(){
		login.login_2();
	}


	public void start() {
		LOG.info("+++++++开始消息处理+++++++"+coreKey+"+++++++");
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				MsgCenter.handleMsg(msgHandler, coreKey);
			}
		});
		thread.setName("WXBOT-MSG-"+coreKey);
		thread.start();
	}

}
