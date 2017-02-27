package com.wziwen.spider;

import java.io.IOException;

/**
 * Created by wen on 2017/2/10.
 */
public class Spider {

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();
        Task task = new Task();
        task.setName("苗疆蛊事");
        task.setUrl("http://gushi.luxuqing.com/");
        dispatcher.addTask(task);
        dispatcher.start();

        try {
            int inputStream = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
