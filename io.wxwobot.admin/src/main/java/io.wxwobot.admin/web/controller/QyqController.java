package io.wxwobot.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.render.JsonRender;
import io.wxwobot.admin.web.model.WxPyqRelate;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WesleyOne
 * @create 2018/12/28
 */
public class QyqController extends _BaseController {

    private List<Record> appList(){
        List<Record> apps = Db.find("SELECT id,name FROM app");
        List<Record> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(apps)){
            for (Record app:apps){
                if (!app.getStr("name").startsWith("##")){
                    result.add(app);
                }
            }
        }
        return result;
    }

    public void index(){
        String outKey = getPara("ok");
        if (StringUtils.isNotEmpty(outKey)){
            setAttr("search_ok",outKey);
        }
        setAttr("apps",appList());
        setAttr("active","qyq");
        setAttr("imgdomain",PropKit.get("imgDomain"));
        renderTemplate("index.html");
    }

    public void list(){
        int rows = getParaToInt("limit", 10);
        int pageNum = getPageNum(getParaToInt("offset", 1), rows);
        String outKey = getPara("outKey");
        Integer appId = getParaToInt("appId");
        Integer pyqId = getParaToInt("pyqId");
        Boolean enable = getParaToBoolean("enable");

        String where = " where 1=1 ";
        if (StringUtils.isNotEmpty(outKey)){
            where += " and out_key = '"+outKey + "' ";
        }
        if (appId != null){
            where += " and app_id = '"+ appId +"'";
        }
        if (pyqId != null){
            where += " and pyq_id = '"+ pyqId +"'";
        }
        if (enable != null){
            where += " and enable = " + (enable?1:0);
        }

        Page<WxPyqRelate> page = WxPyqRelate.dao.paginate(pageNum, rows, "select * ",
                " from wx_pyq_relate with(nolock) "+where);

        setAttrs(buildPagination(page.getList(), page.getTotalRow()));
        render(new JsonRender().forIE());
    }



    public void editIndex(){

        Integer id = getParaToInt("id");
        WxPyqRelate editRecord;
        boolean isEdit = true;
        if (id != null){
            editRecord = WxPyqRelate.dao.findById(id);
        }else{
            isEdit = false;
            editRecord = new WxPyqRelate();
            String outKey = getPara("ok");
            if (StringUtils.isNotEmpty(outKey)){
                editRecord.setOutKey(outKey);

            }
        }
        List<Record> apps = Db.find("SELECT id,name FROM app");

        setAttr("isEdit",isEdit);
        setAttr("form",editRecord);

        setAttr("active","qyq");
        setAttr("imgdomain",PropKit.get("imgDomain"));
        setAttr("apps",appList());
        renderTemplate("editIndex.html");
    }

    public void editPost(){

        JSONObject postParam = getPostParam();
        Integer id = postParam.getInteger("id");
        String outKey = postParam.getString("outKey");
        Integer appId = postParam.getInteger("appId");
        Integer pyqId = postParam.getInteger("pyqId");
        String img = postParam.getString("img");
        Boolean enable = postParam.getBoolean("enable");

        WxPyqRelate editRecord = new WxPyqRelate();

        if (StringUtils.isNotEmpty(outKey)){
            editRecord.setOutKey(outKey);
        }
        if (appId != null){
            editRecord.setAppId(appId);
        }
        if (pyqId != null){
            editRecord.setPyqId(pyqId);
        }
        if (enable != null){
            editRecord.setEnable(enable);
        }
        if (StringUtils.isNotEmpty(img)){
            editRecord.setImg(img);
        }

        if (id != null){
            editRecord.setId(id);
            boolean update = editRecord.update();
            if (update){
                setMsg("修改成功");
            }else{
                setOperateErr("修改失败");
            }
        }else{
            // 校验
            editRecord.setEnable(true);
            if (validatorParamNull(editRecord.getOutKey(),"外接码不能为空")){
                return;
            }
            if (validatorParamNull(editRecord.getAppId(),"应用ID不能为空")){
                return;
            }
            if (validatorParamNull(editRecord.getPyqId(),"亲友圈ID不能为空")){
                return;
            }
            if (validatorParamNull(editRecord.getImg(),"图片ID不能为空")){
                return;
            }
            boolean save = editRecord.save();
            if (save){
                setMsg("新增成功");
            }else{
                setOperateErr("新增失败");
            }
        }
        renderJson();
    }

    /**
     * 删除
     */
    public void delPost(){
        String id = getPara("id");
        boolean delete = WxPyqRelate.dao.deleteById(id);
        if (delete){
            setMsg("删除成功");
        }else{
            setOperateErr("删除失败");
        }
        renderJson();
    }

}
