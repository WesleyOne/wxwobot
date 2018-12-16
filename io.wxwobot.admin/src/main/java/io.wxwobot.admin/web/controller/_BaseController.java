package io.wxwobot.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.kit.HttpKit;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import io.wxwobot.admin.web.base.BaseError;
import io.wxwobot.admin.web.base.BaseException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WesleyOne
 * @create 2018/7/27
 */
public class _BaseController extends Controller {

    public final Logger LOG = LoggerFactory.getLogger(this.getClass());


    /**
     * 通用验证
     * @param result    true说明验证不通过
     * @param errorMsg
     */
    public boolean validatorParam(boolean result,String code,String errorMsg){
        if (result){
            setAttr("code",code);
            setAttr("message",errorMsg);
            this.renderJson();
            return true;
        }
        return false;
    }

    public boolean validatorParam(boolean result,BaseError baseError){
        if (result){
            return validatorParam(true,baseError.getCode(),baseError.getMsg());
        }
        return false;
    }

    public boolean validatorParam(boolean result,String errMsg){
        if (result){
            return validatorParam(true,BaseError.NORMAL_ERR.getCode(),errMsg);
        }
        return false;
    }

    /**
     * 参数非空判断
     * @param paramValue
     * @param baseError
     * @return
     */
    public boolean validatorParamNull(String paramValue,BaseError baseError){
        if (StrKit.isBlank(paramValue)) {
            return validatorParam(true,baseError.getCode(),baseError.getMsg());
        }
        return false;
    }

    public boolean validatorParamNull(String paramValue,String errMsg){
        if (StrKit.isBlank(paramValue)) {
            return validatorParam(true,BaseError.NORMAL_ERR.getCode(),errMsg);
        }
        return false;
    }

    /**
     * 参数非空判断
     * @param paramValue
     * @param baseError
     * @return
     */
    public boolean validatorParamNull(Object paramValue,BaseError baseError){
        if (paramValue == null) {
            return validatorParam(true,baseError.getCode(),baseError.getMsg());
        }
        return false;
    }

    public boolean validatorParamNull(Object paramValue,String errMsg){
        if (paramValue == null) {
            return validatorParam(true,BaseError.NORMAL_ERR.getCode(),errMsg);
        }
        return false;
    }


    public void setData(Object o){
        this.setAttr("data",o);
    }

    public void setCount(Object o){
        this.setAttr("_count",o);
    }

    public void setCode(String code){
        this.setAttr("code",code);
    }

    public void setMsg(String msg){
        this.setAttr("message",msg);
    }

    public void setOperateErr(String msg){
        this.setAttr("code",BaseError.OPERATION_ERR.getCode());
        this.setAttr("message",msg);
    }

    public void setOperateErr(){
        this.setAttr("code",BaseError.OPERATION_ERR.getCode());
        this.setAttr("message",BaseError.OPERATION_ERR.getMsg());
    }

    public void setDeleteErr(){
        this.setAttr("code",BaseError.OPERATION_ERR.getCode());
        this.setAttr("message",BaseError.OPERATION_ERR.getMsg());
    }


    public void addCookie(String key,String value,int second) {
        Cookie cookie = new Cookie(key,value);
        cookie.setDomain(PropKit.get("COOKIE_DOMAIN"));
        cookie.setMaxAge(second);
        cookie.setPath("/");
        setCookie(cookie);
    }


    public JSONObject getPostParam(){
        String jsonString= HttpKit.readData(getRequest());
        return JSONObject.parseObject(jsonString);
    }

    public String getUid(){
        return this.getCookie("uid");
    }

    //分页处理
    @SuppressWarnings("rawtypes")
    protected Map<String, Object> buildPagination(List list, Integer count) {
        return buildPagination(list, count, null);
    }

    @SuppressWarnings("rawtypes")
    protected Map<String, Object> buildPagination(List list, Integer count,
                                                  List<Map<String, Object>> footer) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", count);
        map.put("rows", list);
        if (footer != null){
            map.put("footer", footer);
        }
        return map;
    }

    protected static int getPageNum(int pageNum,int rows){
        int pageNumber = pageNum / rows + 1;
        return pageNumber;
    }

    /**
     * 获取CoreKey
     * @return
     */
    public String getCoreKey() throws BaseException {
        String coreKey = getPara("_ck", "");
        if (StringUtils.isEmpty(coreKey)){
            throw new BaseException("CoreKey为空");
        }
        return coreKey;
    }

}
