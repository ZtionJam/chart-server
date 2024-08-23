package cn.ztion.chart.exception;

import cn.dev33.satoken.exception.SaTokenException;
import cn.ztion.chart.util.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 14:23
 * @Description: 全局异常处理器
 * @Version 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public R<String> handException(Exception e) {
        log.error("err:", e);
        return R.fail("错误:" + e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public R<String> handServiceException(ServiceException e) {
        return R.fail(e.getMessage());
    }

    @ExceptionHandler(SaTokenException.class)
    public R<Integer> handlerSaTokenException(SaTokenException e) {
        log.error("err:", e);
        String msg = switch (e.getCode()) {
            case 11001, 11002, 11011 -> "未登录，请先登录";
            case 11012, 11013, 11014, 11015, 11016 -> "登录失效，请重新登录";
            default -> "鉴权失败";
        };
        Integer code = switch (e.getCode()) {
            case 11001, 11002, 11011 -> 403;
            case 11012, 11013, 11014, 11015, 11016 -> 403;
            default -> 500;
        };
        return R.fail(null, code, msg);
    }
}
