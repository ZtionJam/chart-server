package cn.ztion.chart.openai.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

@Data
public class Usage {
    @JSONField(name = "prompt_tokens")
    private int promptTokens;
    @JSONField(name = "completion_tokens")
    private int completionTokens;
    @JSONField(name = "total_tokens")
    private int totalTokens;


}