package me.biezhi.wechat.examples;

import io.github.biezhi.wechat.WeChatBot;
import io.github.biezhi.wechat.api.annotation.Bind;
import io.github.biezhi.wechat.api.constant.Config;
import io.github.biezhi.wechat.api.enums.AccountType;
import io.github.biezhi.wechat.api.model.WeChatMessage;
import io.github.biezhi.wechat.utils.DateUtils;
import io.github.biezhi.wechat.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 喝水机器人
 * <p>
 * <p>
 * 人体每天喝水的生理需要量为3000毫升。喝水每次以一百毫升至三百毫升为宜，不能超过1500毫升，否则即过量。
 * <p>
 * 附上科学家推荐的“ 喝水行程表”7:00 早晨起床后，先喝一杯温开水，然后开始穿衣叠被洗漱。
 * 这可让睡了一夜的肠胃很快进入工作状态，有利于排出体内污物。
 * <p>
 * 8:00  早餐除了主食外，必需有液态食物，不论是牛奶、豆浆、果汁或稀饭，都能令肌体充分地吸收水分和营养。
 * 10:00  这时候必须补充一杯矿泉水或茶水。如皮肤干涩，可用保湿润肤水轻轻拍打在脸部或直接用面部喷雾来补水
 * 12:00  如果午饭吃了辛辣的食物一定要喝些汤粥来冲淡对胃肠的刺激。餐后多饮水，以避免上火引起皮肤干燥瘙痒。
 * 15:00  下午容易疲劳，吃点水果可以提精神。富含维生素Ｃ的水果既能缓解疲劳，又能补水养颜。
 * 19:00  晚上回家以后的补水是一天中的重要功课，进家后先来一瓶矿泉水。晚餐要有汤有粥，餐后吃些水果，看电视时喝点茶；如有精力可炖些补品汤来喝。
 * 21:00  晚上睡前一小时最好不要喝水。用温水净面，再涂上保湿的营养晚霜，轻轻按摩脸、手足等容易干燥的部位10分钟，使水分和营养渗透到肌肤深层。
 * <p>
 * 专家建议，在正常饮食外多补充1000毫升的水。也就是至少补充3000毫升的水。
 *
 * @author biezhi
 * @date 2018/1/22
 */
@Slf4j
public class DrinkBot extends WeChatBot {

    private Map<String, String> times = new HashMap<>();

    private String tip1 = "现在是北京时间: %s点\n各位大佬请及时饮水, 以免猝死.";
    private String tip2 = "同志们，吃饭要积极，点饭也要准时。";

    private String currentTip = "";

    public DrinkBot(Config config) {
        super(config);
        times.put("10:00", tip1);
        times.put("12:00", tip1);
        times.put("15:00", tip1);
        times.put("19:00", tip1);
        times.put("21:00", tip1);
        times.put("11:45", tip2);
    }

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @Bind(accountType = AccountType.TYPE_FRIEND)
    public void friendMsg(WeChatMessage message) {
        log.info("收到好友 {} 的消息: {}", message.getName(), message.getText());
        this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
    }

    @Bind(accountType = AccountType.TYPE_GROUP)
    public void groupMsg(WeChatMessage message) {
        if (StringUtils.isNotEmpty(message.getName()) && message.getName().equals("蛇皮群聊小分队")) {
            log.info("收到群 {} 的消息: {}", message.getName(), message.getText());
            if (message.isAtMe()) {
                this.sendMsg(message.getFromUserName(), "自动回复: " + message.getText());
            }
        }
    }

    @Override
    protected void other() {
        while (true) {
            String time = LocalDateTime.now().format(dateTimeFormatter);
            // 还没提醒过
            if (times.keySet().contains(time) && !currentTip.equals(time)) {
                currentTip = time;
                log.info("{} 又到了按时提醒的时刻", time);
                String text = String.format(times.get(time), time);
                log.info("向大佬们发送了一条: {}", text);
                this.sendMsgByName("平均月薪17.5", text);
            }
            DateUtils.sleep(100L);
        }
    }

    public static void main(String[] args) {
        new DrinkBot(Config.me().autoLogin(true).showTerminal(true)).start();
    }
}
