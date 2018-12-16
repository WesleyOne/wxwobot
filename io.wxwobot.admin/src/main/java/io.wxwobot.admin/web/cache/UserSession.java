package io.wxwobot.admin.web.cache;

import io.wxwobot.admin.web.utils.LRUCache;

import java.util.LinkedHashMap;

/**
 * 会话处理
 * @author WesleyOne
 * @create 2018/9/25
 */
public class UserSession {
    public static LinkedHashMap<String,String> USERSESSION_CACHE = new LRUCache<String, String>(16);

    public static void addUserSession(String username,String userSession){
        USERSESSION_CACHE.put(username,userSession);
    }

    public static void delUserSession(String username){
        USERSESSION_CACHE.remove(username);
    }

    public static boolean checkUserSession(String username,String userSession){
        String s = USERSESSION_CACHE.get(username);
        if (userSession!=null&&userSession.equals(s)){
            return true;
        }
        return false;
    }
}
