package chaos.core.utils;

import chaos.core.utils.object.ObjectUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * ©app
 * qq:1413221142
 * 作者：王健(wangjian)
 * 时间：2016-05-17
 */
public class ConvertUtil {
    /**
     * 需要先转成tree，才能使用这个方法
     * 把tree转列表
     *
     * @param models
     * @param nameF
     * @param childF
     * @return
     */
    public static List toOption(List models, String levelFormatStr, String nameF, String childF) {
        List temp = new ArrayList<>();
        _toOption(models, 0, nameF, childF, temp, levelFormatStr);
        models.clear();
        models.addAll(temp);
        return temp;
    }


//    public static List toOption(List models, String levelFormatStr) {
//        List temp = new ArrayList<>();
//        _toOption(models, 0, temp, levelFormatStr);
//        models.clear();
//        models.addAll(temp);
//        return temp;
//    }
//    public static List toOption(List<? extends Child> models, String levelFormatStr) {
//        List temp = new ArrayList<>();
//        _toOption(models, 0, temp, levelFormatStr);
//        models.clear();
//        models.addAll(temp);
//        return temp;
//    }

    /**
     * 递归分类
     *
     * @param models
     * @param level
     * @param container
     */
    private static void _toOption(List models, int level, String nameF, String childF, List container, String isLevelFormat) {
        for (Object child : models) {
            String nbsp = "";
            for (int i = 0; i < level; i++) {
                nbsp += isLevelFormat;
            }
            if (!ObjectUtils.isEmpty(isLevelFormat)) {
                try {
                    Object name = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(child, nameF);

//                    String name = ObjectUtils.toString(PropertyUtils.getSimpleProperty(child, nameF));
                    BeanUtils.setProperty(child, nameF, nbsp + name);
//                    child.setOptionName(nbsp + child.getOptionName());
                    container.add(child);

                    List list = (List) BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(child, childF);
//                    List list = (List) PropertyUtils.getSimpleProperty(child, childF);
                    _toOption(list, level + 1, nameF, childF, container, isLevelFormat);
//                    _toOption(child.getChild(), level + 1, container, isLevelFormat);
                    list.clear();
//                    child.getChild().clear();
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    private static void _toOption(List<? extends Child> models, int level, List container, String isLevelFormat) {
//        for (Child child : models) {
//            String nbsp = "";
//            for (int i = 0; i < level; i++) {
//                nbsp += isLevelFormat;
//            }
//            if (!org.springframework.util.StringUtils.isEmpty(isLevelFormat)) {
//                child.setOptionName(nbsp + child.getOptionName());
//            }
//            container.add(child);
//            _toOption(child.getChild(), level + 1, container, isLevelFormat);
//            child.getChild().clear();
//        }
//    }

    /**
     * 集合转成树结构
     *
     * @param models
     */
    public static void toTree(List models) {
        String[] joinFields = analysis(models);
        if (ObjectUtils.isEmpty((Object) joinFields)) return;
        _toTree(models, null, joinFields[0], joinFields[1], joinFields[2]);
    }

    /**
     * 集合转成树结构
     *
     * @param models 數據源
     */
    public static void toTree(List models, String idF, String pIdF, String childF) {
        String[] joinFields = new String[]{idF, pIdF, childF};
        if (ObjectUtils.isEmpty((Object) joinFields)) return;
        _toTree(models, null, joinFields[0], joinFields[1], joinFields[2]);
    }


    /**
     * 解析 树结构
     *
     * @param models 數據源
     * @return 集合
     */
    public static String[] analysis(List<?> models) {

        String[] def = new String[0];
        if (ObjectUtils.isEmpty(models)) return def;
        def = new String[3];

        Map<String, long[]> temp = new HashMap<>();
        for (int j = 0; j < models.size(); j++) {
            //获取所有数字类型字段
            Object model = models.get(j);
            for (int i = 0; i < model.getClass().getDeclaredFields().length; i++) {
                Field field = model.getClass().getDeclaredFields()[i];
                if (Modifier.isStatic(field.getModifiers())) continue;
                if (Modifier.isFinal(field.getModifiers())) continue;

                if (field.getType().isAssignableFrom(List.class)) {
                    field.getGenericType().getTypeName().equals(model.getClass().getName());
                    def[2] = field.getName();
                }

                if (field.getType() != Short.class && field.getType() != short.class && field.getType() != Integer.class && field.getType() != int.class && field.getType() != long.class && field.getType() != Long.class) continue;

                if (!temp.containsKey(field.getName())) temp.put(field.getName(), new long[models.size()]);

                try {
                    Object property = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(model, field.getName());
                    temp.get(field.getName())[j] = ObjectUtils.toInt(property);
//                    temp.get(field.getName())[j] = ObjectUtils.toInt(BeanUtils.getSimpleProperty(model, field.getName()));
//                    System.out.println(BeanUtils.getProperty(model, field.getName()));
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        List<Map> ret = new ArrayList<>();
        for (Map.Entry entry : temp.entrySet()) ret.addAll(findIdName(temp, entry));
        for (Map<String, Object> map : ret) {
            for (Map.Entry entry : map.entrySet()) {
                def[0] = String.valueOf(entry.getKey());
                def[1] = String.valueOf(entry.getValue());
            }
        }
        return def;
    }

    /**
     * 查找树结构id
     *
     * @param map map
     * @param tag 目標
     * @return
     */
    private static List<Map> findIdName(Map<String, long[]> map, Map.Entry tag) {
//        boolean [] temp = new boolean[map.size()];
        List<Map> temp = new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            if (entry.getKey().equals(tag.getKey())) continue;
            long tagSum = 0;
            for (long l : (long[]) tag.getValue()) tagSum += l;
            if (tagSum == 0) continue;
            long entrySum = 0;
            for (long l : (long[]) entry.getValue()) entrySum += l;
            if (entrySum == 0) continue;


            Map map1 = new HashMap<>();
//            System.out.println(tagSum);
            for (long l : (long[]) tag.getValue()) {
                if (l == 0) continue;//过滤0
                //判断当前数组是否包含目标long值
                if (!ArrayUtils.contains((long[]) entry.getValue(), l)) {
                    map1.put(entry.getKey(), false);
                }
            }
            if (!map1.containsKey(entry.getKey())) {
//                r.put(entry.getKey(), true);
//                r.put(tag.getKey(), true);
                map1.put(entry.getKey(), tag.getKey());
                //目标所有long 被包含，已经找到 id 字段名称
                temp.add(map1);
            }
//            return String.valueOf(entry.getKey());
        }
        return temp;


//        return "";
    }


    //    /**
//     * 集合转成树结构
//     *
//     * @param models
//     */
//    public static void toTree(List<? extends Child> models) {
//        _toTree(models, null);
//    }

    /**
     * 转成树结构
     *
     * @param models 数据
     * @param temp 递归变量
     * @param idF id
     * @param parentIdF 上级字段名称
     * @param childF 子类集合字段名称
     */
    private static void _toTree(List<?> models, List temp, String idF, String parentIdF, String childF) {
        if (ObjectUtils.isEmpty(temp)) temp = models;

        for (Object model : models) {
            try {
                Object id = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(model, idF);
//                String id = BeanUtils.getSimpleProperty(model, idF);
//                int id = ObjectUtils.toInt(BeanUtils.getProperty(model, idF));
//                int id = ObjectUtils.toInt(BeanUtils.getProperty(model, idF));
                List child = findChild(temp, id, parentIdF);
                BeanUtils.setProperty(model, childF, child);
                _toTree(child, temp, idF, parentIdF, childF);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        /**
         * 删除重复数据
         */
        for (int i = 0; i < models.size(); i++) {
            if (i == models.size() - 1) continue;
            try {
                Object parentId = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(models.get(i), parentIdF);
//                String parentId = BeanUtils.getSimpleProperty(models.get(i), parentIdF);
                Object tagParentId = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(models.get(i + 1), parentIdF);
//                String tagParentId = BeanUtils.getSimpleProperty(models.get(i + 1), parentIdF);
//                int parentId = ObjectUtils.toInt(BeanUtils.getProperty(models.get(i), parentIdF));
//                int tagParentId = ObjectUtils.toInt(BeanUtils.getProperty(models.get(i + 1), parentIdF));

                if (Objects.equals(parentId, tagParentId)) continue;

                if (ObjectUtils.toInt(parentId) > ObjectUtils.toInt(tagParentId)) {
                    models.remove(i);
                } else {
                    models.remove(i + 1);
                }
                i--;
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 转成树结构
     *
     * @param models
     * @param temp
     */
//    private static void _toTree(List<? extends Child> models, List temp) {
//        if (ObjectUtils.isEmpty(temp)) {
//            temp = models;
//        }
//        for (Child model : models) {
//            List<Child> child = findChild(temp, model.getId(), model.getLevel() + 1);
//            model.setChild(child);
//            _toTree(child, temp);
//        }
//        for (int i = 0; i < models.size(); i++) {
//            if (models.get(i).getLevel() == 0 && models.get(i).getParentId() > 0) {
//                models.remove(i);
//                i--;
//            }
//        }
//    }

    /**
     * 查找子类
     *
     * @param list
     * @param parentId
     * @return
     */
    private static List findChild(List list, Object parentId, String parentIdF) {
        List<Object> temp = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {

            Object getParentId = null;
            try {
                getParentId = BeanUtilsBean.getInstance().getPropertyUtils().getSimpleProperty(list.get(i), parentIdF);
//                getParentId = BeanUtils.getProperty(list.get(i), parentIdF);
//                getParentId = BeanUtils.getProperty(list.get(i), parentIdF);
//                getParentId = ObjectUtils.toInt(BeanUtils.getProperty(list.get(i), parentIdF));
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
            if (getParentId==null) return temp;
            if (getParentId.equals(parentId)) {
                try {
                    Object child = BeanUtils.cloneBean(list.get(i));
//                    child.setLevel(level);
                    temp.add(child);
                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
        return temp;
    }
//    private static List findChild(List<? extends Child> list, int parentId, int level) {
//        List<Child> temp = new ArrayList<>();
//
//        for (int i = 0; i < list.size(); i++) {
//            if (list.get(i).getParentId() == parentId) {
//                try {
//                    Child child = (Child) BeanUtils.cloneBean(list.get(i));
//                    child.setLevel(level);
//                    temp.add(child);
//                } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return temp;
//    }

//    private void addChild(List<ArticleCategoryModel> models, Integer level) {
//        for (ArticleCategoryModel model : models) {
//            List<ArticleCategoryModel> child = categoryModelMapper.selectAllByParentId(model.getId());
//            model.setLevel(level);
//            model.setChild(child);
//            if (!ObjectUtils.isEmpty(child)) {
//                addChild(child, level+1);
//            }
//
//        }
//    }


    /**
     * 格式成表格字段样式
     * <pre>
     *     abcDef = abc_def
     * </pre>
     *
     * @param field
     * @return String
     */
    public static String toTableStyle(String field) {
        if (ObjectUtils.isEmpty(field)) return "";
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < field.length(); i++) {
            if (field.charAt(i) >= 'A' && field.charAt(i) <= 'Z') {
                buffer.append("_");
            }
            buffer.append(field.charAt(i));
        }
        return buffer.toString().toLowerCase();
    }

    public static void main(String[] args) {
//        BigDecimal volumn = new BigDecimal(700);
//        System.out.println(volumn.multiply(new BigDecimal("0.7")));

//        List temp = Rehe
//        analysis(temp);

    }
}
