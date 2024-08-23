package cn.ztion.chart.openai.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Choices {
    private Message message;
    @JSONField(name = "finish_reason")
    private String finishReason;

    private int index;

}