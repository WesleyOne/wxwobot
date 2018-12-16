package io.wxwobot.admin.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWxRobRelation<M extends BaseWxRobRelation<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setOutKey(java.lang.String outKey) {
		set("out_key", outKey);
		return (M)this;
	}
	
	public java.lang.String getOutKey() {
		return getStr("out_key");
	}

	public M setUniqueKey(java.lang.String uniqueKey) {
		set("unique_key", uniqueKey);
		return (M)this;
	}
	
	public java.lang.String getUniqueKey() {
		return getStr("unique_key");
	}

	public M setNickName(java.lang.String nickName) {
		set("nick_name", nickName);
		return (M)this;
	}
	
	public java.lang.String getNickName() {
		return getStr("nick_name");
	}

	public M setCreateTime(java.util.Date createTime) {
		set("create_time", createTime);
		return (M)this;
	}
	
	public java.util.Date getCreateTime() {
		return get("create_time");
	}

	public M setEnable(java.lang.Boolean enable) {
		set("enable", enable);
		return (M)this;
	}
	
	public java.lang.Boolean getEnable() {
		return get("enable");
	}

	public M setToGroup(java.lang.Boolean toGroup) {
		set("to_group", toGroup);
		return (M)this;
	}
	
	public java.lang.Boolean getToGroup() {
		return get("to_group");
	}

}
