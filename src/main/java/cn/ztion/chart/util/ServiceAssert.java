package cn.ztion.chart.util;

import cn.ztion.chart.exception.ServiceException;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 14:32
 * @Description: 排除业务异常的断言
 * @Version 1.0.0
 */
public class ServiceAssert {

    public static void notNull(Object o, String msg) {
        if (o == null) {
            throw new ServiceException(msg);
        }
    }

    public static void ok(boolean cond, String msg) {
        if (!cond) {
            throw new ServiceException(msg);
        }
    }
}
