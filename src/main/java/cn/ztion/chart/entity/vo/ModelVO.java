package cn.ztion.chart.entity.vo;

import cn.ztion.chart.entity.Model;
import com.mybatisflex.annotation.Id;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class ModelVO {

    @Id
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 介绍
     */
    private String detail;
    /**
     * 头像
     */
    private String avatar;

    public ModelVO from(Model m) {
        BeanUtils.copyProperties(m, this);
        return this;
    }

}
