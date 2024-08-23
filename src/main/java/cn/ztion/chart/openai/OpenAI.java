package cn.ztion.chart.openai;


import cn.ztion.chart.listener.SseListener;
import cn.ztion.chart.openai.Exception.TokenExceededException;
import cn.ztion.chart.openai.config.ChatV1Url;
import cn.ztion.chart.openai.domain.ChatParam;
import cn.ztion.chart.openai.domain.ChatResp;
import cn.ztion.chart.openai.domain.Model;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import okhttp3.*;
import okhttp3.internal.sse.RealEventSource;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class OpenAI {
    /**
     * 组织
     */
    private String organization;
    public static final Map<Long, SseEmitter> msgClients = new ConcurrentHashMap<>();
    /**
     * 秘钥
     */
    private String apiKey;
    private ChatV1Url v1Url = new ChatV1Url();
    private OkHttpClient httpClient;

    public void chatStream(ChatParam chatParam, Long msgId) throws TokenExceededException {
        String param = JSON.toJSONString(chatParam);
        Request request = new Request.Builder()
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Accept", "text/event-stream")
                .addHeader("Content-Type", "application/json")
                .url(v1Url.getChatCompletionsUrl())
                .method("POST", RequestBody.create(param, MediaType.get("application/json")))
                .build();
        SseEmitter sseEmitter = msgClients.get(msgId);
        RealEventSource eventSource = new RealEventSource(request, new SseListener(msgId, sseEmitter));
        eventSource.connect(httpClient);
    }

}







