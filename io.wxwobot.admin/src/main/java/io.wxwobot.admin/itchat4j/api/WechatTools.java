package io.wxwobot.admin.itchat4j.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.wxwobot.admin.itchat4j.core.Core;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.itchat4j.utils.LogInterface;
import io.wxwobot.admin.itchat4j.utils.enums.StorageLoginInfoEnum;
import io.wxwobot.admin.itchat4j.utils.enums.URLEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小工具，如获好友列表,根据昵称查找好友或群等
 *
 * @author https://github.com/yaphone
 * @date 创建时间：2017年5月4日 下午10:49:16
 * @version 1.0
 *
 */
public class WechatTools  implements LogInterface {

	/**
	 * 返回好友昵称列表
	 *
	 * @author https://github.com/yaphone
	 * @date 2017年5月4日 下午11:37:20
	 * @return
	 */
	public static List<String> getContactNickNameList(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		List<String> contactNickNameList = new ArrayList<String>();
		for (JSONObject o : core.getContactList()) {
			contactNickNameList.add(o.getString("NickName"));
		}
		return contactNickNameList;
	}

	/**
	 * 返回好友完整信息列表
	 *
	 * @date 2017年6月26日 下午9:45:39
	 * @return
	 */
	public static List<JSONObject> getContactList(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		return core.getContactList();
	}

	/**
	 * 返回群列表
	 *
	 * @author https://github.com/yaphone
	 * @date 2017年5月5日 下午9:55:21
	 * @return
	 */
	public static List<JSONObject> getGroupList(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		return core.getGroupList();
	}

	/**
	 * 获取群NickName列表
	 *
	 * @date 2017年6月21日 下午11:43:38
	 * @return
	 */
	public static List<String> getGroupNickNameList(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		return core.getGroupNickNameList();
	}

	/**
	 * 根据groupIdList返回群成员列表
	 *
	 * @date 2017年6月13日 下午11:12:31
	 * @param groupId
	 * @return
	 */
	public static JSONArray getMemberListByGroupId(String groupId, String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		return core.getGroupMemeberMap().get(groupId);
	}

	/**
	 * 退出微信
	 *
	 * @author https://github.com/yaphone
	 * @date 2017年5月18日 下午11:56:54
	 */
	public static void logout(String coreKey) {
		webWxLogout(coreKey);
	}

