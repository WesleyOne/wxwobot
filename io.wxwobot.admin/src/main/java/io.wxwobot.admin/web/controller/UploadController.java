package io.wxwobot.admin.web.controller;

import com.jfinal.kit.PropKit;
import com.jfinal.upload.UploadFile;

import java.io.File;

/**
 * @author WesleyOne
 * @create 2018/12/14
 */
public class UploadController extends _BaseController {

    public void img2local(){
        UploadFile file = getFile();
        String realImgUploadPath = PropKit.get("realImgUploadPath");
        String imgDomain = PropKit.get("imgDomain");

        String fn = getPara("fn", "");
        String originalFileName = file.getOriginalFileName();
        int i = file.getOriginalFileName().lastIndexOf(".")+1;
        String fileType = originalFileName.substring(i);
        String fileName = fn + "_" + System.currentTimeMillis() + "." + fileType;
        String newFilePath = realImgUploadPath+File.separator+fileName;
        String newFileUrl = imgDomain+fileName;
        file.getFile().renameTo(new File(newFilePath));

        setAttr("url",newFileUrl);
        setAttr("name",fileName);

        renderJson();
    }

    public void file2local(){
        UploadFile file = getFile();
        String realFileUploadPath = PropKit.get("realFileUploadPath");
        String fileDomain = PropKit.get("fileDomain");

        String fn = getPara("fn", "");
        String originalFileName = file.getOriginalFileName();
        int i = file.getOriginalFileName().lastIndexOf(".")+1;
        String fileType = originalFileName.substring(i);
        String fileName = fn + "_" + System.currentTimeMillis() + "." + fileType;
        String newFilePath = realFileUploadPath+File.separator+fileName;
        String newFileUrl = fileDomain+fileName;
        file.getFile().renameTo(new File(newFilePath));

        setAttr("url",newFileUrl);
        setAttr("name",fileName);

        renderJson();
    }

}
