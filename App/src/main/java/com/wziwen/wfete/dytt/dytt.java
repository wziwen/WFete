package com.wziwen.wfete.dytt;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.fetcher.FetcherFactory;
import com.wziwen.spider.v1.OrmLiteDataCenter;
import com.wziwen.spider.Task;

/**
 * Created by ziwen.wen on 2017/2/23.
 * xiaopian.com 电影天堂电影
 */
public class dytt {

    public static void main(String[] args) {
        Dispatcher dispatcher = new Dispatcher();

        Task task = new Task();
        task.setName("电影天堂");
        task.setUrl("http://www.xiaopian.com/html/gndy/dyzz/index.html");
        task.setFetcherType(FetcherFactory.FETCHER_JS_WEB);
        task.setParserType(20);

        OrmLiteDataCenter ormLiteDataCenter = new OrmLiteDataCenter();
        ormLiteDataCenter.setDbName("dytt.db");
        dispatcher.setDataCenter(ormLiteDataCenter)
                .addTask(task)
                .addParser(20, new PageParser(20, 21))
                .addParser(21, new MovieParser())
                .setThreadCount(250)
                .start()
                .waitForComplete();

        System.exit(0);
    }
}
