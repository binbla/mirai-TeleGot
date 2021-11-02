package com.binbla.telego.utils;

import com.binbla.telego.AppData;
import com.binbla.telego.TeleGo;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * >ClassName SocketServer.java
 * >Description TODO
 * >Author binbla
 * >Version 1.0.0
 * >CreateTime 2021-06-13  08:54
 */
public class SocketServer implements Runnable {
    public static final SocketServer INSTANCE = new SocketServer();
    int port = 13131;
    ServerSocket server;
    Socket socket;
    boolean status = false;
    private Thread t;

    SocketServer() {
        try {
            server = new ServerSocket(port);
            status = true;
        } catch (IOException e) {
            e.printStackTrace();
            TeleGo.INSTANCE.getLogger().info("Socket创建失败，插件将不会工作！");
            TeleGo.INSTANCE.onDisable();
        }
    }

    public void run() {
        while (true) {
            try {
                socket = server.accept();
                InputStream inputStream = socket.getInputStream();
                byte[] bytes = new byte[1024];
                int len;
                StringBuilder message = new StringBuilder();
                while ((len = inputStream.read(bytes)) != -1) {
                    message.append(new String(bytes, 0, len, StandardCharsets.UTF_8));
                }
                getMsg(message + "");
                inputStream.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
                TeleGo.INSTANCE.getLogger().info("socket出现异常");
                // TeleGo.INSTANCE.onDisable();
                // break;
            }
        }
    }

    public void start() {
        if ((t == null && status)) {
            TeleGo.INSTANCE.getLogger().info("Socket Listening on: " + port);
            t = new Thread(this);
            t.start();
        }
    }

    private void getMsg(String message) {
        TeleGo.INSTANCE.getLogger().info("Msg From Telethon: " + message);
        String[] msg = message.split("\t");
        AppData.INSTANCE.getMsg_queue().add(Arrays.asList(msg));
    }
}
