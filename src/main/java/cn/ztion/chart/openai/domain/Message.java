package cn.ztion.chart.openai.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    /**
     * 角色
     */
    @JSONField(name = "role")
    private String role;
    /**
     * 内容
     */
    @JSONField(name = "content")
    private String content;
}
