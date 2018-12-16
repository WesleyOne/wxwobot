package io.wxwobot.admin.web.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings({"serial", "unchecked"})
public abstract class BaseWxRobKeyword<M extends BaseWxRobKeyword<M>> extends Model<M> implements IBean {

	public M setId(java.lang.Integer id) {
		set("id", id);
		return (M)this;
	}
	
	public java.lang.Integer getId() {
		return getInt("id");
	}

	public M setUniqueKey(java.lang.String uniqueKey) {
		set("unique_key", uniqueKey);
		return (M)this;
	}
	
	public java.lang.String getUniqueKey() {
		return getStr("unique_key");
	}

	public M setKeyData(java.lang.String keyData) {
		set("key_data", keyData);
		return (M)this;
	}
	
	public java.lang.String getKeyData() {
		return getStr("key_data");
	}

	public M setValueData(java.lang.String valueData) {
		set("value_data", valueData);
		return (M)this;
	}
	
	public java.lang.String getValueData() {
		return getStr("value_data");
	}

	public M setTypeData(java.lang.String typeData) {
		set("type_data", typeData);
		return (M)this;
	}
	
	public java.lang.String getTypeData() {
		return getStr("type_data");
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

}
