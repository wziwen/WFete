package com.wziwen.wfete.tycq;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.fetcher.FetcherFactory;
import com.wziwen.spider.Task;

import java.io.File;
import java.io.IOException;

/**
 * Created by wen on 2017/2/15.
 */
public class Test {

    public static void main(String[] args) {
        int CHAPTER_PARSER_TYPE = 10;
        int CHAPTER_CONTENT_PARSER_TYPE = 11;
        Dispatcher dispatcher = new Dispatcher();

        String dir = ".\\TYCQ\\";
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }

        ChapterContentParser chapterContentParser = new ChapterContentParser(dir);
        ChapterParser chapterParser = new ChapterParser(CHAPTER_PARSER_TYPE, CHAPTER_CONTENT_PARSER_TYPE);

        dispatcher.getParseProvider()
                .addParser(CHAPTER_PARSER_TYPE, chapterParser)
                .addParser(CHAPTER_CONTENT_PARSER_TYPE, chapterContentParser);

        Task task = new Task();
        task.setName("天域苍穹");
        task.setUrl("http://m.1xiaoshuo.com/tianyucangqiong/index.html");
        task.setFetcherType(FetcherFactory.FETCHER_HTML);
        task.setParserType(CHAPTER_PARSER_TYPE);
        dispatcher.addTask(task);
        dispatcher.start();

        try {
            int inputStream = System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
