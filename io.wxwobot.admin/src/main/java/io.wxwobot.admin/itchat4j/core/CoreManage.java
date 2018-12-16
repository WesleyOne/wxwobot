package io.wxwobot.admin.itchat4j.core;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * 多开管理
 * @author WesleyOne
 * @create 2018/12/13
 */
public class CoreManage {

    public static HashMap<String,Core> coreMap = new HashMap<>(32);

    public static Core getInstance(String coreKey) {
        if (StringUtils.isEmpty(coreKey)){
            return null;
        }
        Core core;
        if (coreMap.containsKey(coreKey)){
            core = coreMap.get(coreKey);
        }else {
            core = Core.getInstance(coreKey);
            coreMap.put(coreKey, core);
        }
        return core;
    }

    /**
     * 查询是否在线
     * @param coreKey
     * @return
     */
    public static boolean isActive(String coreKey) {
        if (StringUtils.isEmpty(coreKey)){
            return false;
        }
        if (coreMap.containsKey(coreKey) && coreMap.get(coreKey).isAlive()){
            return true;
        }
        return false;
    }
}
