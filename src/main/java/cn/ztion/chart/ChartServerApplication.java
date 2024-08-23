package cn.ztion.chart;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("cn.ztion.chart.mapper")
@EnableScheduling
public class ChartServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChartServerApplication.class, args);
    }

}
