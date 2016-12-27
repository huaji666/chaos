package chaos.core.web.upfile.service.impl;

import chaos.core.utils.ExcelReader;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.constant.CommonKey;
import chaos.core.web.context.Properties;
import chaos.core.web.dao.UpFileModelMapper;
import chaos.core.web.model.UpFileModel;
import chaos.core.web.upfile.service.CallBack;
import chaos.core.web.upfile.service.UpFileService;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * ©chaos-parent
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-09-11
 */
@Service
public class UpFileServieImpl implements UpFileService {

    private static final Logger log = Logger.getLogger(UpFileServieImpl.class);

    @Autowired
    UpFileModelMapper upFileModelMapper;

//    @Override
//    public UpFileModel selectByCode(long code) {
//        UpFileModel model = upFileModelMapper.selectByCode(String.valueOf(code));
//        return model;
//    }
//
//    @Override
//    public String selectByCodes(long[] codes) {
//
//        String[] imgs = StringUtils.split(codes, ",");
//
//        for (int i = 0; i < imgs.length; i++) {
//            UpFileModel upFileModel = selectByCode(imgs[i]);
//            if (upFileModel == null) {
//                log.warn("没有找到model！");
//            } else {
//                imgs[i] = upFileModel.getPath();
//            }
//        }
//        return StringUtils.join(imgs, ",");
//    }
//
//    @Override
//    public String selectByCodes(String codes, String upFileDir) {
//
//        String[] imgs = StringUtils.split(codes, ",");
//
//        for (int i = 0; i < imgs.length; i++) {
//            UpFileModel model = selectByCode(imgs[i]);
//            moveDir(upFileDir, model);
//            imgs[i] = model.getPath();
//        }
//        return StringUtils.join(imgs, ",");
//    }
//
//
//    @Override
//    public UpFileModel selectByCode(String code, String upFileDir) {
//        UpFileModel model = upFileModelMapper.selectByCode(code);
//
//        return moveDir(upFileDir, model);
//    }

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

//    @Override
//    public CaseRes save(MultipartFile[] files, String[] codes) {
//        if (ObjectUtils.isEmpty((Object) files)) return CaseRes.getInstance().setMessage(CommonMessage.upFile.isEmpty);
//
//
//        List models = new ArrayList<>();
//        UpFileModel upFileModel;
//        for (int i = 0; i < files.length; i++) {
//            upFileModel = saveFile(files[i], codes[i] == null ? null : codes[i]);
//            models.add(upFileModel);
//        }
//        // 重定向
//        return CaseRes.getInstance().setData(models);
//    }

//    @Override
//    public CaseRes save(MultipartFile files, String codes) {
//        return save(new MultipartFile[]{files}, new String[]{codes});
//    }

    @Override
    public UpFileModel save(MultipartFile file, String upFileDir, long code) {

        UpFileModel upFileModel = saveFile(file, String.valueOf(code));

        return upFileModel;
    }

    @Override
    public List<UpFileModel> save(MultipartFile[] files, String upFileDir) {
        return null;
    }

    @Override
    public List<UpFileModel> save(MultipartFile[] files) {
        return null;
    }

    @Override
    public void save(MultipartFile[] files, long[] codes) {

    }

    @Override
    public void save(MultipartFile[] files, String upFileDir, long[] codes) {

    }

    @Override
    public String selectPathByCode(long code) {
        String def = "";
        UpFileModel upFileModel = selectByCode(code);
        if (upFileModel == null) return def;
        if (!ObjectUtils.isEmpty(upFileModel.getPath())) def = upFileModel.getPath();
        return def;
    }

    @Override
    public String[] selectPathsByCodes(long[] codes) {
        String[] def = new String[0];
        if (ObjectUtils.isEmpty((Object) codes)) return def;
        String temp = StringUtils.join(codes, ",");
        List<UpFileModel> models = upFileModelMapper.selectByCodes(temp);
        if (ObjectUtils.isEmpty(models)) return def;
        List<String> stringList = models.stream().map(UpFileModel::getPath).collect(Collectors.toList());
        stringList.toArray(def);

        return def;
    }

    @Override
    public List<UpFileModel> selectByCodes(long[] codes) {
        return upFileModelMapper.selectByCodes(StringUtils.join(codes, ","));
    }

