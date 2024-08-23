package cn.ztion.chart.openai.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ChatParam {
    /**
     * 模型
     */
    @JSONField(name = "model")
    private String model;
    /**
     * 消息体
     */
    @JSONField(name = "messages")
    private List<Message> messages;
    /**
     * 采样方式
     */
    @JSONField(name = "temperature")
    private Double temperature;
    /**
     * 采样方式
     */
    @JSONField(name = "top_p")
    private Double topP;
    /**
     * 选择
     */
    @JSONField(name = "n")
    private Integer n;
    /**
     * 是否使用流
     */
    @JSONField(name = "stream")
    private Boolean stream;
    /**
     * 停止token量
     */
    @JSONField(name = "stop")
    private String stop;
    /**
     * 最大使用token
     */
    @JSONField(name = "max_tokens")
    private Integer maxTokens;
    @JSONField(name = "presence_penalty")
    private Double presencePenalty;
    @JSONField(name = "frequency_penalty")
    private Double frequencyPenalty;
    @JSONField(name = "logit_bias")
    private Map<Object, Object> logitBias;
    /**
     * 用户标识
     */
    @JSONField(name = "user")
    private String user;


}
