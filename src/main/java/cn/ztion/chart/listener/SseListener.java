package cn.ztion.chart.listener;

import cn.hutool.core.date.DateUtil;
import cn.ztion.chart.constants.CommonStr;
import cn.ztion.chart.constants.OpenAiMsg;
import cn.ztion.chart.entity.Msg;
import cn.ztion.chart.service.MsgService;
import cn.ztion.chart.util.BeanUtils;
import cn.ztion.chart.util.CacheDao;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RequiredArgsConstructor
@Slf4j
public class SseListener extends EventSourceListener {

    private Long msgId;
    private SseEmitter sseEmitter;
    private CacheDao cacheDao = BeanUtils.getBean(CacheDao.class);
    private final MsgService msgService = BeanUtils.getBean(MsgService.class);

    public SseListener(Long msgId, SseEmitter sseEmitter) {
        this.msgId = msgId;
        this.sseEmitter = sseEmitter;
    }

    @Override
    public void onClosed(@NotNull EventSource eventSource) {
        sseEmitter.complete();
        super.onClosed(eventSource);
    }

    @SneakyThrows
    @Override
    public void onEvent(@NotNull EventSource eventSource, @Nullable String id, @Nullable String type, @NotNull String data) {
        //如果完成
        if (CommonStr.SSE_DOWN.equals(data)) {
            sseEmitter.send(SseEmitter.event().data(data + CommonStr.END_DATA));
            return;
        }
        //解析数据
        StringBuilder content = new StringBuilder();
        JSONArray ja = JSON.parseObject(data).getJSONArray("choices");
        for (int i = 0; i < ja.size(); i++) {
            JSONObject jo = ja.getJSONObject(i).getJSONObject("delta");
            String str = jo.getString("content");
            if (str != null) {
                content.append(str);
            }
        }
        //推送和更新消息
        synchronized ((msgId + "send").intern()) {
            sseEmitter.send(SseEmitter.event().data(content + CommonStr.END_DATA, MediaType.TEXT_PLAIN));
            msgService.msgAppend(msgId, content.toString());
        }


        super.onEvent(eventSource, id, type, data);
    }

    @SneakyThrows
    @Override
    public void onFailure(@NotNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
        synchronized ((msgId + "send").intern()) {
            if (response != null && response.body() != null) {
                String string = response.body().string();
                log.info("请求失败:{}", string);
                JSONObject jo = JSON.parseObject(string);
                String code = jo.getJSONObject("error").getString("code");
                //超长
                if ("context_length_exceeded".equals(code)) {
                    sseEmitter.send(SseEmitter.event().data(OpenAiMsg.CONTEXT_TO_LONG + CommonStr.END_DATA));
                }
            } else {
                sseEmitter.send(SseEmitter.event().data(OpenAiMsg.CHAT_ERR + CommonStr.END_DATA));
                log.error("请求失败", t);
            }
            sseEmitter.send(SseEmitter.event().data(CommonStr.SSE_DOWN + CommonStr.END_DATA));
            sseEmitter.complete();
            msgService.del(msgId);
        }
        super.onFailure(eventSource, t, response);
    }

    @Override
    public void onOpen(@NotNull EventSource eventSource, @NotNull Response response) {
        Msg msg = msgService.findById(msgId);
        String key = CommonStr.LIMIT_COUNT + msg.getUserId() + ":" + DateUtil.today();
        int value = 0;
        String valueStr = cacheDao.get(key);
        if (valueStr != null) {
            value = Integer.parseInt(valueStr);
        }
        cacheDao.set(key, String.valueOf(value + 1), 24 * 60 * 60);
        super.onOpen(eventSource, response);
    }
}