    @Override
    public UpFileModel selectByCode(long code) {
        return upFileModelMapper.selectByCode(String.valueOf(code));
    }

    @Override
    public void saveAsync(MultipartFile file, CallBack<UpFileModel> callBack) {

    }

    @Override
    public void saveAsync(MultipartFile file, CallBack<UpFileModel> callBack, long code) {

    }

    @Override
    public void saveAsync(MultipartFile file, String upFileDir, CallBack<UpFileModel> callBack) {

    }

    @Override
    public void saveAsync(MultipartFile file, String upFileDir, CallBack<UpFileModel> callBack, long code) {

    }

    @Override
    public void saveAsync(MultipartFile[] files, String upFileDir, CallBack callBack) {

    }

    @Override
    public void saveAsync(MultipartFile[] files, CallBack<List<UpFileModel>> callBack) {

    }

    @Override
    public void saveAsync(MultipartFile[] files, CallBack<UpFileModel> callBack, long[] codes) {

    }

    @Override
    public void saveAsync(MultipartFile[] files, String upFileDir, CallBack<List<UpFileModel>> callBack, long[] codes) {

    }

    @Override
    public UpFileModel save(MultipartFile file) {
        UpFileModel upFileModel = saveFile(file);
        return upFileModel;
    }

    @Override
    public void save(MultipartFile file, long code) {

    }

