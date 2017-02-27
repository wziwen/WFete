package com.wziwen.spider.fetcher;

import com.wziwen.spider.IFetcher;

/**
 * Created by ziwen.wen on 2017/2/10.
 * FetcherFactory
 */
public class FetcherFactory {
    public static final int FETCHER_DEFAULT = 0;
    public static final int FETCHER_FILE = 1;
    public static final int FETCHER_HTML = 2;
    public static final int FETCHER_JS_WEB = 3;


    public IFetcher createFetcher(int type) {
        switch (type) {
            case FETCHER_JS_WEB:
                return new JsWebFetcher();
            case FETCHER_HTML:
                return new StringFetcher();
            default:
                return new BaseFetcher();
        }
    }

}
