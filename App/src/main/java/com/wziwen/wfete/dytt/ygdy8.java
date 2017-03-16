package com.wziwen.wfete.dytt;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.Task;
import com.wziwen.spider.fetcher.FetcherFactory;
import com.wziwen.spider.v1.OrmLiteDataCenter;

/**
 * Created by ziwen.wen on 2017/2/23.
 * http://www.ygdy8.net 电影天堂电影
 */
public class ygdy8 {

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        Task task = new Task();
        task.setName("阳光电影");
        task.setUrl("http://www.ygdy8.net/html/gndy/dyzz/index.html");
        task.setFetcherType(FetcherFactory.FETCHER_JS_WEB);
        task.setParserType(20);

        PageParser pageParser = new PageParser(20, 21);
        pageParser.setMaxPage(30);
        pageParser.setHostUrl("http://www.ygdy8.net");
        pageParser.setPageBaseUrl("http://www.ygdy8.net/html/gndy/dyzz/");

        MovieParser movieParser = new MovieParser();
        movieParser.setFileName("ygdy8.txt");

        OrmLiteDataCenter ormLiteDataCenter = new OrmLiteDataCenter();
        ormLiteDataCenter.setDbName("ygdy8.db");
        dispatcher.setDataCenter(ormLiteDataCenter)
                .addTask(task)
                .addParser(20, pageParser)
                .addParser(21, new MovieParser())
                .setThreadCount(250)
                .start()
                .waitForComplete();

        System.exit(0);
    }
}
