package cn.ztion.chart.task;

import cn.ztion.chart.entity.Cache;
import cn.ztion.chart.mapper.CacheMapper;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/26 11:07
 * @Description: 缓存定时工具
 * @Version 1.0.0
 */
@Component
@Slf4j
@AllArgsConstructor
public class CacheTask {

    private final CacheMapper cacheMapper;


    @Scheduled(cron = "0 */1 * * * ?")
    public void clearCache() {

        int i = cacheMapper.deleteByQuery(QueryWrapper.create()
                .le(Cache::getExpiry, System.currentTimeMillis() / 1000));

        log.info("清除过期缓存:{}", i);
    }
}
