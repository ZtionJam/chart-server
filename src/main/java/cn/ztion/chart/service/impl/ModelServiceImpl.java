package cn.ztion.chart.service.impl;

import cn.ztion.chart.entity.Model;
import cn.ztion.chart.entity.User;
import cn.ztion.chart.mapper.ModelMapper;
import cn.ztion.chart.mapper.UserMapper;
import cn.ztion.chart.service.ModelService;
import com.mybatisflex.core.query.QueryWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelMapper modelMapper;
    private final UserMapper userMapper;

    @Override
    public List<Model> getUserCanUseModel(Long userId) {
        User user = userMapper.selectOneById(userId);
        return modelMapper.selectListByQuery(QueryWrapper.create()
                .le(Model::getLevel, user.getLevel()));
    }
}
