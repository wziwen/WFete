package com.wziwen.wfete.dytt;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.IParser;
import com.wziwen.spider.fetcher.FetcherFactory;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wen on 2017/2/15.
 * 电影天堂, 页面爬去
 */
public class PageParser implements IParser<String> {
    private static final String BASE_URL = "http://www.xiaopian.com";

    private int type = 20;
    private int contentParserType = 21;

    public PageParser(int type, int contentParserType) {
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
            index = parentUrl.substring(parentUrl.indexOf("index_") + 6, parentUrl.indexOf(".html"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        int page;
        try {
            page = Integer.valueOf(index);
        } catch (Exception e) {
            page = 1;
        }

        int count = 25 * page;

        Elements elements = document.getElementsByClass("tbspan");
        for (int i = 1; i < elements.size(); i ++) {
            Elements element = elements.get(i).getElementsByClass("ulink");

            Task task = new Task();
            String url = BASE_URL + element.attr("href");
            task.setUrl(url);
            task.setFetcherType(FetcherFactory.FETCHER_HTML);
            task.setParserType(contentParserType);
            String title = String.format("%04d--%s", count ++, element.text());
            task.setExtra(title); // 名称

            // 通过正则获取电影评分, 但不是所有电影都有评分
            Pattern pattern = Pattern.compile("(\\d{1}\\.\\d{1}|\\d{1})分");
            Matcher matcher = pattern.matcher(title);
            double score = 0;
            if (matcher.find()) {
                try {
                    score = Double.parseDouble(matcher.group(0).replace("分", ""));
                } catch (Exception e) {
                    System.out.println("PageParser find score fail");
                    e.printStackTrace();
                }
            }

            // 只获取7分以上电影
            if (score >= 7.0F) {
                System.out.println("new Chapter: " + title);
                dispatcher.addTask(task);
            } else {
                System.out.println("skip Chapter: " + title);
            }
        }
    }

    /**
     * 获取所有的页面. 也可以直接根据规则生成所有页面链接
     * @param dispatcher
     * @param document
     */
    private void getAllPage(Dispatcher dispatcher, Document document) {
        Elements elements = document.select("select option");
        for (int i = 0; i < elements.size(); i ++) {
            Task task = new Task();
            String url = BASE_URL + elements.get(i).val();
            task.setUrl(url);
            task.setFetcherType(FetcherFactory.FETCHER_JS_WEB);
            task.setParserType(type);

            dispatcher.addTask(task);
        }
    }
}
