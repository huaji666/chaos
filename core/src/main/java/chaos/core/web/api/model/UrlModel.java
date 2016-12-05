package chaos.core.web.api.model;

import chaos.core.web.api.ApiHelper;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * © app
 * qq:1413221142
 * 作者：王健
 * 时间：2016-02-03
 */
public class UrlModel {

    public String className;
    public String url;
    public String methodName;
    public Method method;
    public Class[] beans = {};
    public String[] exclude = {};
    public String[] params = {};
    private List<ParamModel> paramModels = new ArrayList<>();


    public String getClassName() {
        return className;
    }

    public String getUrl() {
        return url;
    }

    public String getMethodName() {
        return methodName;
    }


    public Method getMethod() {
        return method;
    }

    public String[] getParams() {
        return params;
    }


    public List<ParamModel> getParamModels() {
        return toParamModel(beans, params, exclude, paramModels, method);
    }

    private List<ParamModel> toParamModel(Class[] beans, String[] params, String[] excludeNames, List<ParamModel> paramModels, Method method) {

        Map<String, ParamModel> temp = new HashMap<>();
        //添加bean中的字段
        for (Class bean : beans) {
            for (Field field : bean.getDeclaredFields()) {
                ParamModel paramModel = new ParamModel(field);
                temp.put(field.getName(), paramModel);
            }
        }
        // TODO 根据参数添加需要的字段
//        for (Parameter parameter : method.getParameters()) {
//            Class cla = parameter.getType();
//            if (cla.getTypeName().contains("java")) continue;
//            if (cla.isPrimitive()) continue;
//
//            for (Field field : cla.getDeclaredFields()) {
//                ParamModel paramModel = new ParamModel(field);
//                temp.put(field.getName(), paramModel);
//            }
//        }

        //添加自定义字段
        for (String s : params) {
            ParamModel paramModel = new ParamModel(s);

            if (temp.containsKey(paramModel.getName())) {
                ParamModel p = temp.get(paramModel.getName());
                if (!ObjectUtils.isEmpty(paramModel.getDef())) p.setDef(paramModel.getDef());
                if (!ObjectUtils.isEmpty(paramModel.getDesc())) p.setDesc(paramModel.getDesc());
                if (!ObjectUtils.isEmpty(paramModel.getType())) p.setType(paramModel.getType());
            } else {
                temp.put(paramModel.getName(), paramModel);
            }
        }



        //排除不需要的字段
        for (String s : excludeNames) temp.remove(s);

        for (String s : ApiHelper.getInstance().getConfig().getExcludeParams()) temp.remove(s);

        paramModels.addAll(temp.values().stream().collect(Collectors.toList()));

//        ComparatorUtils.sort(paramModels, "name", true);
        paramModels.sort((o1, o2) -> {
            if (o1.getName().length() > o2.getName().length()) return 1;
            if (o1.getName().length() == o2.getName().length()) return 0;
            if (o1.getName().length() < o2.getName().length()) return -1;

            return 0;
        });

        return paramModels;
    }
}
