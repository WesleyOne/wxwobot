package io.wxwobot.admin.web.controller;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.JsonRender;
import io.wxwobot.admin.itchat4j.core.Core;
import io.wxwobot.admin.itchat4j.core.CoreManage;
import io.wxwobot.admin.web.model.WxRobConfig;

import java.util.Date;
import java.util.List;

/**
 * TODO 机器人相关管理接口
 * @author WesleyOne
 * @create 2018/12/16
 */
public class RobotController extends _BaseController {

    /**
     * 机器人页面
     */
    public void index(){

        renderTemplate("index.html");
    }

    /**
     * 机器人列表
     * 分页，搜索条件
     */
    public void list(){
        int rows = getParaToInt("limit", 10);
        int pageNum = getPageNum(getParaToInt("offset", 1), rows);

        Page<WxRobConfig> page = WxRobConfig.dao.paginate(pageNum, rows, "select * ",
                " from  wx_rob_config ");
        //其他处理
        List<WxRobConfig> dataList = page.getList();
        for (WxRobConfig conf: dataList){
            // TODO 获取机器人状态
            String uniqueKey = conf.getUniqueKey();
            conf.setActive(CoreManage.isActive(uniqueKey));
        }

        setAttrs(buildPagination(dataList, page.getTotalRow()));
        render(new JsonRender().forIE());

        renderJson();
    }

    /**
     * 添加机器人
     */
    public void addRob(){
        WxRobConfig bean = getBean(WxRobConfig.class, true);
        bean.setWhiteList("");
        bean.setCreateTime(new Date());
        bean.setUpdateTime(new Date());
        bean.setToFriend(false);
        bean.setToGroup(false);
        bean.save();
        renderJson();
    }

    /**
     * 删除机器人
     */
    public void delRob(){


        renderJson();
    }


}
