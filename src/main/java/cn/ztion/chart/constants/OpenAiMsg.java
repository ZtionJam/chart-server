package cn.ztion.chart.constants;

/**
 * @Author: ZtionJam
 * @Date: 2023/10/20 16:45
 * @Description: OpenAiMsg
 * @Version 1.0.0
 */
public class OpenAiMsg {
    public static final String MODEL_NOT_EXISTS = "模型不存在";
    public static final String PLEASE_CHOOSE_MODEL = "请选择模型";
    public static final String LEVEL_LOW = "用户等级不足以使用此模型";
    public static final String CHAT_NOT_EXISTS = "会话不存在，请切换模型重试";
    public static final String NO_PER = "无权操作";
    public static final String CONTEXT_TO_LONG = "<font color='red'> 上下文超长啦，请点击左下角清理按钮重新开始对话 </font>吧";
    public static final String CHAT_ERR = "<font color='red'> 请求失败啦，请尝试重试哦~ </font>";
    public static final String DAY_LIMIT_ERR = "<font color='red'> 今天的聊天次数达到上线了啦，不要沉迷哦，出去看看外面的世界吧 </font>";
}
