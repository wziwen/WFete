package com.wziwen.spider;

import java.util.List;

/**
 * Created by ziwen.wen on 2017/2/10.
 */
public interface IDataCenter<T> {

    void open();

    void close();

    void saveTask(T task);

    void deleteTask(T task);

    void updateTask(T task);

    List<T> listTask();

    List<T> listUnFinishedTask();
}
