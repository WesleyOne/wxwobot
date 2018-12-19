package io.wxwobot.admin.itchat4j.client;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * 多开请求类管理
 * @author WesleyOne
 * @create 2018/12/15
 */
public class HttpClientManage {

    public static HashMap<String,SingleHttpClient> clientMap = new HashMap<>(32);

    public static SingleHttpClient getInstance(String uniqueKey) {
        if (StringUtils.isEmpty(uniqueKey)){
            return null;
        }
        SingleHttpClient client;
        if (!clientMap.containsKey(uniqueKey)){
            client = SingleHttpClient.getInstance(uniqueKey);
            clientMap.put(uniqueKey, client);
        }
        return clientMap.get(uniqueKey);
    }
}
