package com.wziwen.spider;

import com.wziwen.spider.v1.TaskException;

/**
 * Created by ziwen.wen on 2017/2/10.
 */
public interface IParser<DATA> {

    void parse(Dispatcher dispatcher, Task task, DATA data) throws TaskException;

}
