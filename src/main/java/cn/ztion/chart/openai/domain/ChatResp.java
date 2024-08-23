package cn.ztion.chart.openai.domain;

import lombok.Data;

import java.util.List;

@Data
public class ChatResp {
    private String id;

    private String object;

    private int created;

    private String model;

    private Usage usage;

    private List<Choices> choices;


}