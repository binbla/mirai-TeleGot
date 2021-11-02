package com.binbla.telego;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.io.File;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

/**
 * >ClassName SendThread.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-06-13  13:08
 */
public class SendThread extends Thread {
    String from_id, from_channel="", text="", imageFile;
    Bot bot;

    SendThread(List<String> msg, Bot bot) {
        this.from_id = msg.get(0);
        this.imageFile = msg.get(1);
        this.from_channel = msg.get(2);
        if(msg.size()==4){
            this.text = msg.get(3);
        }
        this.bot = bot;
        this.start();
    }

    @Override
    public void run() {
        File file = new File(imageFile);
        for (long x : Config.INSTANCE.getGroupList().keySet()) {
            if (Config.INSTANCE.getGroupList().get(x).contains(from_id)) {
                TeleGo.INSTANCE.getLogger().info("检查群:" + x);
                try {
                    Image image = net.mamoe.mirai.contact.Contact.uploadImage(Objects.requireNonNull(bot.getGroup(x)), file);
                    Objects.requireNonNull(bot.getGroup(x)).sendMessage(buildMessage(image));
                } catch (Exception e) {
                    TeleGo.INSTANCE.getLogger().info("发送失败");
                }
            }
        }
    }

    MessageChain buildMessage(Image image) {
        /*
        MessageChainBuilder chain = new MessageChainBuilder()
                .append("各位")
                .append(Config.INSTANCE.getChannel().get(from_id).get(1))
                .append("，")
                .append(getTime())
                .append("，")
                .append("这是现在提供的")
                .append(Config.INSTANCE.getChannel().get(from_id).get(2))
                .append("，")
                .append("请享用～")
                .append(image)
                .append("来自频道:")
                .append(from_channel);
         */
        MessageChainBuilder chain = new MessageChainBuilder();
        chain.append(image);
        if(!text.equals("")){
            //chain.append("\n").append(text);
            chain.append(text);
        }
        return chain.build();
    }

    String getTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour >= 3 && hour < 6) return "凌晨好";
        if (hour >= 6 && hour < 8) return "早上好";
        if (hour >= 8 && hour < 11) return "上午好";
        if (hour >= 11 && hour < 13) return "中午好";
        if (hour >= 13 && hour < 17) return "下午好";
        if (hour >= 17 && hour < 19) return "傍晚好";
        if (hour >= 19 && hour < 23) return "晚上好";
        return "深夜好";
    }
}
