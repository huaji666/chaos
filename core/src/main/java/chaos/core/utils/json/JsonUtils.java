package chaos.core.utils.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * © shop
 * qq:1413221142
 * 作者：王健(aupp)
 * 时间：2016-03-11
 */
public class JsonUtils {
    //    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static void setMapperConfig() {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //单引号问题
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
//        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.configure(SerializationFeature.WRITE_NULL_MAP_VALUES,false);
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//        mapper.configure(SerializationFeature.FAIL_ON_SELF_REFERENCES,false);
//        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
//        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT,false);
    }


    /**
     * @param obj
     * @return
     */
    public static String tojSON(Object obj) {
        setMapperConfig();

        if (ObjectUtils.isEmpty(obj)) {
            return "";
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * @param jsonStr
     * @param parametrized
     * @param <T>
     * @return
     */
    public static <T> T toObj(String jsonStr, Class<T> parametrized) {
        setMapperConfig();
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        JavaType stringType = mapper.constructType(parametrized);
        try {
            return mapper.readValue(jsonStr, stringType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param map
     * @param parametrized
     * @param <T>
     * @return
     */
    public static <T> T toObj(Object map, Class<T> parametrized) {
        setMapperConfig();
        String jsonStr = tojSON(map);
        if (StringUtils.isEmpty(jsonStr)) {
            return null;
        }
        JavaType stringType = mapper.constructType(parametrized);
        try {
            return mapper.readValue(jsonStr, stringType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param <T>
     * @param jsonStr
     * @param elementType
     * @return
     */
    public static <T> ArrayList<T> toList(String jsonStr, Class<?> elementType) {
        setMapperConfig();
        if (StringUtils.isEmpty(jsonStr)) {
            return new ArrayList<>();
        }
//        JavaType listType = mapper.getTypeFactory().c(elementType);
        JavaType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementType);
        try {
            return mapper.readValue(jsonStr, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * @param <T>
     * @param map
     * @param elementType
     * @return
     */
    public static <T> ArrayList<T> toList(Object map, Class<?> elementType) {
        setMapperConfig();
        String jsonStr = tojSON(map);
        if (StringUtils.isEmpty(jsonStr)) {
            return new ArrayList<>();
        }
//        JavaType listType = mapper.getTypeFactory().c(elementType);
        JavaType listType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, elementType);
        try {
            return mapper.readValue(jsonStr, listType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * @param jsonStr
     * @param key
     * @param value
     * @param <T>
     * @return
     */
    public static <T> T toMap(String jsonStr, Class<?> key, Class<?> value) {
        if (StringUtils.isEmpty(jsonStr)) {
            return (T) new HashMap<>();
        }
        JavaType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, key, value);

        try {
            return mapper.readValue(jsonStr, mapType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (T) new HashMap<>();
    }


//    /**
//     * 获取泛型的Collection Type
//     *
//     * @param elementClasses 元素类
//     * @return JavaType Java类型
//     * @since 1.0
//     */
//    private static JavaType getCollectionType(Class<?> parametrized, Class<?>... elementClasses) {
//        return mapper.getTypeFactory().constructParametricType(parametrized, elementClasses);
//    }

//    /**
//     * 获取泛型的Collection Type
//     *
//     * @param elementClasses 元素类
//     * @return JavaType Java类型
//     * @since 1.0
//     */
//    private static JavaType getCollectionType(Class<?> parametrized, Class<?> parametersFor, Class<?>... elementClasses) {
//        return mapper.getTypeFactory().constructParametrizedType(parametrized, parametersFor, elementClasses);
//    }


//    public static void main(String[] args) {
//
//
//
//        Map<String, Object> map = new HashMap<>();
//        map.put("mapkey", "hahaha");
//        map.put("mapkey2", "hah按时开放了");
//        map.put("mapkey3", "h圣诞快乐就流口水的积分离开时");
//
//
//        List<String> temp = new ArrayList<>();
//        temp.add("adfasf");
//        temp.add("您好啊！");
//        String v = tojSON(temp);
//        String vmap = tojSON(map);
//
//
//        System.out.println("*-*-*-*-*-" + v);
//        System.out.println("*-*-*-*-*-" + vmap);
//
//        List<String> vList = toList(v, String.class);
//
//        Map<String, Object> map1 = toMap(vmap, String.class, Object.class);
//
//        for (Map.Entry<String, Object> entry : map1.entrySet()) {
//            System.out.println(entry.getValue() + "..............val");
//        }
//
//        for (String s : vList) {
//            System.out.println("------" + s);
//        }
//
////        test(new UpFileSimpleModel(), "bbbb", "cccc");
//
//    }


    public static void test(Object... aaa) {

    }

    public static void main(String[] args) {
        int i = 0, j = 5;
        tp:
        for (; ; ) {
            i++;
            for (; ; ) {
                if (i > --j) break tp;
            }
//            System.out.println("i=" + i + ",j=" + j);
//            System.out.println("ss");
        }
    }
}
