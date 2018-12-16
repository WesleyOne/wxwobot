package io.wxwobot.admin.web.common;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import io.wxwobot.admin.web.beans.CoreDefaultConfig;
import io.wxwobot.admin.web.constant.ConfigKeys;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author WesleyOne
 * @create 2018/12/13
 */
public class MyCache {

    private static boolean useCache = false;
    private static Map<String,CoreDefaultConfig> cores = new HashMap<>(32);

    public static void clear(){
        cores = new HashMap<>(32);
    }

    public static CoreDefaultConfig getCoreConfig(String coreKey){
        if (!useCache || !cores.containsKey(coreKey)){
            // 基本配置
            Record aiConfig = Db.findFirst("SELECT TOP 1 * FROM custom_config with(nolock) WHERE [key] = ?", ConfigKeys.WX_ROB_AI + coreKey);
            String val = aiConfig.getStr("val");
            CoreDefaultConfig coreDefaultConfig = JSONObject.parseObject(val,CoreDefaultConfig.class);
            // 获取关键字配置
            Map<String, String> keywordsMap = coreDefaultConfig.getKeywords();
            Record keywordsConfig = Db.findFirst("SELECT TOP 1 * FROM custom_config with(nolock) WHERE [key] = ?", ConfigKeys.WX_ROB_KEYWORD + coreKey);
            String keyValue = keywordsConfig.getStr("val");
            JSONObject keyValueJson = JSONObject.parseObject(keyValue);
            Iterator<String> keywordsKeys = keyValueJson.keySet().iterator();
            while (keywordsKeys.hasNext()){
                String k = keywordsKeys.next();
                String v = keyValueJson.getString(k);
                keywordsMap.put(k,v);
            }
            cores.put(coreKey,coreDefaultConfig);
        }
        return cores.get(coreKey);
    }

}
