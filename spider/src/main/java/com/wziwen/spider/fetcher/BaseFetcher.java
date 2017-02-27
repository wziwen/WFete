package com.wziwen.spider.fetcher;

import com.wziwen.spider.IFetcher;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by ziwen.wen on 2017/2/10.
 * Fetcher 基类
 */
public class BaseFetcher implements IFetcher<Task, InputStream> {

    public InputStream fetch(Task task) throws TaskException {
        try {
            Connection.Response response = Jsoup.connect(task.getUrl()).execute();
            ByteArrayInputStream bufferedInputStream = new ByteArrayInputStream(response.bodyAsBytes());
            System.out.println("BaseFetcher: fetch finished");
            return bufferedInputStream;
        } catch (IOException e) {
            e.printStackTrace();
            throw new TaskException("fetch error", e);
        }
    }
}
