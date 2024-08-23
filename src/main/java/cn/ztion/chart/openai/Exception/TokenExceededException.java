package cn.ztion.chart.openai.Exception;

/**
 * @author ztion
 * @version 1.0
 * @description: token过长
 * @date 2023/4/23 21:55
 */
public class TokenExceededException extends Exception {
    public TokenExceededException(String message) {
        super(message);
    }

    public TokenExceededException() {
    }
}
