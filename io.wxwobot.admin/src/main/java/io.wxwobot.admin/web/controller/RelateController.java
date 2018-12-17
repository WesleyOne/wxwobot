package io.wxwobot.admin.web.controller;

/**
 * TODO 关联配置
 * @author WesleyOne
 * @create 2018/12/16
 */
public class RelateController extends _BaseController {

    public void index(){
        setAttr("active","relate");
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
