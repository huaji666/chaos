package chaos.core.web.region.service;

import chaos.core.web.model.RegionModel;

import java.util.List;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-08-15
 */
public interface RegionService {

    /**
     * 0 为一级菜单
     *
     * @return
     */
    List<RegionModel> getRegion(int parentId);

    String getAddressByCode(String code);

}
