package chaos.core.web.exception.service;


import chaos.core.web.model.ExceptionModel;
import chaos.core.web.model.ParamBST;
import chaos.core.web.ucenter.UCenter;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 异常操作
 * <p>
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-12
 */
public interface ExceptionService {

    /**
     * 添加异常记录
     *
     * @return
     */
    boolean insertExceptionModel(UCenter uCenter, String title, String context);

    /**
     * @param title
     * @param context
     * @return
     */
    boolean insertExceptionModel(String title, String context);

    /**
     * 添加异常
     * @return
     */
    boolean insertExceptionModel(Throwable throwable);

    /**
     * 添加异常管理
     * @return
     */
    boolean insertExceptionModel(UCenter uCenter, Throwable throwable);

    /**
     * 查询异常 根据用户
     *
     * @param uCenter
     * @return
     */
    List<ExceptionModel> selectByUserId(UCenter uCenter);

    /**
     * 查询异常根据 时间
     *
     * @param start
     * @param end
     * @return
     */
    List<ExceptionModel> selectByCreateDate(long start, long end);


    /**
     * @param model
     * @return
     */
    PageInfo<ExceptionModel> selectAll(ParamBST model);

}
