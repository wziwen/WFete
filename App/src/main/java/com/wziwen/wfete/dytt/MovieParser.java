package com.wziwen.wfete.dytt;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.IParser;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Created by wen on 2017/2/15.
 * 电影信息获取
 */
public class MovieParser implements IParser<String> {

    private String outputDir;

    public MovieParser() {
    }

    public MovieParser(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void parse(Dispatcher dispatcher, Task task, String html) throws TaskException {
        Document document = Jsoup.parse(html);

        String url = document.getElementById("Zoom").getElementsByTag("a").get(0).attr("href");

        // 生成小米路由器下载链接
        String downloadUrl = "http://d.miwifi.com/d2r/?url=" +
                new String(Base64.getEncoder().encode(url.getBytes())) + "&src=demo";

        writeFile(task.getExtra() + "\r\n         "  + downloadUrl + "\r\n                  " + task.getUrl() + "\r\n\r\n");
    }

    private synchronized void writeFile(String str) {
        File file = new File("Dytt.txt");
        try {
            FileOutputStream outputStream = new FileOutputStream(file, true);
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
