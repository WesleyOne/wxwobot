package io.wxwobot.admin.itchat4j.beans;

import java.util.List;

/**
 * 成员信息对象
 *
 *  来自获取好友列表
 *  /cgi-bin/mmwebwx-bin/webwxgetcontact
 * MemberList中单体
 * @author WesleyOne
 * @create 2018/12/21
 */
public class Member {

    private String Alias;
    private Integer AppAccountFlag;
    private Integer AttrStatus;
    private Integer ChatRoomId;
    private String City;
    private Integer ContactFlag;
    /**
     * 群昵称
     */
    private String DisplayName;
    private String EncryChatRoomId;
    private String HeadImgUrl;
    private Integer HideInputBarFlag;
    private Integer IsOwner;
    private String KeyWord;
    private Integer MemberCount;
    private List<Member> MemberList;
    private String NickName;
    private Integer OwnerUin;
    private String PYInitial;
    private String PYQuanPin;
    private String Province;
    /**
     * 备注名、首拼、全拼
     */
    private String RemarkName;
    private String RemarkPYInitial;
    private String RemarkPYQuanPin;

    private Integer Sex;
    private String Signature;
    private Integer SnsFlag;
    private Integer StarFriend;
    private Integer Statues;
    private Integer Uin;
    private Integer UniFriend;
    private String UserName;
    /**
     * 用来判断是否是公众号或服务号的字段
     */
    private String VerifyFlag;

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public java.lang.Integer getAppAccountFlag() {
        return AppAccountFlag;
    }

    public void setAppAccountFlag(java.lang.Integer appAccountFlag) {
        AppAccountFlag = appAccountFlag;
    }

    public java.lang.Integer getAttrStatus() {
        return AttrStatus;
    }

    public void setAttrStatus(java.lang.Integer attrStatus) {
        AttrStatus = attrStatus;
    }

    public java.lang.Integer getChatRoomId() {
        return ChatRoomId;
    }

    public void setChatRoomId(java.lang.Integer chatRoomId) {
        ChatRoomId = chatRoomId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public java.lang.Integer getContactFlag() {
        return ContactFlag;
    }

    public void setContactFlag(java.lang.Integer contactFlag) {
        ContactFlag = contactFlag;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }

    public String getEncryChatRoomId() {
        return EncryChatRoomId;
    }

    public void setEncryChatRoomId(String encryChatRoomId) {
        EncryChatRoomId = encryChatRoomId;
    }

    public String getHeadImgUrl() {
        return HeadImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        HeadImgUrl = headImgUrl;
    }

    public java.lang.Integer getHideInputBarFlag() {
        return HideInputBarFlag;
    }

    public void setHideInputBarFlag(java.lang.Integer hideInputBarFlag) {
        HideInputBarFlag = hideInputBarFlag;
    }

    public java.lang.Integer getIsOwner() {
        return IsOwner;
    }

    public void setIsOwner(java.lang.Integer isOwner) {
        IsOwner = isOwner;
    }

    public String getKeyWord() {
        return KeyWord;
    }

    public void setKeyWord(String keyWord) {
        KeyWord = keyWord;
    }

    public java.lang.Integer getMemberCount() {
        return MemberCount;
    }

    public void setMemberCount(java.lang.Integer memberCount) {
        MemberCount = memberCount;
    }

    public List<Member> getMemberList() {
        return MemberList;
    }

    public void setMemberList(List<Member> memberList) {
        MemberList = memberList;
    }

    public String getNickName() {
        return NickName;
    }

    public void setNickName(String nickName) {
        NickName = nickName;
    }

    public java.lang.Integer getOwnerUin() {
        return OwnerUin;
    }

    public void setOwnerUin(java.lang.Integer ownerUin) {
        OwnerUin = ownerUin;
    }

    public String getPYInitial() {
        return PYInitial;
    }

    public void setPYInitial(String PYInitial) {
        this.PYInitial = PYInitial;
    }

    public String getPYQuanPin() {
        return PYQuanPin;
    }

    public void setPYQuanPin(String PYQuanPin) {
        this.PYQuanPin = PYQuanPin;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getRemarkName() {
        return RemarkName;
    }

    public void setRemarkName(String remarkName) {
        RemarkName = remarkName;
    }

    public String getRemarkPYInitial() {
        return RemarkPYInitial;
    }

    public void setRemarkPYInitial(String remarkPYInitial) {
        RemarkPYInitial = remarkPYInitial;
    }

    public String getRemarkPYQuanPin() {
        return RemarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String remarkPYQuanPin) {
        RemarkPYQuanPin = remarkPYQuanPin;
    }

    public java.lang.Integer getSex() {
        return Sex;
    }

    public void setSex(java.lang.Integer sex) {
        Sex = sex;
    }

    public String getSignature() {
        return Signature;
    }

    public void setSignature(String signature) {
        Signature = signature;
    }

    public java.lang.Integer getSnsFlag() {
        return SnsFlag;
    }

    public void setSnsFlag(java.lang.Integer snsFlag) {
        SnsFlag = snsFlag;
    }

    public java.lang.Integer getStarFriend() {
        return StarFriend;
    }

    public void setStarFriend(java.lang.Integer starFriend) {
        StarFriend = starFriend;
    }

    public java.lang.Integer getStatues() {
        return Statues;
    }

    public void setStatues(java.lang.Integer statues) {
        Statues = statues;
    }

    public java.lang.Integer getUin() {
        return Uin;
    }

    public void setUin(java.lang.Integer uin) {
        Uin = uin;
    }

    public java.lang.Integer getUniFriend() {
        return UniFriend;
    }

    public void setUniFriend(java.lang.Integer uniFriend) {
        UniFriend = uniFriend;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getVerifyFlag() {
        return VerifyFlag;
    }

    public void setVerifyFlag(String verifyFlag) {
        VerifyFlag = verifyFlag;
    }
}
