package com.wziwen.wfete.tycq;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.IParser;
import com.wziwen.spider.fetcher.FetcherFactory;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by wen on 2017/2/15.
 * 天域苍穹
 */
public class ChapterParser implements IParser<String>{

    int type = 10;
    int contentParserType = 11;

    public ChapterParser(int type, int contentParserType) {
        this.type = type;
        this.contentParserType = contentParserType;
    }

    @Override
    public void parse(Dispatcher dispatcher, Task task, String html) throws TaskException {
        Document document = Jsoup.parse(html);

        if (task.getUrl().endsWith("index.html")) {
            getAllPage(dispatcher, document);
        }


        getAllChapter(dispatcher, document, task.getUrl());
    }

    private void getAllChapter(Dispatcher dispatcher, Document document, String parentUrl) {
        String index = null;
        try {
            index = parentUrl.substring(parentUrl.indexOf("index")+6, parentUrl.indexOf(".html"));
        } catch (Exception e) {
            System.out.print("error:"+ parentUrl);
            e.printStackTrace();
            return;
        }
        int page;
        try {
            page = Integer.valueOf(index);
        } catch (Exception e) {
            page = 0;
        }

        int count = 20 * page + 1;


        Elements elements = document.getElementsByClass("chapter").select("li");
        for (int i = 1; i < elements.size(); i ++) {
            Element element = elements.get(i);

            Task task = new Task();
            String url = "http://m.1xiaoshuo.com/" + element.getElementsByTag("a").attr("href");
            task.setUrl(url);
            task.setFetcherType(FetcherFactory.FETCHER_HTML);
            task.setParserType(contentParserType);
            String title = String.format("%04d--%s", count ++, element.text());
            task.setExtra(title); // 章节名称

            System.out.println("new Chapter: " + title);

            dispatcher.addTask(task);
        }
    }

    private void getAllPage(Dispatcher dispatcher, Document document) {
        Elements elements = document.select("select option");
        for (int i = 0; i < elements.size(); i ++) {
            Task task = new Task();
            String url = "http://m.1xiaoshuo.com/tianyucangqiong/" + elements.get(i).val();
            task.setUrl(url);
            task.setFetcherType(FetcherFactory.FETCHER_HTML);
            task.setParserType(type);

            dispatcher.addTask(task);
        }
    }
}
