package cn.ztion.chart.openai.config;

import lombok.Data;

@Data
public class ChatV1Url {
    /**
     * 模型列表
     */
    private String modelsUrl = "https://api.openai.com/v1/models";
    /**
     * 绘画
     */
    private String chatCompletionsUrl = "https://api.openai.com/v1/chat/completions";

}
