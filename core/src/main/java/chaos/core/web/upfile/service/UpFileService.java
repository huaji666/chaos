package chaos.core.web.upfile.service;

import chaos.core.model.CaseRes;
import chaos.core.model.CaseRes;
import chaos.core.web.model.UpFileModel;
import org.springframework.web.multipart.MultipartFile;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)

 * 时间：2016-09-11
 */
public interface UpFileService {
    /**
     * 根据code 获取上传文件信息
     *
     * @param code
     * @return
     */
    UpFileModel selectByCode(String code);

    /**
     * 根据codes 获取paths
     *
     * @param codes
     * @return
     */
    String selectByCodes(String codes);

    /**
     * 根据codes 获取paths
     *
     * @param codes
     * @return
     */
    String selectByCodes(String codes, String upFileDir);

    /**
     * 根据code 获取上传文件信息
     *
     * @param code
     * @param upFileDir 移动文档到其他目录
     * @return
     */
    UpFileModel selectByCode(String code, String upFileDir);

    /**
     * 保存文件
     *
     * @param files
     * @param codes
     * @return
     */
    CaseRes insert(MultipartFile[] files, String[] codes);

    /**
     * 保存文件
     * @param files
     * @param codes
     * @return
     */
    CaseRes insert(MultipartFile files, String codes);

}
