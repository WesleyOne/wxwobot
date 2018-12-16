package io.wxwobot.admin.web.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author WesleyOne
 * @create 2018/12/14
 */
public enum KeyMsgValueType {

    IMG("*IMG*","图片"),
    FILE("*FILE*","文件"),
    CASH("*CASH*","收款码"),
    TEXT("","文本")
    ;

    private String value;
    private String name;

    KeyMsgValueType(String value,String name) {
        this.value = value;
        this.name = name;
    }


    private static final Map<String, KeyMsgValueType> lookup = new HashMap<>();
    static {
        for (KeyMsgValueType s : EnumSet.allOf(KeyMsgValueType.class)){
            lookup.put(s.toValue(), s);
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
