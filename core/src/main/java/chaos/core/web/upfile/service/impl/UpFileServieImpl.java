package chaos.core.web.upfile.service.impl;

import chaos.core.model.CaseRes;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.constant.CommonKey;
import chaos.core.web.constant.CommonMessage;
import chaos.core.web.upfile.service.UpFileService;
import chaos.core.web.context.Properties;
import chaos.core.web.dao.UpFileModelMapper;
import chaos.core.web.model.UpFileModel;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-11
 */
@Service
public class UpFileServieImpl implements UpFileService {

    private static final Logger log = Logger.getLogger(UpFileServieImpl.class);

    @Autowired
    UpFileModelMapper upFileModelMapper;

    @Override
    public UpFileModel selectByCode(String code) {
        UpFileModel model = upFileModelMapper.selectByCode(code);
        return model;
    }

    @Override
    public String selectByCodes(String codes) {

        String[] imgs = StringUtils.split(codes, ",");

        for (int i = 0; i < imgs.length; i++) {
            UpFileModel upFileModel = selectByCode(imgs[i]);
            if (upFileModel == null) {
                log.warn("没有找到model！");
            } else {
                imgs[i] = upFileModel.getPath();
            }
        }
        return StringUtils.join(imgs, ",");
    }

    @Override
    public String selectByCodes(String codes, String upFileDir) {

        String[] imgs = StringUtils.split(codes, ",");

        for (int i = 0; i < imgs.length; i++) {
            UpFileModel model = selectByCode(imgs[i]);
            moveDir(upFileDir, model);
            imgs[i] = model.getPath();
        }
        return StringUtils.join(imgs, ",");
    }


    @Override
    public UpFileModel selectByCode(String code, String upFileDir) {
        UpFileModel model = upFileModelMapper.selectByCode(code);

        return moveDir(upFileDir, model);
    }

    /**
     * 文件移动
     *
     * @param upFileDir
     * @param model
     * @return
     */
    private UpFileModel moveDir(String upFileDir, UpFileModel model) {
        if (model.getPath().contains(upFileDir)) return model;

        String src = Properties.file.getString(CommonKey.Propertie.file.upload_ads) + model.getPath();
        model.setPath(model.getPath().replace(Properties.file.getString(CommonKey.Propertie.file.upload_temp), File.separator + upFileDir + File.separator));
        String tag = Properties.file.getString(CommonKey.Propertie.file.upload_ads) + model.getPath();

        try {
            FileUtils.moveFile(new File(src), new File(tag));
            upFileModelMapper.updateByPrimaryKeySelective(model);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return model;
    }

    @Override
    public CaseRes insert(MultipartFile[] files, String[] codes) {
        if (ObjectUtils.isEmpty((Object) files)) return CaseRes.getInstance().setMessage(CommonMessage.upFile.isEmpty);


        List models = new ArrayList<>();
        UpFileModel upFileModel;
        for (int i = 0; i < files.length; i++) {
            upFileModel = saveFile(files[i], codes[i] == null ? null : codes[i]);
            models.add(upFileModel);
        }
        // 重定向
        return CaseRes.getInstance().setData(models);
    }

    @Override
    public CaseRes insert(MultipartFile files, String codes) {
        return insert(new MultipartFile[]{files}, new String[]{codes});
    }


    /***
     * 保存文件
     *
     * @param file
     * @return
     */
    private UpFileModel saveFile(MultipartFile file) {
        return saveFile(file, null);
    }

    /***
     * 保存文件
     *
     * @param file
     * @return
     */
    private UpFileModel saveFile(MultipartFile file, String code) {



        // 判断文件是否为空
        UpFileModel upFileModel = null;
        if (!file.isEmpty()) {
            try {

                String originalName = file.getOriginalFilename();


                if (ObjectUtils.isEmpty(code)) code = getNewFileName();
                // 文件保存路径
                String newFileName = code + "." + Files.getFileExtension(originalName);

                String filePath = Properties.file.getString(CommonKey.Propertie.file.upload_temp_ads) + newFileName;

                upFileModel = new UpFileModel();
                upFileModel.setName(originalName);
                upFileModel.setCode(code);
                upFileModel.setFileType(file.getContentType());
                upFileModel.setPath(Properties.file.getString(CommonKey.Propertie.file.upload_temp) + newFileName);
                upFileModel.setCreateDate(String.valueOf(System.currentTimeMillis()));
                upFileModel.setStatus((short) 0);
                upFileModelMapper.insert(upFileModel);
                // 转存文件
                file.transferTo(new java.io.File(filePath));

//                im4JavaService.quality(30.0, filePath, filePath + "aaaa.png");

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return upFileModel;
    }

    /**
     * 获取文件名称
     *
     * @return
     */
    private String getNewFileName() {
        StringBuilder sb = new StringBuilder();
        int year = Calendar.getInstance(Locale.CHINA).get(Calendar.YEAR);
        int march = Calendar.getInstance(Locale.CHINA).get(Calendar.MARCH) + 1;
        int day = Calendar.getInstance(Locale.CHINA).get(Calendar.DAY_OF_MONTH);
        int hour = Calendar.getInstance(Locale.CHINA).get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance(Locale.CHINA).get(Calendar.MINUTE);
        int second = Calendar.getInstance(Locale.CHINA).get(Calendar.SECOND);
        return sb.append(year).append(march).append(day).append(hour).append(minute).append(second).append(RandomStringUtils.randomNumeric(4)).toString();
    }

//    /**
//     * @param originalFilename
//     * @return
//     */
//    private String newFileName(String originalFilename) {
//        String type = originalFilename.substring(originalFilename.lastIndexOf("."));
//        System.out.println(Files.getFileExtension(originalFilename));
//        return System.currentTimeMillis() + "" + RandomUtils.patchRandom(4) + type;
//    }
}
