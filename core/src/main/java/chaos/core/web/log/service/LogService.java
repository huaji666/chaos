package chaos.core.web.log.service;


import chaos.core.web.model.LogModel;
import com.github.pagehelper.PageInfo;
import chaos.core.web.model.ParamBST;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-03-23
 */
public interface LogService  {

    int insert(LogModel record);

    int insertSelective(LogModel record);

    LogModel selectByPrimaryKey(Long id);

    void getLogByUserID(long id);

    PageInfo<LogModel> selectAll(ParamBST model);
}
