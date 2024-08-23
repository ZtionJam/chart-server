package cn.ztion.chart.enums;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:49
 * @Description: ModelTypeEnum
 * @Version 1.0.0
 */
public enum ModelTypeEnum {

    Default("default"),

    Custom("custom");

    private final String value;

    ModelTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
