package com.wya.pub;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.wya.pub.enums.ResponseEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author wya
 * @version 1.0
 * @date 2020/12/22 15:20
 * @Description: No Description
 */
public abstract class BaseController<T>  {
    private static Logger logger = LoggerFactory.getLogger(BaseController.class);

    public abstract BaseService<T> getService();

    public String getEnv(ShopModel shopModel) {
        return shopModel.getEnv();
    }

    public PageInfo<T> pageList(T t, int pageNum, int pageSize, boolean count) {
        return this.getService().pageList(t, pageNum, pageSize, count);
    }

    /**
     * @Description: 返回不带数据的响应结果
     * @CreatedBy: lantaimin
     * @CreatedDate: 2019/1/3 14:12
     * @LastModifiedBy: lantaimin
     * @LastModifiedDate: 2019/1/3 14:12
     * @parms [returnCode, returnMsg]
     * @return com.yilutong.crm.vo.ResponseVO
     */
    public static ResponseVo returnResponse(ResponseEnum responseEnum) {
        logger.info("responseVo[code={}, message={}]", responseEnum.getCode(), responseEnum.getMessage());
        return new ResponseVo(responseEnum);
    }

    /**
     * @Description: 返回带数据的响应结果
     * @CreatedBy: lantaimin
     * @CreatedDate: 2019/1/3 14:13
     * @LastModifiedBy: lantaimin
     * @LastModifiedDate: 2019/1/3 14:13
     * @parms [returnCode, returnMsg, data]
     * @return com.yilutong.crm.vo.ResponseVO
     */
    public static <T> ResponseVo returnResponse(ResponseEnum responseEnum, T data) {
        logger.info("responseVo[code={}, message={}]", responseEnum.getCode(), responseEnum.getMessage());
        ResponseVo responseBean = new ResponseVo(responseEnum);
        if (null != data) {
            responseBean.setData(data);
            logger.info("responseVo[data={}]", JSON.toJSONString(data));
        }
        return responseBean;
    }

    public static <T> ResponseVo returnResponse(ResponseEnum responseEnum, PageInfo<T> pageInfo) {
        logger.info("responseVo[code={}, message={}]", responseEnum.getCode(), responseEnum.getMessage());
        ResponseVo responseBean = new ResponseVo(responseEnum);
        if (null != pageInfo) {
            responseBean.setPageInfo(pageInfo);
            logger.info("responseVo[pageInfo={}]", JSON.toJSONString(pageInfo));
        }
        return responseBean;
    }

    public static ResponseVo returnSuccess() {
        return returnResponse(ResponseEnum.SUCCESS);
    }

    public static <T> ResponseVo returnSuccess(T data) {
        return returnResponse(ResponseEnum.SUCCESS, data);
    }

    public static <T> ResponseVo returnSuccess(PageInfo<T> pageInfo) {
        return returnResponse(ResponseEnum.SUCCESS, pageInfo);
    }

    public static ResponseVo returnFail() {
        return returnFail(ResponseEnum.SYSTEM_INNER_ERROR);
    }

    public static ResponseVo returnFail(ResponseEnum responseEnum) {
        return returnResponse(responseEnum);
    }
}
