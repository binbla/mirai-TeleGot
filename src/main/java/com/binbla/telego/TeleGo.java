package com.binbla.telego;

import com.binbla.telego.utils.SocketServer;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;
import net.mamoe.mirai.event.events.BotOnlineEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public final class TeleGo extends JavaPlugin {
    public static final TeleGo INSTANCE = new TeleGo();

    private TeleGo() {
        super(new JvmPluginDescriptionBuilder("com.binbla.telego.TeleGo", "1.0")
                .name("TeleGo")
                .author("bla")
                .build());
    }

    @Override
    public void onEnable() {
        reloadPluginConfig(Config.INSTANCE);
        reloadPluginData(AppData.INSTANCE);
        sync_file();
        SocketServer.INSTANCE.start();
        getLogger().info("TeleGo 插件已加载");
        GlobalEventChannel.INSTANCE.subscribeOnce(BotOnlineEvent.class, e -> {
            timeSchedule();
        });
    }

    //读取未完成列表，没有加文件锁，所以插件启动之前，telethon最好先别运行
    public void sync_file() {
        try {
            File file = new File(Config.INSTANCE.getFilePath());
            if (file.exists()) {
                FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);
                String readLine = bufferedReader.readLine();
                String[] message;
                while (readLine != null) {
                    getLogger().info("Read From Data:" + readLine);
                    message = readLine.split("\t");
                    AppData.INSTANCE.getMsg_queue().add(Arrays.asList(message));
                    readLine = bufferedReader.readLine();
                }
                bufferedReader.close();
                fileReader.close();
                //清空文件
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timeSchedule() {
        int timeSplit = Config.INSTANCE.getTimeSplit();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (!AppData.INSTANCE.getMsg_queue().isEmpty()) {
                    List<String> msg = AppData.INSTANCE.getMsg_queue().get(0);
                    AppData.INSTANCE.getMsg_queue().remove(0);
                    getLogger().info("取出了一份转发信息");
                    if(msg.size()<3){
                        getLogger().info("这是个异常数据！");
                    }
                    long myBotID = Config.INSTANCE.getBotID();
                    new SendThread(msg, Bot.getInstance(myBotID));
                }
            }
        }, 1000, timeSplit);
    }
}
