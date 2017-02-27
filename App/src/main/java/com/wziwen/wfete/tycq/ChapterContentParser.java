package com.wziwen.wfete.tycq;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.IParser;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by wen on 2017/2/15.
 */
public class ChapterContentParser implements IParser<String> {

    private String outputDir;

    public ChapterContentParser(String outputDir) {
        this.outputDir = outputDir;
    }

    @Override
    public void parse(Dispatcher dispatcher, Task task, String html) throws TaskException {
        Document document = Jsoup.parse(html);

        String content = document.getElementById("nr1").html();

        File file = new File(outputDir, task.getExtra());
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(content.getBytes());
            outputStream.close();
        } catch (IOException e) {
            throw new TaskException("MovieParser", e);
        }
    }
}
