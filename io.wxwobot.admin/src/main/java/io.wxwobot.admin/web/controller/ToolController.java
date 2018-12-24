package io.wxwobot.admin.web.controller;

/**
 * 通用工具
 * @author WesleyOne
 * @create 2018/12/24
 */
public class ToolController extends _BaseController {

    public void index(){
        setAttr("active","tool");
        renderTemplate("index.html");
    }

}
