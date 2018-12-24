package io.wxwobot.admin.itchat4j.core;

import com.jfinal.kit.PropKit;
import io.wxwobot.admin.itchat4j.api.MessageTools;
import io.wxwobot.admin.itchat4j.api.WechatTools;
import io.wxwobot.admin.itchat4j.beans.BaseMsg;
import io.wxwobot.admin.itchat4j.face.IMsgHandlerFace;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.MoreConfig;
import io.wxwobot.admin.itchat4j.utils.enums.MsgCodeEnum;
import io.wxwobot.admin.itchat4j.utils.enums.MsgTypeEnum;
import io.wxwobot.admin.itchat4j.utils.tools.CommonTools;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 消息处理中心
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年5月14日 下午12:47:50
 * @version 1.0
 *
 */
public class MsgCenter implements LogInterface {

	/**
	 * 接收消息，放入队列
	 *
	 * @author https://github.com/yaphone
	 * @date 2017年4月23日 下午2:30:48
	 * @param msgList
	 * @return
	 */
	public static JSONArray produceMsg(JSONArray msgList, String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		JSONArray result = new JSONArray();
		// 用于暂存未知群ID,最后调用webwxbatchgetcontact获取
		List<String> unknowGroup = new ArrayList<>();

		for (int i = 0; i < msgList.size(); i++) {
			JSONObject msg = new JSONObject();
			JSONObject m = msgList.getJSONObject(i);

			if (PropKit.getBoolean("devMode",false)){
				LOG.info(m.toJSONString());
			}
			m.put("groupMsg", false);// 是否是群消息
			if (m.getString("FromUserName").contains("@@") || m.getString("ToUserName").contains("@@")) { // 群聊消息
				if (m.getString("FromUserName").contains("@@")
						&& !core.getGroupInfoMap().containsKey(m.getString("FromUserName"))) {
                    unknowGroup.add(m.getString("FromUserName"));
				} else if (m.getString("ToUserName").contains("@@")
						&& !core.getGroupInfoMap().containsKey(m.getString("ToUserName"))) {
                    unknowGroup.add(m.getString("ToUserName"));
				}
				// 群消息与普通消息不同的是在其消息体（Content）中会包含发送者id及":<br/>"消息，这里需要处理一下，去掉多余信息，只保留消息内容
				if (m.getString("Content").contains("<br/>")) {
					String content = m.getString("Content").substring(m.getString("Content").indexOf("<br/>") + 5);
					String sendMemberId = m.getString("Content").substring(0,m.getString("Content").indexOf("<br/>"));
					m.put("Content", content);
					m.put("groupMsg", true);
					m.put(MoreConfig.SEND_MEMBER_ID,sendMemberId);
				}
			}

			// 存在emoji就转换
			if (m.getString("Content").contains("emoji emoji")){
				CommonTools.msgFormatter(m, "Content");
			}
			// 是否打印日志
			boolean isLog = false;
			// 1.文本消息
			if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_TEXT.getCode())) {
				if (m.getString("Url").length() != 0) {
					// 1.1分享位置
					String[] contents = m.getString("Content").split(":");
					String data = "Map";
					if (contents.length>0) {
						data = contents[0]+":"+m.getString("Url");
					}
					msg.put("Type", "Map");
					msg.put("Text", data);
//					LOG.warn("MAP_CONTENT: {},URL: {}",m.getString("Content"),m.getString("Url"));
					/**
					 *	MAP_CONTENT: 滨兴小区(东区):/cgi-bin/mmwebwx-bin/webwxgetpubliclinkimg?url=xxx&msgid=7525662842661720095&pictype=location,URL: http://apis.map.qq.com/uri/v1/geocoder?coord=30.191660,120.200508
					 */

				} else {
					// 1.2 普通文本
					msg.put("Type", MsgTypeEnum.TEXT.getType());
					msg.put("Text", m.getString("Content"));
				}
				m.put("Type", msg.getString("Type"));
				m.put("Text", msg.getString("Text"));
				isLog = true;
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_IMAGE.getCode())
					|| m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_EMOTICON.getCode())) {
				// 2.图片消息
				m.put("Type", MsgTypeEnum.PIC.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_VOICE.getCode())) {
				// 3.语音消息
				m.put("Type", MsgTypeEnum.VOICE.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_VERIFYMSG.getCode())) {
				// 4.好友确认消息
				// MessageTools.addFriend(core, userName, 3, ticket); // 确认添加好友
				m.put("Type", MsgTypeEnum.VERIFYMSG.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_SHARECARD.getCode())) {
				// 5.共享名片
				m.put("Type", MsgTypeEnum.NAMECARD.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_VIDEO.getCode())
					|| m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_MICROVIDEO.getCode())) {
				// 6.视频
				m.put("Type", MsgTypeEnum.VIEDO.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_MEDIA.getCode())) {
				// 7.分享链接
				m.put("Type", MsgTypeEnum.MEDIA.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_STATUSNOTIFY.getCode())) {
				// 微信初始化消息	系统
				m.put("Type", MsgTypeEnum.SYS.getType());
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_SYS.getCode())) {
				// 系统消息	系统
				m.put("Type", MsgTypeEnum.SYS.getType());
				isLog = true;
			} else if (m.getInteger("MsgType").equals(MsgCodeEnum.MSGTYPE_RECALLED.getCode())) {
				// 撤回消息	系统
				m.put("Type", MsgTypeEnum.SYS.getType());
				isLog = true;
			} else {
				LOG.error("Useless msg: {} \n {}",m.getInteger("MsgType"),m.getString("Content"));
			}

			/**
			 *	日志
			 *	显示收到的消息
 			 */
			String nickName;
			String memberName = "";
			if (m.getBoolean("groupMsg")){
				nickName = WechatTools.getGroupNickNameByUserName(m.getString("FromUserName"),coreKey);

				if (m.getString(MoreConfig.SEND_MEMBER_ID) != null){
					// TODO 获取成员昵称
//					memberName = WechatTools.getMemberNickName(m.getString("FromUserName"),coreKey,m.getString(MoreConfig.SEND_MEMBER_ID));
//					m.put(MoreConfig.SEND_MEMBER_NICKNAMW,memberName);
				}
			}else {
				nickName = WechatTools.getContactNickNameByUserName(m.getString("FromUserName"),coreKey);
			}
			m.put("fromNickName",nickName);
			LOG.info("收到【{}】=>【{}】消息,来自: {} _ {} \n 内容: {} " ,
					MsgCodeEnum.fromCode(m.getInteger("MsgType"))==null?"未知类型"+m.getInteger("MsgType"):MsgCodeEnum.fromCode(m.getInteger("MsgType")).getType(),
					m.getString("Type"),
					nickName,
					memberName,
					StringUtils.isNotEmpty(m.getString("Content"))?m.getString("Content"):"");
			result.add(m);
		}
		return result;
	}

	/**
	 * 消息处理
	 *
	 * @author https://github.com/yaphone
	 * @date 2017年5月14日 上午10:52:34
	 * @param msgHandler
	 */
	public static void handleMsg(IMsgHandlerFace msgHandler, String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		while (true) {
			if (!core.isAlive()){
				LOG.info("handleMsg_log_out");
				break;
			}
			if (core.getMsgList().size() > 0 && core.getMsgList().get(0).getContent() != null) {
				if (core.getMsgList().get(0).getContent().length() > 0) {
					BaseMsg msg = core.getMsgList().get(0);
					if (msg.getType() != null) {
						try {
							if (msg.getType().equals(MsgTypeEnum.TEXT.getType())) {
								String result = msgHandler.textMsgHandle(msg);
							} else if (msg.getType().equals(MsgTypeEnum.PIC.getType())) {
								String result = msgHandler.picMsgHandle(msg);
								MessageTools.sendMsgById(result, core.getMsgList().get(0).getFromUserName(),coreKey);
							} else if (msg.getType().equals(MsgTypeEnum.VOICE.getType())) {
								String result = msgHandler.voiceMsgHandle(msg);
								MessageTools.sendMsgById(result, core.getMsgList().get(0).getFromUserName(),coreKey);
							} else if (msg.getType().equals(MsgTypeEnum.VIEDO.getType())) {
								String result = msgHandler.videoMsgHandle(msg);
								MessageTools.sendMsgById(result, core.getMsgList().get(0).getFromUserName(),coreKey);
							} else if (msg.getType().equals(MsgTypeEnum.NAMECARD.getType())) {
								String result = msgHandler.nameCardMsgHandle(msg);
								MessageTools.sendMsgById(result, core.getMsgList().get(0).getFromUserName(),coreKey);
							} else if (msg.getType().equals(MsgTypeEnum.SYS.getType())) { // 系统消息
								msgHandler.sysMsgHandle(msg);
							} else if (msg.getType().equals(MsgTypeEnum.VERIFYMSG.getType())) { // 确认添加好友消息
								String result = msgHandler.verifyAddFriendMsgHandle(msg);
								MessageTools.sendMsgById(result,
										core.getMsgList().get(0).getRecommendInfo().getUserName(),coreKey);
							} else if (msg.getType().equals(MsgTypeEnum.MEDIA.getType())) { // 多媒体消息
								String result = msgHandler.mediaMsgHandle(msg);
								MessageTools.sendMsgById(result, core.getMsgList().get(0).getFromUserName(),coreKey);
							} else{
								LOG.warn("暂未处理信息【{}】", msg.getType());
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				core.getMsgList().remove(0);
			}
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
