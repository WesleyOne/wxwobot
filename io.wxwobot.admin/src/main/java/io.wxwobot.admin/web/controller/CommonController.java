package io.wxwobot.admin.web.controller;

/**
 * TODO 通用配置
 * @author WesleyOne
 * @create 2018/12/16
 */
public class CommonController extends _BaseController {

    public void index(){

        renderTemplate("index.html");
    }

    /**
     * 编辑外部id关联
     */
    public void editRelation(){

        renderJson();
    }

    /**
     * 删除外部id关联
     */
    public void delRelation(){

        renderJson();
    }
}
