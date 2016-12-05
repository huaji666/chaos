package chaos.core.web.controller;

import chaos.core.model.CaseRes;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.api.annoatation.Api;
import chaos.core.web.constant.CommonKey;
import chaos.core.web.constant.CommonMessage;
import chaos.core.web.context.CommonController;
import chaos.core.web.context.Properties;
import chaos.core.web.email.service.EmailService;
import chaos.core.web.kaptcha.service.CaptchaService;
import chaos.core.web.model.CaseResBST;
import chaos.core.web.model.RegionModel;
import chaos.core.web.sms.interfaces.SmsSend;
import chaos.core.web.sms.interfaces.impl.SmsSendImpl;
import chaos.core.web.upfile.service.UpFileService;
import com.baidu.ueditor.ActionEnter;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.google.code.kaptcha.Constants;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

@Api(value = "Commons-web控制器")
@Controller
@RequestMapping("/")
public class TestController extends CommonController {


    @RequestMapping("test/test1")
    public ModelAndView test1(@RequestParam(value = "value", defaultValue = "World") String name) {
        System.out.println("Hello " + name);
        Map<String, Object> map = new HashMap<>();
        map.put("userName", name);
        return new ModelAndView("/hello", map);
    }


    /**
     * 测试返回JSON数据
     */
    @Api(value = "测试:方法2", params = {"value", "phone", "code"})
    @RequestMapping("test/test2")
    @ResponseBody
    public CaseRes test2(@RequestBody(required = false) Object paramReq,
                         @RequestParam(required = false) String phone, Model model) {
        System.out.println("Test......999995555666777888..............");
        return CaseRes.getInstance().setData("adfasfddddffffwwwwwwee");
    }

    /**
     * 测试返回JSON数据
     */
    @Api(value = "测试:方法3", params = {"value", "phone", CommonKey.session.sCode})
    @RequestMapping("test/test3")
    @ResponseBody
    public CaseRes test3(@RequestBody(required = false) Object paramReq,
                         @RequestParam(required = false) String phone, Model model) {
        System.out.println("Test......999995555666777888..............");
        SmsSend smsSend = new SmsSendImpl();
        smsSend.testSend();
        return CaseRes.getInstance().setData("adfasfddddffffwwwwwwee");
    }

    /**
     * 测试返回JSON数据
     */
    @Api(value = "测试方法4", params = {"value", "phone", CommonKey.session.sCode})
    @RequestMapping("test/test4")
    @ResponseBody
    public CaseResBST test4(@RequestBody(required = false) Object paramReq,
                            @RequestParam(required = false) String phone, Model model) {
        System.out.println("Test......5464645..............");
        String t = null;
        t.toString();
        SmsSend smsSend = new SmsSendImpl();
        smsSend.testSend();

        Map<String, String> temp = new HashMap<>();
        List list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            temp.put("id", String.valueOf(i));
            temp.put("name", "name" + i);
            list.add(temp);
        }

