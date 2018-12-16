package cn.zhouyafeng.itchat4j.client;

import cn.zhouyafeng.itchat4j.core.Core;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;

/**
 * 多开请求类管理
 * @author WesleyOne
 * @create 2018/12/15
 */
public class HttpClientManage {

    public static HashMap<String,SingleHttpClient> clientMap = new HashMap<>(32);

    public static SingleHttpClient getInstance(String coreKey) {
        if (StringUtils.isEmpty(coreKey)){
            return null;
        }
        SingleHttpClient client;
        if (!clientMap.containsKey(coreKey)){
            client = SingleHttpClient.getInstance(coreKey);
            clientMap.put(coreKey, client);
        }
        return clientMap.get(coreKey);
    }
}