	private static boolean webWxLogout(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		String url = String.format(URLEnum.WEB_WX_LOGOUT.getUrl(),
				core.getLoginInfo().get(StorageLoginInfoEnum.url.getKey()));
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("redirect", "1"));
		params.add(new BasicNameValuePair("type", "1"));
		params.add(
				new BasicNameValuePair("skey", (String) core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey())));
		try {
			HttpEntity entity = core.getMyHttpClient().doGet(url, params, false, null);
			String text = EntityUtils.toString(entity, Consts.UTF_8); // 无消息
			return true;
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		} finally {
			// 强制退出，提示相关线程退出
			core.setAlive(false);
		}
		return false;
	}

	public static void setUserInfo(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		for (JSONObject o : core.getContactList()) {
			core.getUserInfoMap().put(o.getString("NickName"), o);
			core.getUserInfoMap().put(o.getString("UserName"), o);
		}
	}

	/**
	 *
	 * 根据用户昵称设置备注名称
	 *
	 * @date 2017年5月27日 上午12:21:40
	 * @param nickName
	 * @param remName
	 */
	public static void remarkNameByNickName(String nickName, String remName, String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		String url = String.format(URLEnum.WEB_WX_REMARKNAME.getUrl(), core.getLoginInfo().get("url"),
				core.getLoginInfo().get(StorageLoginInfoEnum.pass_ticket.getKey()));
		Map<String, Object> msgMap = new HashMap<String, Object>(8);
		Map<String, Object> msgMap_BaseRequest = new HashMap<String, Object>(8);
		msgMap.put("CmdId", 2);
		msgMap.put("RemarkName", remName);
		msgMap.put("UserName", core.getUserInfoMap().get(nickName).get("UserName"));
		msgMap_BaseRequest.put("Uin", core.getLoginInfo().get(StorageLoginInfoEnum.wxuin.getKey()));
		msgMap_BaseRequest.put("Sid", core.getLoginInfo().get(StorageLoginInfoEnum.wxsid.getKey()));
		msgMap_BaseRequest.put("Skey", core.getLoginInfo().get(StorageLoginInfoEnum.skey.getKey()));
		msgMap_BaseRequest.put("DeviceID", core.getLoginInfo().get(StorageLoginInfoEnum.deviceid.getKey()));
		msgMap.put("BaseRequest", msgMap_BaseRequest);
		try {
			String paramStr = JSON.toJSONString(msgMap);
			HttpEntity entity = core.getMyHttpClient().doPost(url, paramStr);
			// String result = EntityUtils.toString(entity, Consts.UTF_8);
			LOG.info("修改备注" + remName);
		} catch (Exception e) {
			LOG.error("remarkNameByUserName", e);
		}
	}

	/**
	 * 获取微信在线状态
	 *
	 * @date 2017年6月16日 上午12:47:46
	 * @return
	 */
	public static boolean getWechatStatus(String coreKey) {
		Core core = CoreManage.getInstance(coreKey);
		return core.isAlive();
	}


	/**
	 *
	 * @param userName
	 * @param nickName
	 * @return
	 */
	private static JSONObject getContactByNickNameAndUserName(String userName, String nickName, String coreKey){
		Core core = CoreManage.getInstance(coreKey);
		// 通过userName查询
		if (StringUtils.isNotEmpty(userName) && StringUtils.isEmpty(nickName)){
			for (JSONObject contact:core.getContactList()){
				if (userName.equals(contact.getString("UserName"))){
					return contact;
				}
			}
		}

		// 通过群昵称查询
		if (StringUtils.isNotEmpty(nickName) && StringUtils.isEmpty(userName)){
			for (JSONObject contact:core.getContactList()){
				if (nickName.equals(contact.getString("NickName"))){
					return contact;
				}
			}
		}

		// 通过userName和昵称联合查
		if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(nickName)){
			for (JSONObject contact:core.getContactList()){
				if (nickName.equals(contact.getString("NickName")) && userName.equals(contact.getString("UserName"))){
					return contact;
				}
			}
		}

		return null;
	}

	/**
	 * 通过群id查找群昵称
	 * @param userName
	 * @return
	 */
	public static String getContactNickNameByUserName(String userName, String coreKey){
		JSONObject contact = getContactByNickNameAndUserName(userName,null, coreKey);
		if (contact!=null && StringUtils.isNotEmpty(contact.getString("NickName"))){
			return contact.getString("NickName");
		}else{
			return "";
		}
	}

	/**
	 * 通过群昵称查找群id
	 * @param nickName
	 * @return
	 */
	public static String getContactUserNameByNickName(String nickName, String coreKey){
		JSONObject contact = getContactByNickNameAndUserName(null,nickName,coreKey);
		if (contact!=null && StringUtils.isNotEmpty(contact.getString("UserName"))){
			return contact.getString("UserName");
		}else{
			return null;
		}
	}

	/**
	 * 通过userName或昵称查找群信息
	 * @param userName
	 * @return
	 */
	private static JSONObject getGroupByNickNameAndUserName(String userName, String nickName, String coreKey){
		Core core = CoreManage.getInstance(coreKey);
		// 通过userName查询
		if (StringUtils.isNotEmpty(userName) && StringUtils.isEmpty(nickName)){
			for (JSONObject group:core.getGroupList()){
				if (userName.equals(group.getString("UserName"))){
					return group;
				}
			}
		}

		// 通过群昵称查询
		if (StringUtils.isNotEmpty(nickName) && StringUtils.isEmpty(userName)){
			for (JSONObject group:core.getGroupList()){
				if (nickName.equals(group.getString("NickName"))){
					return group;
				}
			}
		}

		// 通过userName和昵称联合查
		if (StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(nickName)){
			for (JSONObject group:core.getGroupList()){
				if (nickName.equals(group.getString("NickName")) && userName.equals(group.getString("UserName"))){
					return group;
				}
			}
		}

		return null;
	}

	/**
	 * 通过群id查找群昵称
	 * @param userName
	 * @return
	 */
	public static String getGroupNickNameByUserName(String userName, String coreKey){
		JSONObject group = getGroupByNickNameAndUserName(userName,null,coreKey);
		if (group!=null && StringUtils.isNotEmpty(group.getString("NickName"))){
			return group.getString("NickName");
		}else{
			return "";
		}
	}

	/**
	 * 通过群昵称查找群id
	 * @param nickName
	 * @return
	 */
	public static String getGroupUserNameByNickName(String nickName, String coreKey){
		JSONObject group = getGroupByNickNameAndUserName(null,nickName, coreKey);
		if (group!=null && StringUtils.isNotEmpty(group.getString("UserName"))){
			return group.getString("UserName");
		}else{
			return null;
		}
	}
}