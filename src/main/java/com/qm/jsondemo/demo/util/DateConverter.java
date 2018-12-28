package com.qm.jsondemo.demo.util;


import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期转换器
 * 对于日期校验，这里只是简单的做了一下，实际上还有对闰年的校验，每个月份的天数的校验
 * @author: qiumin
 * @create: 2018-12-28 10:43
 **/
public class DateConverter implements Converter{

    /**
     * 校验 yyyy-MM-dd HH:mm:ss
     */
    private static final String REGEX_DATE_TIME = "^\\d{4}([-]\\d{2}){2}[ ]([0-1][0-9]|[2][0-4])(:[0-5][0-9]){2}$";

    /**
     * 校验 yyyy-MM-dd
     */
    private static final String REGEX_DATE = "^\\d{4}([-]\\d{2}){2}$";

    /**
     * 校验HH:mm:ss
     */
    private static final String REGEX_TIME = "^([0-1][0-9]|[2][0-4])(:[0-5][0-9]){2}";

    /**
     * 校验 yyyy-MM-dd HH:mm
     */
    private static final String REGEX_DATE_TIME_NOT_CONTAIN_SECOND = "^\\d{4}([-]\\d{2}){2}[ ]([0-1][0-9]|[2][0-4]):[0-5][0-9]$";

    /**
     * 默认格式
     */
    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";


    /**
     * 存储数据map
     */
    private static final Map<String,String> PATTERN_MAP = new ConcurrentHashMap<>();

    static {
        PATTERN_MAP.put(REGEX_DATE,"yyyy-MM-dd");
        PATTERN_MAP.put(REGEX_DATE_TIME,"yyyy-MM-dd HH:mm:ss");
        PATTERN_MAP.put(REGEX_TIME,"HH:mm:ss");
        PATTERN_MAP.put(REGEX_DATE_TIME_NOT_CONTAIN_SECOND,"yyyy-MM-dd HH:mm");
    }

    @Override
    public Object convert(Type clazz, Object value) {
        if (clazz == null){
            throw new RuntimeException("type must be not null!");
        }
        if (value == null){
            return null;
        }else if ("".equals(String.valueOf(value))){
            return null;
        }
        try {
            return new SimpleDateFormat(getDateStrPattern(String.valueOf(value))).parse(String.valueOf(value));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private String getDateStrPattern(String value){
        for (Map.Entry<String,String> m : PATTERN_MAP.entrySet()){
            if (value.matches(m.getKey())){
                return m.getValue();
            }
        }
        return DEFAULT_PATTERN;
    }
}
