package cn.ztion.chart.util;

import cn.dev33.satoken.dao.SaTokenDao;
import cn.ztion.chart.entity.Cache;
import cn.ztion.chart.mapper.CacheMapper;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 模拟缓存工具
 * 对key加锁锁
 * 缓存持直接存入Sqlite，不引入第三方缓存
 */
@Component
@AllArgsConstructor
@Slf4j
public class CacheDao implements SaTokenDao {

    private final CacheMapper cacheMapper;
    private final FastJson2JsonSerializer<Object> serializer = new FastJson2JsonSerializer<>(Object.class);

    @Override
    public String get(String s) {

        synchronized (s.intern()) {
            Cache cache = cacheMapper.selectOneByQuery(QueryWrapper.create().eq(Cache::getKey, s));
            if (cache != null) {
                return cache.getValue();
            }
            return null;
        }

    }

    @Override
    public void set(String s, String s1, long l) {
        synchronized (s.intern()) {
            this.delete(s);
            Cache cache = new Cache(s, s1, (System.currentTimeMillis() / 1000) + l);
            cacheMapper.insert(cache);
        }

    }

    @Override
    public void update(String s, String s1) {
        synchronized (s.intern()) {
            Cache cache = cacheMapper.selectOneByQuery(QueryWrapper.create().eq(Cache::getKey, s));
            cache.setValue(s1);
            cacheMapper.update(cache);
        }

    }

    @Override
    public void delete(String s) {
        synchronized (s.intern()) {
            cacheMapper.deleteById(s);

        }
    }

    @Override
    public long getTimeout(String s) {
        synchronized (s.intern()) {
            Cache cache = cacheMapper.selectOneByQuery(QueryWrapper.create().eq(Cache::getKey, s));
            if (cache == null) {
                return 0;
            }
            return cache.getExpiry() - System.currentTimeMillis() / 1000;
        }

    }

    @Override
    public void updateTimeout(String s, long l) {
        synchronized (s.intern()) {
            Cache cache = cacheMapper.selectOneByQuery(QueryWrapper.create().eq(Cache::getKey, s));
            cache.setExpiry((System.currentTimeMillis() / 1000) + l);
            cacheMapper.update(cache);
        }

    }

    @SneakyThrows
    @Override
    public Object getObject(String s) {
        synchronized (s.intern()) {
            Cache cache = cacheMapper.selectOneByQuery(QueryWrapper.create().eq(Cache::getKey, s));
            if (cache != null && cache.getValue() != null) {
                return serializer.deserialize(cache.getValue().getBytes(StandardCharsets.UTF_8));
            }
            return null;
        }

    }

    @SneakyThrows
    @Override
    public void setObject(String s, Object o, long l) {
        synchronized (s.intern()) {
            this.deleteObject(s);
            Cache cache = new Cache(s, new String(serializer.serialize(o), StandardCharsets.UTF_8), (System.currentTimeMillis() / 1000) + l);
            cacheMapper.insert(cache);
        }

    }

    @SneakyThrows
    @Override
    public void updateObject(String s, Object o) {
        synchronized (s.intern()) {
            Cache cache = cacheMapper.selectOneByQuery(QueryWrapper.create().eq(Cache::getKey, s));
            cache.setValue(new String(serializer.serialize(o), StandardCharsets.UTF_8));
            cacheMapper.update(cache);
        }

    }

    @Override
    public void deleteObject(String s) {
        cacheMapper.deleteById(s);
    }

    @Override
    public long getObjectTimeout(String s) {
        return this.getTimeout(s);
    }

    @Override
    public void updateObjectTimeout(String s, long l) {
        this.updateTimeout(s, l);
    }

    @Override
    public List<String> searchData(String s, String s1, int i, int i1, boolean b) {
        log.error("缓存不支持搜索");
        return null;
    }
}
