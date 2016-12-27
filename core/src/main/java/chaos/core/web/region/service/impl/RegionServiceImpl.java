package chaos.core.web.region.service.impl;

import chaos.core.web.constant.CommonKey;
import chaos.core.web.model.RegionModel;
import chaos.core.web.region.service.RegionService;
import chaos.core.web.dao.RegionModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ©chaos-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-15
 */
@Service
public class RegionServiceImpl implements RegionService {

    @Autowired
    RegionModelMapper regionModelMapper;


    private void toDb(RegionModel model) {
        RegionModel temp, temp1, temp2;
//        for (RegionModel regionModel : model.getState()) {
        temp = new RegionModel();
        temp.setParentId((short) 0);
        temp.setName(model.getName());
        temp.setCode((short) model.getCode());
        regionModelMapper.insert(temp);
        for (RegionModel regionModel2 : model.getChild()) {
            temp1 = new RegionModel();
            temp1.setParentId(temp.getId());
            temp1.setName(regionModel2.getName());
            temp1.setCode((short) regionModel2.getCode());
            regionModelMapper.insert(temp1);
            for (RegionModel regionModel1 : regionModel2.getChild()) {
                temp2 = new RegionModel();
                temp2.setParentId(temp1.getId());
                temp2.setName(regionModel1.getName());
                temp2.setCode((short) regionModel1.getCode());
                regionModelMapper.insert(temp2);
            }
        }
//        }
    }


    /**
     * 0 为一级菜单
     *
     * @param parentId
     * @return
     */
    @Cacheable(cacheNames = CommonKey.Cache.region, key = "#" + CommonKey.Cache.Region.parentId)
    @Override
    public List<RegionModel> getRegion(int parentId) {

        List<RegionModel> list = regionModelMapper.selectByParentId(parentId);

        return list;
    }

    @Cacheable(cacheNames = CommonKey.Cache.region)
    @Override
    public String getAddressByCode(String code) {
        return null;
    }

    public static void main(String[] args) {

    }

}