        return CaseResBST.getInstance().setPageInfo(new PageInfo(list));
    }

    /**
     * 测试返回JSON数据
     */
    @Api(value = "测试方法5", params = {"value", "phone", CommonKey.session.sCode})
    @RequestMapping("test/test5")
    @ResponseBody
    public CaseRes test5() {
        System.out.println("Test......5464645..............");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                while (true) {
//                    System.out.println(sc.next());
                }

            }
        });

        thread.setName("kzt");
        thread.start();
        return CaseRes.getInstance();
    }


    /**
     * 取用户BST
     */
    @Api(value = "取用户BST")
    @RequestMapping("test/getUserBST")
    @ResponseBody
    public CaseResBST getUserBST(@RequestBody(required = false) Object paramReq) {

        List list = TextData.getInstance().getList();

        return CaseResBST.getInstance().setPageInfo(new PageInfo(list));
    }


    /**
     * 编辑用户-BST
     */
    @Api(value = "编辑用户", params = {"id", "field", "newValue"})
    @RequestMapping("test/editUser")
    @ResponseBody
    public CaseResBST editUser(@RequestBody(required = false) Map<String, String> paramReq) {

        Assert.hasText(paramReq.getOrDefault("id", ""));

        int id = Integer.parseInt(paramReq.getOrDefault("id", ""));
        String field = paramReq.getOrDefault("field", "");
        String newVaule = paramReq.getOrDefault("newValue", "");

        List list = TextData.getInstance().editAjax(id, field, newVaule);

        return CaseResBST.getInstance().setPageInfo(new PageInfo(list));
    }

    /**
     * 删除用户
     */
    @Api(value = "删除用户", params = {"id"})
    @RequestMapping("test/delectUser")
    @ResponseBody
    public CaseResBST delectUser(@RequestBody(required = false) Map<String, String> paramReq) {

        Assert.hasText(paramReq.getOrDefault("id", ""));

        int id = Integer.parseInt(paramReq.getOrDefault("id", ""));

        List list = TextData.getInstance().delectById(id);

        if (list == null) {
            return CaseResBST.getInstance();
        }
        return CaseResBST.getInstance().setPageInfo(new PageInfo(list));
    }

    /**
     * 添加编辑用户
     */
    @Api(value = "添加编辑用户", params = {"id", "username", "phone", "nickname", "sex", "userType", "email", "realname"})
    @RequestMapping("test/addOrEditUser")
    @ResponseBody
    public CaseResBST addOrEditUser(@RequestBody(required = false) Map<String, String> paramReq) {

        List list = TextData.getInstance().getList();

        if (!StringUtil.isEmpty(paramReq.getOrDefault("id", ""))) {
            //编辑用户
            int id = Integer.parseInt(paramReq.getOrDefault("id", ""));
            Map<String, String> map = TextData.getInstance().selectById(id);
            map.put("username",paramReq.getOrDefault("username", ""));
            map.put("phone",paramReq.getOrDefault("phone", ""));
            map.put("nickname",paramReq.getOrDefault("nickname", ""));
            map.put("sex",paramReq.getOrDefault("sex", ""));
            map.put("userType",paramReq.getOrDefault("userType", ""));
            map.put("email",paramReq.getOrDefault("email", ""));
            map.put("realname",paramReq.getOrDefault("realname", ""));
//
        } else {
            //添加用户
            list = TextData.getInstance().addList(paramReq);
        }

        return CaseResBST.getInstance().setPageInfo(new PageInfo(list));
    }


    @Autowired
    CaptchaService captchaService;

    @Api(value = "图片验证码:获取")
    @RequestMapping("kaptcha/getKaptchaImage")
    public ModelAndView getKaptchaImage() {
        HttpSession session = request.getSession();
        String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
        System.out.println("******************验证码是: " + code + "******************");

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        try {
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(captchaService.getICodeImg()));
            ServletOutputStream out = null;
            out = response.getOutputStream();
            ImageIO.write(bi, "png", out);
            try {
                out.flush();
            } finally {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Api(value = "图片验证码:取B64")
    @RequestMapping("kaptcha/getKaptchaImageB64")
    @ResponseBody
    public CaseRes getKaptchaImageB64() {
        String result = captchaService.getICode();
        Map<String, String> map = new HashMap<>();
        map.put("head", "data:image/png;base64");
        map.put("body", result);
        return CaseRes.getInstance().setData(map);
    }

    @Api(value = "图片验证码:验证", params = {"code"})
    @RequestMapping("kaptcha/kaptchaVerify")
    @ResponseBody
    public CaseRes kaptchaVerify() {

        String code = getStringParam("code");

        if (captchaService.isVerify(code)) {
            return CaseRes.getInstance();
        }
        return CaseRes.getInstance().setMessage("1_验证码错误！");
    }


    @Autowired
    EmailService mailSenderService;

    @Api(value = "邮箱:获取验证码", params = {"con/"})
    @RequestMapping("email/getCode")
    @ResponseBody
    public CaseRes sendEmail(@RequestBody(required = false) String con) {
//
//        if (ObjectUtils.isEmpty(paramReq.mark)) {
//            return new CaseRes("mark不能为空！");
//        } else {
//            String getSmsToken = String.valueOf(request.getSession().getAttribute(Constant.Session.key_mark));
//            if (!paramReq.mark.equals(getSmsToken)) {
//                return new CaseRes("mark以过期！");
//            } else {
//                request.getSession().setAttribute(Constant.Session.key_mark, null);
//            }
//        }
//        if (ObjectUtils.isEmpty(paramReq.email)) {
//            return new CaseRes("email不能为空！");
//        }
//
//        if (ObjectUtils.isEmpty(paramReq.type)) {
//            return new CaseRes("类型不能为空！");
//        }
//
//        String code = getNumberCode();
//
//        String con = MessageFormat.format(Constant.Tmpl.register, code);
//

//        request.getSession().setAttribute(Constant.Session.key_code, code);
//        emailService.emailSendText("邮箱验证码", con, paramReq.email);
//
//        /**
//         * email发送没有开启，直接返回验证码
//         */
//        if (!Constant.Email.isSend) {
//            return CaseRes.getInstance().setData(code);
//        }
        mailSenderService.emailSendText("标题", "内容", "1413221142@qq.com");

        return CaseRes.getInstance();
    }


    @Api(value = "地区:列表", params = {"parentId/上级ID//0"})
    @RequestMapping("region/getRegion")
    @ResponseBody
    public CaseRes getRegion(@RequestBody(required = false) RegionModel model) {
        return CaseRes.getInstance().setData(regionService.getRegion(ObjectUtils.toInt(model.getParentId())));
    }


    @Autowired
    UpFileService upFileService;

    @Api(value = "文件上传:单文件", params = {"file//file", "code/文件对应的标示//time"})
    @RequestMapping("upLoad/file")
    @ResponseBody
    public CaseRes fileUpload(@RequestParam(value = "file", required = false) MultipartFile file, @RequestParam(required = false) String code) {
        return upFileService.insert(file, code);
    }

    @Api(value = "文件上传:多文件", params = {"file//file", "file1//file", "file2//file", "codes/文件对应的标示"})
    @RequestMapping("upLoad/muchFile")
    @ResponseBody
    public CaseRes muchFileUpload(@RequestParam(value = "file", required = false) MultipartFile[] files, @RequestParam(required = false) String code) {

        if (org.springframework.util.ObjectUtils.isEmpty(files)) return CaseRes.getInstance().setMessage(CommonMessage.upFile.isEmpty);

        return upFileService.insert(files, null);
    }

    @Api(value = "富文本:上传")
    @RequestMapping("ueditor/upLoad")
    @ResponseBody
    public void ueditor() {
//        String rootPath = request.getServletContext().getRealPath("/");
        String rootPath = Properties.file.getString(CommonKey.Propertie.file.upload_ads);
        ActionEnter actionEnter = new ActionEnter(request, rootPath);
        try {
            response.getWriter().write(actionEnter.exec());
            response.getWriter().close();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

}


