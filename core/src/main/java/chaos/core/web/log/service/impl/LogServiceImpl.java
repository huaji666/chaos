package chaos.core.web.log.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import chaos.core.web.model.ParamBST;
import chaos.core.web.dao.LogModelMapper;
import chaos.core.web.log.service.LogService;
import chaos.core.web.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ©土土网 shop
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-03-23
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogModelMapper logModelMapper;

    @Override
    public int insert(LogModel record) {
        return logModelMapper.insert(record);
    }

    @Override
    public int insertSelective(LogModel record) {
        return logModelMapper.insertSelective(record);
    }

    @Override
    public LogModel selectByPrimaryKey(Long id) {
        return logModelMapper.selectByPrimaryKey(id);
    }

    @Override
    public void getLogByUserID(long id) {
        logModelMapper.getLogByUserID(id);
    }

    @Override
    public PageInfo<LogModel> selectAll(ParamBST model) {
        PageHelper.offsetPage(model.getOffset(), model.getLimit(), model.getSort());
        List<LogModel> list = logModelMapper.selectAll();
        return new PageInfo<>(list);
    }
}
