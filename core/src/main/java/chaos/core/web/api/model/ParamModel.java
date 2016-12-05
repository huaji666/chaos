package chaos.core.web.api.model;

import chaos.core.utils.json.JsonUtils;
import chaos.core.utils.object.ObjectUtils;
import chaos.core.web.api.annoatation.ApiField;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import com.google.common.io.Files;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

/**
 * ©app
 * qq:1413221142
 * 作者：王健
 * 时间：2016-02-18
 */
public class ParamModel {

    /**
     * 参数名称
     */
    private String name = "";
    /**
     * 参数类型
     */
//    public TtwParamEnum type = TtwParamEnum.STRING;
    private String type = "text";
    /**
     * 参数说明
     */
    private String desc = "";
    /**
     * 默认值
     */
    private String def = "";


    public ParamModel() {
    }

    /**
     * 字符串参数格式化
     *
     * @param params
     */
    public ParamModel(String params) {
        if (!ObjectUtils.isEmpty(params)) {
            String[] temp = params.split("/");
            if (!ObjectUtils.isEmpty((Object) temp)) {
                for (int i = 0; i < temp.length; i++) {
                    if (ObjectUtils.isEmpty(temp[i])) {
                        continue;
                    }
                    switch (i) {
                        case 0:
                            this.name = temp[i];
                            break;
                        case 1:
                            this.desc = temp[i];
                            break;
                        case 2:
                            this.type = temp[i];
                            break;
                        case 3:
                            this.def = temp[i];
//                            //如果默认值等于time 返回时间戳
//                            this.def = (temp[i].equals("time") ? System.currentTimeMillis() + "" : temp[i]);
                            break;
                    }

                }
            }
        }
    }

    /**
     * 字段格式化
     *
     * @param field
     */
    public ParamModel(Field field) {
        setName(field.getName());
        setType(field.getType().getName());

        ApiField apiField = field.getAnnotation(ApiField.class);
        if (apiField == null) return;
        if (ObjectUtils.isEmpty(apiField.value())) return;
        setDesc(new ParamModel(apiField.value()).getDesc());
    }


    public String getName() {
        return name;
    }

    public String getType() {

        if (ObjectUtils.isEmpty(type)) return type;

        if (type.equals(String.class.getName())) {
            type = "text";
        } else if (type.equals(Short.class.getName()) || type.equals(int.class.getName()) || type.equals(Integer.class.getName())) {
            type = "number";
        }
        return type;
    }

    public String getDesc() {
        return desc;
    }

    public String getDef() {
        if (def == null) return def;

        if (def.contains(".class")) {
            def = toJson(def);
        }

        return def;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public static void main(String[] args) {
//        System.out.println(Short.class.getName());
    }


    private String toJson(String calssName) {
        String def = calssName;
        try {
            List<File> fils = (List<File>) FileUtils.listFiles(FileUtils.toFile(ParamModel.class.getResource("/")), FileFilterUtils.nameFileFilter(calssName), DirectoryFileFilter.INSTANCE);
            if (ObjectUtils.isEmpty(fils)) return def;
            File fil = fils.get(0);
            String[] temp = StringUtils.split(findPackageName("", fil.getParentFile()), ".");
            ArrayUtils.reverse(temp);
            String className = StringUtils.join(temp, ".") + "." + Files.getNameWithoutExtension(fil.getName());
            def = JsonUtils.tojSON(Class.forName(className).newInstance());
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return def;
    }

    /**
     * 根据文件 获取包名
     *
     * @param pname
     * @param parent
     * @return
     */
    public static String findPackageName(String pname, File parent) {
        if (!parent.getName().equals("classes")) {
            pname += parent.getName() + ".";
            return findPackageName(pname, parent.getParentFile());
        }
        return pname;
    }

}


