package io.wxwobot.admin.web.beans;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能实例
 * @author WesleyOne
 * @create 2018/12/13
 */
public class CoreDefaultConfig implements Serializable {

    private static final long serialVersionUID = 1L;


    public CoreDefaultConfig(){

    }

    private String coreKey;
    private int appId;
    private String numAccount;
    private int numId;
    // 关闭群聊
    private boolean noGroup = true;
    // 关闭好友聊天
    private boolean noFriend = true;
    // 收款码路径
    private String cashImg = "";

    @JSONField(serialize = false)
    private Map<String,String> keywords = new HashMap<>(16);

    public String getCoreKey() {
        return coreKey;
    }

    public void setCoreKey(String coreKey) {
        this.coreKey = coreKey;
    }

    public boolean isNoGroup() {
        return noGroup;
    }

    public void setNoGroup(boolean noGroup) {
        this.noGroup = noGroup;
    }

    public boolean isNoFriend() {
        return noFriend;
    }

    public void setNoFriend(boolean noFriend) {
        this.noFriend = noFriend;
    }

    public Map<String, String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Map<String, String> keywords) {
        this.keywords = keywords;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    public String getNumAccount() {
        return numAccount;
    }

    public void setNumAccount(String numAccount) {
        this.numAccount = numAccount;
    }

    public int getNumId() {
        return numId;
    }

    public void setNumId(int numId) {
        this.numId = numId;
    }

    public String getCashImg() {
        return cashImg;
    }

    public void setCashImg(String cashImg) {
        this.cashImg = cashImg;
    }
}
