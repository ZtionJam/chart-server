package cn.ztion.chart.openai.domain;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * 模型实体
 */
@Data
public class Model {
    private String id;
    private String object;
    @JSONField(name = "owned_by")
    private String ownedBy;
    private List<Permission> permission;
    private String root;
    private String parent;


}
