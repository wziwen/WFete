package com.wziwen.spider.fetcher;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.wziwen.spider.IFetcher;
import com.wziwen.spider.Task;
import com.wziwen.spider.v1.TaskException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by wen on 2017/2/10.
 */
public class JsWebFetcher implements IFetcher<Task, String>{

    @Override
    public String fetch(Task task) throws TaskException {
        try {
            WebClient wc = new WebClient();
            wc.setJavaScriptEnabled(true);
            wc.setCssEnabled(false);
            wc.setThrowExceptionOnScriptError(false);
            wc.setTimeout(10000);
            HtmlPage page = wc.getPage(task.getUrl());
            String pageXml = page.asXml();

            /**jsoup解析文档*/
            Document document = Jsoup.parse(pageXml);
            String html = document.html();
            wc.closeAllWindows();
            return html;
        } catch (IOException e) {
            throw new TaskException("String fetcher error", e);
        }
    }
}
