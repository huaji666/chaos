package chaos.core.web.upfile;

import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.constant.CommonKey;
import chaos.core.web.upfile.service.UpFileService;
import chaos.core.web.context.Properties;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;

/**
 * ©app-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-11
 */
public class UpFileHelper {

    private static final Logger log = Logger.getLogger(UpFileHelper.class);

    private static UpFileHelper instance;
    private UpFileService upFileService;
    private ApplicationContext context;

    public static UpFileHelper getInstance() {
        Assert.notNull(instance, "UpFileHelper 还未初始化！");
        return instance;
    }

    private UpFileHelper(ApplicationContext context) {
        this.context = context;
        upFileService = context.getBean(UpFileService.class);
    }

//    }

    public static void initFielDir(ApplicationContext context) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        if (instance != null) return;
        instance = new UpFileHelper(context);
        initFielDir(context, null);
    }

    public static void initFielDir(ApplicationContext context, String dir) {
        Assert.notNull(context, "ApplicationContext 不能为空！");
        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝UpFile初始化＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        java.lang.String server, uploadDir, uploadAbsDir, tempDir, goodsDir, categoryDir, brandDir, tempAbsDir, goodsAbsDir, brandAbsDir, categoryAbsDir;

        uploadDir = Properties.file.getString(CommonKey.Propertie.file.upload);
        if (uploadDir == null || uploadDir.length() == 0) {
            uploadDir = context.getApplicationName() + "Data";
            Properties.file.setProperty(CommonKey.Propertie.file.upload, uploadDir);
        }


        tempDir = Properties.file.getString(CommonKey.Propertie.file.upload_temp);
        if (tempDir == null || tempDir.length() == 0) {
            tempDir = File.separator + String.temp + File.separator;
            Properties.file.setProperty(CommonKey.Propertie.file.upload_temp, tempDir);
        }
        uploadAbsDir = Properties.file.getString(CommonKey.Propertie.file.upload_ads);
        if (ObjectUtils.isEmpty(uploadAbsDir) || !new File(uploadAbsDir).exists()) {
            java.lang.String bitPath = null;
            long tempLong = 0;
            for (File f : File.listRoots()) {
//                System.out.println(f.getPath());
//                System.out.println(f.getTotalSpace());//获取该卷大小(单位：字节)
//                System.out.println(f.getFreeSpace());//获取该卷可用大小(单位：字节)
                if (f.getFreeSpace() > tempLong) {
                    tempLong = f.getFreeSpace();
                    bitPath = f.getPath();
                }
            }
            uploadAbsDir = bitPath + uploadDir;
//            System.out.println("创建目录");
            log.info("目录创建----->" + uploadAbsDir);
//            propertiesUtil.setProperty("file.upload", file_upload);
            try {
                FileUtils.forceMkdir(new File(uploadAbsDir));
//                PropertiesConfiguration propertiesUtil = PropertiesUtil.getInstance("config.properties");
//                PropertiesConfiguration config = new PropertiesConfiguration("config.properties");
//                config.setEncoding("utf8");
//                propertiesUtil.setProperty("file.upload", file_upload);
                Properties.file.setProperty(CommonKey.Propertie.file.upload_ads, uploadAbsDir);
//                config.save();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }

//        Properties.file.setProperty(Key.Propertie.file.upload_ads, uploadAbsDir);

        server = Properties.file.getString(CommonKey.Propertie.file.upload_server);

        try {
            tempAbsDir = Properties.file.getString(CommonKey.Propertie.file.upload_temp_ads);
            if (tempAbsDir == null || tempAbsDir.length() == 0) {
                tempAbsDir = uploadAbsDir + tempDir;
                Properties.file.setProperty(CommonKey.Propertie.file.upload_temp_ads, tempAbsDir);
                FileUtils.forceMkdir(new File(tempAbsDir));
                log.info("目录创建----->" + tempAbsDir);
            }
//            goodsAbsDir = Properties.file.getString(Key.Propertie.File.upload_user_ads);
//            if (goodsAbsDir == null || goodsAbsDir.length() == 0) {
//                goodsAbsDir = uploadAbsDir + goodsDir;
//                Properties.file.setProperty(Key.Propertie.File.upload_user_ads, goodsAbsDir);
//                FileUtils.forceMkdir(new File(goodsAbsDir));
//                log.info("目录创建----->" + goodsAbsDir);
//            }
//
//            brandAbsDir = Properties.file.getString(Key.Propertie.File.upload_project_ads);
//            if (brandAbsDir == null || brandAbsDir.length() == 0) {
//                brandAbsDir = uploadAbsDir + brandDir;
//                Properties.file.setProperty(Key.Propertie.File.upload_project_ads, brandAbsDir);
//                FileUtils.forceMkdir(new File(brandAbsDir));
//                log.info("目录创建----->" + brandAbsDir);
//            }
//
//            categoryAbsDir = Properties.file.getString(Key.Propertie.File.upload_category_ads);
//            if (categoryAbsDir == null || categoryAbsDir.length() == 0) {
//                categoryAbsDir = uploadAbsDir + categoryDir;
//                Properties.file.setProperty(Key.Propertie.File.upload_category_ads, categoryAbsDir);
//                FileUtils.forceMkdir(new File(categoryAbsDir));
//                log.info("目录创建----->" + categoryAbsDir);
//            }

            FileUtils.forceMkdir(new File(uploadAbsDir));
            log.info("目录校验----->" + uploadAbsDir);
            FileUtils.forceMkdir(new File(tempAbsDir));
            log.info("目录校验----->" + tempAbsDir);
//            FileUtils.forceMkdir(new File(goodsAbsDir));
//            log.info("目录校验----->" + goodsAbsDir);
//            FileUtils.forceMkdir(new File(brandAbsDir));
//            log.info("目录校验----->" + brandAbsDir);
//            FileUtils.forceMkdir(new File(categoryAbsDir));
//            log.info("目录校验----->" + categoryAbsDir);
//            System.out.println("上传目录检查");
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝UpFile初始化完成＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
    }

    public UpFileService getUpFileService() {
        return upFileService;
    }
}
