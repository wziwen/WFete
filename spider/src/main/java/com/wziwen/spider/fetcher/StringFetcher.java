package com.wziwen.spider.fetcher;

import com.wziwen.spider.IFetcher;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by wen on 2017/2/10.
 */
public class StringFetcher implements IFetcher<Task, String>{


    @Override
    public String fetch(Task task) throws TaskException {
        try {
            Document document = Jsoup.connect(task.getUrl()).get();
            return document.html();
        } catch (IOException e) {
            throw new TaskException("String fetcher error", e);
        }
    }
}
