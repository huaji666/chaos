package chaos.core.web.api;

import chaos.core.utils.ComparatorUtils;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.api.model.UrlModel;
import chaos.core.web.api.annoatation.Api;
import chaos.core.web.api.model.ApiConfig;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * © app
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-02-03
 */
public class ApiHelper {

    private static final Logger log = Logger.getLogger(ApiHelper.class);

    private ApiHelper() {
    }

    private static ApiHelper apiHelper = new ApiHelper();

    public static ApiHelper getInstance() {
        return apiHelper;
    }

    public ApiConfig getConfig() {
        return config;
    }

    private ApiConfig config = new ApiConfig();

    public void setConfig(ApiConfig config) {
        this.config = config;
    }


    /**
     * 生成html帮助文档
     *
     * @param applicationContext
     */
    public void init(ApplicationContext applicationContext) {

        setConfig(new ApiConfig().setExcludeParams(new String[]{"serialVersionUID"}));

        if (applicationContext == null) {
            log.error("applicationContext 为空无法初始化Api接口文档！");
            return;
        }


        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝开始生成api页面html＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
        String[] con = applicationContext.getBeanNamesForAnnotation(Api.class);
//        Object[] con = ArrayUtils.addAll(ArrayUtils.addAll(con, con2), con3);
        if (ObjectUtils.isEmpty((Object) con)) return;
        Map<String, List<UrlModel>> listMap = new HashMap<>();

        //遍历所有控制器
        for (Object c : con) {
            String s = String.valueOf(c);
            ArrayList list = new ArrayList();
            Object o = applicationContext.getBean(s);
            Class cla = o.getClass();
            if (AopUtils.isAopProxy(o) || AopUtils.isCglibProxy(o) || AopUtils.isJdkDynamicProxy(o)) {

                if (AopUtils.isJdkDynamicProxy(o)) {
//            TODO isJdkDynamicProxy 代理对象处理 王健
                    log.error("isJdkDynamicProxy 代理未处理");
                } else {
                    o = AopUtils.getTargetClass(o);
                    cla = (Class) o;
                }
            }
            String claString = cla.getName();
            /**
             * 使用aop会导致，多一个父类
             */
            if (!(claString.length() == claString.toUpperCase().indexOf(s.toUpperCase()) + s.length())) {
                cla = cla.getSuperclass();
            }
            UrlModel conUrlModel = new UrlModel();
            String clsMapping = "";
            /**
             *取菜单标题
             */
//            if (cla.isAnnotationPresent(ApiMenu.class)) {
//                ApiMenu apiMenu = (ApiMenu) cla.getAnnotation(ApiMenu.class);
//                conUrlModel.className = apiMenu.value();
//            }

            if (cla.isAnnotationPresent(Api.class)) {
                Api api = (Api) cla.getAnnotation(Api.class);
                if (ObjectUtils.isEmpty(conUrlModel.className)) {
                    conUrlModel.className = api.value();
                }
            }

//            if (cla.isAnnotationPresent(ApiResponse.class)) {
//                ApiResponse apiResponse = (ApiResponse) cla.getAnnotation(ApiResponse.class);
//                if (ObjectUtils.isEmpty(conUrlModel.className)) {
//                    conUrlModel.className = apiResponse.value();
//                }
//            }
            if (ObjectUtils.isEmpty(conUrlModel.className)) {
                conUrlModel.className = s;
            }

            /**
             * 取url
             */
            if (cla.isAnnotationPresent(RequestMapping.class)) {
                RequestMapping requestMapping = (RequestMapping) cla.getAnnotation(RequestMapping.class);
                clsMapping = requestMapping.value()[0];
            }

            /**
             * 遍历方法
             */
            for (Method method : cla.getMethods()) {
                Api api = method.getAnnotation(Api.class);
                if (ObjectUtils.isEmpty(api)) continue;

                UrlModel urlModel = new UrlModel();
                urlModel.className = conUrlModel.className;
                urlModel.methodName = api.value();
                urlModel.params = api.params();
                urlModel.beans = api.beans();
                urlModel.exclude = api.excludeParamss();

//                if (method.isAnnotationPresent(ApiMenu.class)) {
//                    ApiMenu apiMenu = method.getAnnotation(ApiMenu.class);
//                    urlModel.requestDesc = apiMenu.requestDesc();
//                    urlModel.responseDesc = apiMenu.responseDesc();
//                }


                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if (ObjectUtils.isEmpty(requestMapping)) {
                    urlModel.url = (clsMapping + "/" + method.getName());
                } else {
                    urlModel.url = (clsMapping + requestMapping.value()[0]);
                }
                if (ObjectUtils.isEmpty(urlModel.methodName)) {
                    urlModel.methodName = method.getName();
                }
                urlModel.method = method;
//                Parameter[] parameters = method.getParameters();
//                for (Parameter parameter : parameters) {
////                        System.out.println(parameter.getType().getCanonicalName());
////                        System.out.println(parameter.getType());
//                    if (parameter.isNamePresent()) {
//                            System.out.println(parameter.getName());
//                    } else {
//                        System.out.println("无法获取参数名称，请使用JDK8 添加 -parameters 后编译");
//                    }
//                }
                list.add(urlModel);
            }


            ComparatorUtils.sort(list, "methodName", true);
            listMap.put(conUrlModel.className, list);
        }



        /*
        生成html
         */
        String webPath = ((WebApplicationContext) applicationContext).getServletContext().getRealPath("/api/");
        VelocityContext context = new VelocityContext();
        VelocityEngine ve = new VelocityEngine();
        try {
            Properties properties = new Properties();
            properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, webPath);

//            properties.setProperty("eventhandler.referenceinsertion.class", EscapeJavaScriptReference.class.getName());

//            properties.setProperty("eventhandler.escape.javascript.match", "/^(?!\\$\\!.*?Json).*/");
//                properties.setProperty(Velocity.MAX_NUMBER_LOOPS, "5");
            ve.init(properties);
            Template t = ve.getTemplate("api.vm", "utf-8");
            context.put("form", listMap);
            FileOutputStream fos = new FileOutputStream(webPath + "/api.html");
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));//设置写入的文件编码,解决中文问题
            t.merge(context, writer);
            writer.close();
        } catch (Exception e) {
            log.error("加载模板异常！");
            e.printStackTrace();
        }
        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝生成apihtml完成＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝==");
    }

