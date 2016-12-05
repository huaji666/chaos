package chaos.core.web.exception.service.impl;

import chaos.core.web.dao.ExceptionModelMapper;
import chaos.core.web.model.ExceptionModel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import chaos.core.web.model.ParamBST;
import chaos.core.web.context.CommonRequestHelper;
import chaos.core.web.exception.service.ExceptionService;
import chaos.core.web.ucenter.UCenter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-23
 */

@Service
public class ExceptionServiceImpl implements ExceptionService {

    @Autowired
    ExceptionModelMapper exceptionModelMapper;

    /**
     * 添加异常记录
     *
     * @param uCenter
     * @param title
     * @param context
     * @return
     */
    @Override
    public boolean insertExceptionModel(UCenter uCenter, String title, String context) {

        if (uCenter == null) return insertExceptionModel(title, context);
        try {
            ExceptionModel model = new ExceptionModel();
            model.setUserId(uCenter.getUcenterId());
            model.setIp(CommonRequestHelper.getInstance().getIp());
            model.setTitle(StringUtils.abbreviate(title, 200));
            model.setContext(context);
            model.setDevice(CommonRequestHelper.getInstance().getDevice());
            model.setCreateDate(String.valueOf(System.currentTimeMillis()));
            return exceptionModelMapper.insert(model) > 0;
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * @param title
     * @param context
     * @return
     */
    @Override
    public boolean insertExceptionModel(String title, String context) {
        try {
            ExceptionModel model = new ExceptionModel();
            model.setIp(CommonRequestHelper.getInstance().getIp());
            model.setTitle(StringUtils.abbreviate(title, 200));
            model.setContext(context);
            model.setDevice(CommonRequestHelper.getInstance().getDevice());
            model.setCreateDate(String.valueOf(System.currentTimeMillis()));
            return exceptionModelMapper.insert(model) > 0;
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public boolean insertExceptionModel(Throwable throwable) {

        try {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) sb.append(stackTraceElement.toString());
            return insertExceptionModel(throwable.toString(), sb.toString());
        } catch (Exception ignored) {
        }
        return false;
    }

    @Override
    public boolean insertExceptionModel(UCenter uCenter, Throwable throwable) {

        try {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) sb.append(stackTraceElement.toString());
            return insertExceptionModel(uCenter, throwable.toString(), sb.toString());
        } catch (Exception ignored) {
        }
        return false;
    }

    /**
     * 查询异常 根据用户
     *
     * @param uCenter
     * @return
     */
    @Override
    public List<ExceptionModel> selectByUserId(UCenter uCenter) {
        return null;
    }

    /**
     * 查询异常根据 时间
     *
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<ExceptionModel> selectByCreateDate(long start, long end) {
        return null;
    }


    @Override
    public PageInfo<ExceptionModel> selectAll(ParamBST model) {

        PageHelper.offsetPage(model.getOffset(), model.getLimit(), model.getSort());
        List<ExceptionModel> list = exceptionModelMapper.selectAll();
        return new PageInfo<>(list);
    }
}
