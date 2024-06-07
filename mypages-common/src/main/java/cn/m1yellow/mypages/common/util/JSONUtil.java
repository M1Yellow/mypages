package cn.m1yellow.mypages.common.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * JSON 工具类。<br>
 * 主要用于统一管理使用的 json 类库，方便以后哪个类库漏洞频出，或者顶峰衰落了，随时切换其他类库
 */
@Slf4j
public class JSONUtil {

    private static ObjectMapper om = new ObjectMapper();

    static {
        // 对象的所有字段全部列入，还是其他的选项，可以忽略null等
        om.setSerializationInclusion(Include.ALWAYS);
        // 设置Date类型的序列化及反序列化格式
        om.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略空Bean转json的错误
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知属性，防止json字符串中存在，java对象中不存在对应属性的情况出现错误
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // 注册一个时间序列化及反序列化的处理模块，用于解决 jdk8 中 localDateTime 等的序列化问题
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        om.registerModule(javaTimeModule);
    }


    /**
     * 对象 => json字符串
     *
     * @param obj 源对象
     */
    public static <T> String toJSON(T obj) {
        String json = null;
        if (null != obj) {
            try {
                json = om.writeValueAsString(obj);
            } catch (Exception e) {
                log.error("JsonUtil | method=toJSON() | 对象转为 Json 字符串 Error！" + e.getMessage(), e);
            }
        }
        return json;
    }


    /**
     * Json String TO JSONObject<br>
     * jackson 中 没有 JSONObject 类，可以用 JsonNode、ObjectNode 替代。注意，JsonNode 类是不可变的
     */
    public static ObjectNode toJSONObject(String json) {
        try {
            return om.readValue(json, ObjectNode.class);
        } catch (Exception e) {
            log.error("JsonUtil | method=toJSONObject() | 将 json 格式的数据换为 JSONObject Error！" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * JsonNode to String<br>
     */
    public static String node2Str(JsonNode node) {
        if (null == node) return null;
        //return node.toString(); // 外层多出""包裹，""小明""
        //return node.toPrettyString(); // 外层多出""包裹，""小明""
        return node.asText(); // 正常字符串，，"小明"
    }


    /**
     * Json to Bean by clazz
     */
    public static <T> T toBean(String json, Class<T> clazz) {
        try {
            return parse(json, clazz);
        } catch (Exception e) {
            log.error("JsonUtil | method=toBean() by clazz | Json 转为 Jave Bean Error！" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * Json to Bean by type
     */
    public static <T> T toBean(String json, TypeReference type) {
        try {
            return parse(json, type);
        } catch (Exception e) {
            log.error("JsonUtil | method=toBean() by type | Json 转为 Jave Bean Error！" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * Json 转为 Map
     */
    public static Map<String, Object> toMap(String json) {
        try {
            return parse(json, Map.class);
        } catch (Exception e) {
            log.error("JsonUtil | method=toMap() | Json 转为 Map Error！" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * Json 转 List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            return om.readValue(json, getCollectionType(ArrayList.class, clazz));
        } catch (Exception e) {
            log.error("JsonUtil | method=toList() | Json 转为 List Error！" + e.getMessage(), e);
        }
        return null;
    }


    /**
     * 获取泛型的 Collection Type
     *
     * @param collectionClass 泛型的Collection
     * @param elementClasses  元素类
     * @return JavaType Java类型
     */
    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return om.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }


    /**
     * json字符串 => 对象
     *
     * @param json  源json串
     * @param clazz 对象类
     * @param <T>   泛型
     */
    private static <T> T parse(String json, Class<T> clazz) {
        return parse(json, clazz, null);
    }


    /**
     * json字符串 => 对象
     *
     * @param json 源json串
     * @param type 对象类型
     * @param <T>  泛型
     */
    private static <T> T parse(String json, TypeReference type) {
        return parse(json, null, type);
    }


    /**
     * json => 对象处理方法
     * <br>
     * 参数clazz和type必须一个为null，另一个不为null
     * <br>
     * 此方法不对外暴露，访问权限为private
     *
     * @param json  源json串
     * @param clazz 对象类
     * @param type  对象类型
     * @param <T>   泛型
     */
    private static <T> T parse(String json, Class<T> clazz, TypeReference type) {
        T obj = null;
        if (null != json && !"".equals(json.trim())) {
            try {
                if (null != clazz) {
                    obj = om.readValue(json, clazz);
                } else {
                    obj = (T) om.readValue(json, type);
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        return obj;
    }
}
