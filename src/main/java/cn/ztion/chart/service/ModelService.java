package cn.ztion.chart.service;

import cn.ztion.chart.entity.Model;

import java.util.List;

public interface ModelService {
    /**
     * 获取用户可使用的模型
     *
     * @param userId 用户id
     * @return 模型列表
     */
    List<Model> getUserCanUseModel(Long userId);
}
