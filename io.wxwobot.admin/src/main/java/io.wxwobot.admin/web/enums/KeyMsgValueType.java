package io.wxwobot.admin.web.enums;

import com.jfinal.plugin.activerecord.Record;

import java.util.*;

/**
 * @author WesleyOne
 * @create 2018/12/14
 */
public enum KeyMsgValueType {

    IMG("IMG","图片"),
    FILE("FILE","文件"),
    TEXT("TEXT","纯文本")
    ;

    private String value;
    private String name;

    KeyMsgValueType(String value,String name) {
        this.value = value;
        this.name = name;
    }


    private static final Map<String, KeyMsgValueType> lookup = new HashMap<>();
    public static List<Record> LIST_KV = new ArrayList<>();
    static {
        for (KeyMsgValueType s : EnumSet.allOf(KeyMsgValueType.class)){
            lookup.put(s.toValue(), s);
            LIST_KV.add(new Record().set("v",s.toValue()).set("n",s.toName()));
        }
    }


    /**
     * 获取枚举的值（整数值、字符串值等）
     * @return
     */
    public String toValue() {
        return this.value;
    }

    public String toName() {
        return this.name;
    }


    /**
     * 根据值（整数值、字符串值等）获取相应的枚举类型
     * @param value
     * @return
     */
    public static KeyMsgValueType fromValue(String value) {
        return lookup.get(value);
    }
}
