package me.biezhi.wechat.examples;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.MsgType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.api.request.StringRequest;
import io.github.biezhi.wechat.api.response.ApiResponse;

/**
 * @author biezhi
 * @date 2018/1/22
 */
public class MoliBot extends WeChatBot {

    private final String baseUrl;

    public MoliBot(Config config) {
        super(config);
        this.baseUrl = String.format("http://i.itpk.cn/api.php?api_key=%s&api_secret=%s",
                config.get("moli.api_key"), config.get("moli.api_secret"));
    }

    @Bind(msgType = MsgType.TEXT)
    public void handleText(WeChatMessage message) {
        String      url      = String.format("%s&question=%s", baseUrl, message.getText());
        ApiResponse response = this.client().send(new StringRequest(url).post().jsonBody());
        this.sendMsg(message.getFromUserName(), response.getRawBody());
    }

    public static void main(String[] args) {
        new MoliBot(
                Config.load("/wechat.properties").showTerminal(true).autoLogin(true)
        ).start();
    }

}
