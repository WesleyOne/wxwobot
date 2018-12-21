package io.wxwobot.admin.itchat4j.client;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;

import java.util.HashMap;

/**
 * 多开请求类管理
 * @author WesleyOne
 * @create 2018/12/15
 */
public class HttpClientManage {

    public static HashMap<String,SingleHttpClient> clientMap = new HashMap<>(32);

    public static SingleHttpClient getInstance(String uniqueKey){
        return getInstance(uniqueKey,null);
    }

    public static SingleHttpClient getInstance(String uniqueKey,CookieStore outCookieStore) {
        if (StringUtils.isEmpty(uniqueKey)){
            return null;
        }
        SingleHttpClient client;
        // TODO outCookieStore不为空时也重新构造
        if (clientMap.containsKey(uniqueKey) && clientMap.get(uniqueKey) != null && outCookieStore == null){

        }else{
            client = SingleHttpClient.getInstance(uniqueKey, outCookieStore);
            clientMap.put(uniqueKey, client);
        }
        return clientMap.get(uniqueKey);
    }
}