//    @Override
//    public void onApplicationEvent(ContextRefreshedEvent event) {
//
//        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝==生成apihtml＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝");
//        ApplicationContext applicationContext = event.getApplicationContext();
//        String[] con1 = applicationContext.getBeanNamesForAnnotation(Api.class);
//        String[] con2 = applicationContext.getBeanNamesForAnnotation(ApiResponse.class);
//        String[] con3 = applicationContext.getBeanNamesForAnnotation(ApiMenu.class);
//        Object[] con = ArrayUtils.addAll(ArrayUtils.addAll(con1, con2), con3);
////        String[] con = applicationContext.getBeanNamesForAnnotation(Controller.class);
//        if (!ObjectUtils.isEmpty(con)) {
//            Map<String, List<UrlModel>> listMap = new HashMap<>();
//
//            //遍历所有控制器
//            for (Object c : con) {
//                String s = String.valueOf(c);
//                ArrayList list = new ArrayList();
//                Object o = applicationContext.getBean(s);
//                Class cla = o.getClass();
//                if (AopUtils.isAopProxy(o)) {
////                    if (AopUtils.isJdkDynamicProxy(o)) {
////                        o = getJdkDynamicProxyTargetObject(o);
////                    } else { //cglib
////                        o = getCglibProxyTargetObject(o);
//                    o = AopUtils.getTargetClass(o);
//                    cla = (Class) o;
////                    }
//                }
//                String claString = cla.getName();
//                /**
//                 * 使用aop会导致，多一个父类
//                 */
//                if (!(claString.length() == claString.toUpperCase().indexOf(s.toUpperCase()) + s.length())) {
//                    cla = cla.getSuperclass();
//                }
//                UrlModel conUrlModel = new UrlModel();
//                String clsMapping = "";
//                /**
//                 *取菜单标题
//                 */
//                if (cla.isAnnotationPresent(ApiMenu.class)) {
//                    ApiMenu apiMenu = (ApiMenu) cla.getAnnotation(ApiMenu.class);
//                    conUrlModel.className = apiMenu.value();
//                }
//
//                if (cla.isAnnotationPresent(Api.class)) {
//                    Api api = (Api) cla.getAnnotation(Api.class);
//                    if (ObjectUtils.isEmpty(conUrlModel.className)) {
//                        conUrlModel.className = api.value();
//                    }
//                }
//
//                if (cla.isAnnotationPresent(ApiResponse.class)) {
//                    ApiResponse apiResponse = (ApiResponse) cla.getAnnotation(ApiResponse.class);
//                    if (ObjectUtils.isEmpty(conUrlModel.className)) {
//                        conUrlModel.className = apiResponse.value();
//                    }
//                }
//                if (ObjectUtils.isEmpty(conUrlModel.className)) {
//                    conUrlModel.className = s;
//                }
//
//                /**
//                 * 取url
//                 */
//                if (cla.isAnnotationPresent(RequestMapping.class)) {
//                    RequestMapping requestMapping = (RequestMapping) cla.getAnnotation(RequestMapping.class);
//                    clsMapping = requestMapping.value()[0];
//                }
//
//                /**
//                 * 遍历方法
//                 */
//                for (Method method : cla.getMethods()) {
////                    method.getAnnotation(TtwApiRequest.class);
//                    Api ttwApiHtml = method.getAnnotation(Api.class);
//                    if (ObjectUtils.isEmpty(ttwApiHtml)) {
//                        continue;
//                    }
//
////                    if (!method.isAnnotationPresent(TtwApiRequest.class)) {
////                        continue;
////                    }
//                    UrlModel urlModel = new UrlModel();
//                    urlModel.className = conUrlModel.className;
////                    TtwApiRequest ttwApiHtml = method.getAnnotation(TtwApiRequest.class);
//                    urlModel.methodName = ttwApiHtml.value();
//                    urlModel.params = ttwApiHtml.params();
//                    urlModel.beans = ttwApiHtml.beans();
//
//
//                    if (method.isAnnotationPresent(ApiMenu.class)) {
//                        ApiMenu apiMenu = method.getAnnotation(ApiMenu.class);
//                        urlModel.requestDesc = apiMenu.requestDesc();
//                        urlModel.responseDesc = apiMenu.responseDesc();
//                    }
//
//
//                    RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
//                    if (ObjectUtils.isEmpty(requestMapping)) {
//                        urlModel.url = (clsMapping + "/" + method.getName());
//                    } else {
//                        urlModel.url = (clsMapping + requestMapping.value()[0]);
//                    }
//                    if (ObjectUtils.isEmpty(urlModel.methodName)) {
//                        urlModel.methodName = method.getName();
//                    }
//                    urlModel.method = method;
//                    Parameter[] parameters = method.getParameters();
//                    for (Parameter parameter : parameters) {
////                        System.out.println(parameter.getType().getCanonicalName());
////                        System.out.println(parameter.getType());
//                        if (parameter.isNamePresent()) {
////                            System.out.println(parameter.getName());
//                        } else {
//                            System.out.println("无法获取参数名称，请使用JDK8 添加 -parameters 后编译");
//                        }
//                    }
//                    list.add(urlModel);
//                }
//
//
//                ComparatorUtils.sort(list, "methodName", false);
//                listMap.put(conUrlModel.className, list);
//            }
//            String webPath = ((WebApplicationContext) applicationContext).getServletContext().getRealPath("/api/");
//            VelocityContext context = new VelocityContext();
//            VelocityEngine ve = new VelocityEngine();
//            try {
//                Properties properties = new Properties();
//                properties.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, webPath);
////                properties.setProperty(Velocity.MAX_NUMBER_LOOPS, "5");
//                ve.initFielDir(properties);
//                Template t = ve.getTemplate("api.vm", "utf-8");
//                context.put("form", listMap);
//                FileOutputStream fos = new FileOutputStream(webPath + "/api.html");
//                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));//设置写入的文件编码,解决中文问题
//                t.merge(context, writer);
//                writer.close();
//            } catch (Exception e) {
//                log.error("加载模板异常！");
//                e.printStackTrace();
//            }
//
//        }
//        log.info("＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝==生成apihtml完成＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝==");
//    }

    public static void main(String[] args) {
        System.out.println(RandomStringUtils.randomAlphanumeric(10));
        System.out.println(org.apache.commons.lang3.RandomStringUtils.randomNumeric(10));
    }

}
