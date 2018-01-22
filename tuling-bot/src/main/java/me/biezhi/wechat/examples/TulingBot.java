package me.biezhi.wechat.examples;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.api.request.StringRequest;
import io.github.biezhi.wechat.api.response.ApiResponse;
import io.github.biezhi.wechat.utils.WeChatUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 图灵机器人自动回复
 *
 * @author biezhi
 * @date 2018/1/22
 */
@Slf4j
public class TulingBot extends WeChatBot {

    private final String baseUrl = "http://www.tuling123.com/openapi/api";
    private final String apiKey;
    private final String apiSecret;

    public TulingBot(Config config) {
        super(config);
        this.apiKey = config.get("tuling.api_key");
        this.apiSecret = config.get("tuling.api_secret");
    }

    @Bind(msgType = MsgType.TEXT)
    public void handleText(WeChatMessage message) {
        log.info("收到消息: {}", message.getText());

        Map<String, Object> data = new HashMap<String, Object>(2);
        data.put("key", apiKey);
        data.put("info", message.getText());

        //获取时间戳
        String timestamp = String.valueOf(System.currentTimeMillis());
        //生成密钥
        String keyParam = apiSecret + timestamp + apiKey;
        String key      = Md5.MD5(keyParam);

        //加密
        Aes    mc      = new Aes(key);
        String dataStr = mc.encrypt(WeChatUtils.toJson(data));

        ApiResponse response = this.client().send(new StringRequest(baseUrl).post().jsonBody()
                .add("key", apiKey)
                .add("timestamp", timestamp)
                .add("data", dataStr)
        );
        TulingRet tulingRet = response.parse(TulingRet.class);
        log.info("发送消息: {}", tulingRet.text);

        this.sendMsg(message.getFromUserName(), tulingRet.text);
    }

    @Data
    static class TulingRet {
        private int    code;
        private String text;
    }

    public static void main(String[] args) {
        new TulingBot(
                Config.load("/wechat.properties").showTerminal(true).autoLogin(true)
        ).start();
    }

}
