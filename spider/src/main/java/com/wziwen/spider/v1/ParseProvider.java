package com.wziwen.spider.v1;

import com.wziwen.spider.IParser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wen on 2017/2/15.
 */
public class ParseProvider {

    Map<Integer, IParser> parserMap = new HashMap<Integer, IParser>();

    public IParser getParser(int type) {
        IParser parser = parserMap.get(type);
        if (parser != null) {
            return parser;
        }

        switch (type) {
//            case FETCHER_HTML:
//                break;
            default:
                return new FileSaver();
        }
    }

    public ParseProvider addParser(int type, IParser parser) {
        parserMap.put(type, parser);
        return this;
    }
}
