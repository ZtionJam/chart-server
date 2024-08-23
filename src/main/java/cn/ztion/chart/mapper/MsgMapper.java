package cn.ztion.chart.mapper;

import cn.ztion.chart.entity.Msg;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MsgMapper extends BaseMapper<Msg> {

    void msgAppend(@Param("content") String content, @Param("id") Long id);
}