    @Override
    public UpFileModel save(MultipartFile file, String upFileDir) {
        return null;
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
                upFileModel.setStatus((short) 100);
                upFileModelMapper.insert(upFileModel);
                // 转存文件
                file.transferTo(new File(filePath));

//                im4JavaService.quality(30.0, filePath, filePath + "aaaa.png");

            } catch (Exception e) {
                log.error(e);
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


    @Override
    public List<List<List<List<String>>>> getExcel(String[] codesOrPaths) {
        List<List<List<List<String>>>> defList = new ArrayList<>();
        if (ObjectUtils.isEmpty((Object) codesOrPaths)) return defList;
        List<List<List<String>>> workBookList = new ArrayList<>();
        for (String codeOrPath : codesOrPaths) {
            UpFileModel upFileModel = upFileModelMapper.selectByCodeOrPath(codeOrPath);
            if (ObjectUtils.isEmpty(upFileModel)) continue;
            String filePath = Properties.file.getString(CommonKey.Propertie.file.upload_ads) + upFileModel.getPath();

            Workbook workbook = null;
            try {
                workbook = ExcelReader.createWb(filePath);
                List<List<String>> shellList = null;
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    shellList = new ArrayList<>();
                    Sheet sheet = workbook.getSheetAt(i);
                    if (sheet == null) continue;//过滤空工作簿

                    Cell cell;
                    List<String> rowList = null;
                    for (int j = 0; j < sheet.getLastRowNum() + 1; j++) {
                        rowList = new ArrayList<>();
                        Row row = sheet.getRow(j);
                        if (row == null) continue;//过滤空行

                        for (int k = 0; k < row.getLastCellNum(); k++) {
                            cell = row.getCell(k);
                            if (cell == null) {
                                rowList.add("");
                                continue;
                            }
                            cell.setCellType(Cell.CELL_TYPE_STRING);
                            rowList.add(cell.getStringCellValue());
                        }
                        shellList.add(rowList);
                    }
                    workBookList.add(shellList);
                }
                defList.add(workBookList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return defList;
    }

    @Override
    public List<List<List<String>>> getExcel(String codeOrPath) {
        List<List<List<String>>> defList = new ArrayList<>();
        List<List<List<List<String>>>> excel = getExcel(new String[]{codeOrPath});
        if (ObjectUtils.isEmpty(excel)) return defList;
        if (excel.isEmpty()) return defList;
        defList.addAll(excel.get(0));
        return defList;
    }

    @Override
    public UpFileModel downloadFile(byte[] byts, String upFileDir) {
        if (ObjectUtils.isEmpty((Object) byts)) return null;

        String newFileName = getNewFileName();
        String filePath = getFilePathAbs(upFileDir) + newFileName;
        File file = new File(filePath);
        UpFileModel temp = null;
        try {
            FileUtils.writeByteArrayToFile(file, byts);

            temp = new UpFileModel();
            temp.setName(newFileName);
            temp.setCode(newFileName);
            temp.setFileType(null);
            temp.setPath(getFilePath(upFileDir) + newFileName);
            temp.setCreateDate(String.valueOf(System.currentTimeMillis()));
            temp.setStatus((short) 100);
            upFileModelMapper.insert(temp);
        } catch (IOException e) {
            log.warn("下载失败：", e);
            return temp;
        }
        return temp;

    }

    /**
     * 取绝对地址
     *
     * @param upFileDir
     * @return
     */
    private String getFilePathAbs(String upFileDir) {
        if (ObjectUtils.isEmpty(upFileDir)) return Properties.file.getString(CommonKey.Propertie.file.upload_temp_ads);
        upFileDir = File.separator + upFileDir + File.separator;
        return Properties.file.getString(CommonKey.Propertie.file.upload_ads) + upFileDir;
    }

    /**
     * 取相对地址
     *
     * @param upFileDir
     * @return
     */
    private String getFilePath(String upFileDir) {
        if (ObjectUtils.isEmpty(upFileDir)) return Properties.file.getString(CommonKey.Propertie.file.upload_temp);
        upFileDir = File.separator + upFileDir + File.separator;
        return upFileDir;
    }

    @Override
    public UpFileModel downloadFile(String url) {
        return downloadFile(url, null, 0);
    }

    public interface Callback {
        void callBack(UpFileModel model);
    }

    @Override
    public UpFileModel downloadFile(String url, String upFileDir) {
        return downloadFile(url, upFileDir, 0);
    }

    @Override
    public UpFileModel downloadFile(String url, String upFileDir, long code) {

        if (ObjectUtils.isEmpty(url)) return new UpFileModel();


        if (code > 0) code = Long.parseLong(getNewFileName());
        // 文件保存路径
        String fileExtension = Files.getFileExtension(url);
        String newFileName = String.valueOf(code);
        // 添加后缀
        if (!ObjectUtils.isEmpty(fileExtension)) {
            newFileName = code + "." + fileExtension;
        }
//        else {
//            newFileName = code + ".png";
//        }

        String filePath = Properties.file.getString(CommonKey.Propertie.file.upload_temp_ads) + newFileName;

//        String url = wechatRequest.getPicUrl();
//        HttpClient
        HttpClient httpclient = new DefaultHttpClient();
        //设置代理
//        String dir = Properties.file.getString(CommonKey.Propertie.file.upload_temp_ads) + getNewFileName();
//        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, new HttpHost("192.168.0.101", 3128));
        HttpGet httpget = new HttpGet(url);
        HttpResponse response = null;
        InputStream input = null;
        File file = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            input = entity.getContent();
            file = new File(filePath);
            FileOutputStream output = FileUtils.openOutputStream(file);
            try {
                IOUtils.copy(input, output);
            } finally {
                IOUtils.closeQuietly(output);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }


        UpFileModel temp = new UpFileModel();
        temp.setName(newFileName);
        temp.setCode(String.valueOf(code));
        temp.setFileType(null);
        temp.setPath(Properties.file.getString(CommonKey.Propertie.file.upload_temp) + newFileName);
        temp.setCreateDate(String.valueOf(System.currentTimeMillis()));
        temp.setStatus((short) 0);
        upFileModelMapper.insert(temp);
        // 转存文件

        UpFileModel upFileModel = selectByCode(code);


        return upFileModel;
    }

    @Async
    @Override
    public void downloadFileAsync(String url, Callback callback) {
        downloadFileAsync(url, null, 0, callback);
    }

    @Async
    @Override
    public void downloadFileAsync(byte[] byts, String upFileDir, Callback callback) {
        UpFileModel upFileModel = downloadFile(byts, upFileDir);
        if (!ObjectUtils.isEmpty(upFileDir)) callback.callBack(upFileModel);
    }

    @Async
    @Override
    public void downloadFileAsync(String url, String upFileDir, Callback callback) {
        downloadFileAsync(url, upFileDir, 0, callback);
    }

    @Async
    @Override
    public void downloadFileAsync(String url, String upFileDir, long code, Callback callback) {
        UpFileModel upFileModel = downloadFile(url, upFileDir, code);
        callback.callBack(upFileModel);
    }

}
