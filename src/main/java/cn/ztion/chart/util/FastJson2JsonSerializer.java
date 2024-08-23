package cn.ztion.chart.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Redis使用FastJson序列化
 *
 * @author guard
 */
public class FastJson2JsonSerializer<T> {
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;


    private Class<T> clazz;

    public FastJson2JsonSerializer(Class<T> clazz) {
        super();
    }


    public byte[] serialize(T t) throws Exception {
        if (t == null) {
            return new byte[0];
        }
        return JSON.toJSONString(t, JSONWriter.Feature.WriteClassName).getBytes(DEFAULT_CHARSET);
    }

    public T deserialize(byte[] bytes) throws Exception {
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        String str = new String(bytes, DEFAULT_CHARSET);

        return JSON.parseObject(str, clazz, JSONReader.Feature.SupportAutoType);
    }
}
