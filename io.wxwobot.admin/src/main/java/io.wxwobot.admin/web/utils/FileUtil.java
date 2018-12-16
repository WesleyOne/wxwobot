package io.wxwobot.admin.web.utils;

import java.io.File;

/**
 * @author WesleyOne
 * @create 2018/12/12
 */
public class FileUtil {

    public static void delFile(String path,String fileName){
        System.out.println(path+ File.separator + fileName);
        File file=new File(path+ File.separator + fileName);
        if(file.exists()&&file.isFile()){
            file.delete();
        }
    }
}
