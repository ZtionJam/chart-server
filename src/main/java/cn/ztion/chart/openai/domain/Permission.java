package cn.ztion.chart.openai.domain;

import com.alibaba.fastjson2.annotation.JSONField;

/**
 * 权限实体
 */
public class Permission {
    private String id;
    private String object;
    private String created;
    @JSONField(name = "allow_create_engine")
    private String allowCreateEngine;
    @JSONField(name = "allow_sampling")
    private String allowSampling;
    @JSONField(name = "allow_logprobs")
    private String allowLogprobs;
    @JSONField(name = "allow_search_indices")
    private String allowSearchIndices;
    @JSONField(name = "allow_view")
    private String allowView;
    @JSONField(name = "allow_fine_tuning")
    private String allowFineTuning;
    private String organization;
    private String group;
    @JSONField(name = "is_blocking")
    private String blocking;
}
