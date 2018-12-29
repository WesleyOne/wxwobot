import io.wxwobot.admin.itchat4j.utils.tools.CommonTools;

import java.util.regex.Matcher;

/**
 * @author WesleyOne
 * @create 2018/12/26
 */
public class T3 {

    public static void main(String[] args) {
        String text = "\"程cq\"通过扫描你分享的二维码加入群聊";
        Matcher matcher = CommonTools.getMatcher("邀请\"(.+?)\"加入了群聊", text);
        String newNickName = "";
        if (matcher.find()){
            newNickName = matcher.group(1);
        }else{
            matcher = CommonTools.getMatcher("\"(.+?)\"通过扫描(.+?)分享的二维码加入群聊", text);
            if (matcher.find()){
                newNickName = matcher.group(1);
            }
        }
        System.out.println(newNickName);
    }
}
