package cn.ztion.chart.config;

import cn.ztion.chart.openai.OpenAI;
import cn.ztion.chart.openai.config.ChatV1Url;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "openai")
@Configuration
@Data
@Slf4j
public class OpenAIConfig {
    private String organization;
    private String apiKey;
    private Boolean useProxy;
    private String host;
    private Integer port;

    @Bean
    public OpenAI createApi() {
        OpenAI openAI = new OpenAI();
        openAI.setApiKey(apiKey);
        openAI.setV1Url(new ChatV1Url());
        openAI.setOrganization(organization);
        //http客户端
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .callTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS);
        if (useProxy) {
            log.info("启用代理，{}:{}", host, port);
            builder.proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port)));
        }
        openAI.setHttpClient(builder.build());

        return openAI;
    }
}
