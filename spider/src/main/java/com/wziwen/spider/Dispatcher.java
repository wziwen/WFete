package com.wziwen.spider;

import com.wziwen.spider.fetcher.FetcherFactory;
import com.wziwen.spider.v1.ParseProvider;
import com.wziwen.spider.v1.TaskException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ziwen.wen on 2017/2/10.
 * 任务管理类
 */
public class Dispatcher  {

    private int threadCount = 50;

    private List<Thread> threadList = new ArrayList<Thread>(10);

    private FetcherFactory fetcherFactory = new FetcherFactory();

    public FetcherFactory getFetcherFactory() {
        return fetcherFactory;
    }

    public ParseProvider getParseProvider() {
        return parseProvider;
    }

    public Dispatcher addParser(int id, IParser parser) {
        parseProvider.addParser(id, parser);
        return this;
    }

    private ParseProvider parseProvider = new ParseProvider();

    private IDataCenter<Task> dataCenter;

    private List<Task> taskList = new LinkedList<Task>();

    private boolean started = false;

    public Dispatcher() {

    }

    public Dispatcher setDataCenter(IDataCenter<Task> dataCenter) {
        this.dataCenter = dataCenter;
        dataCenter.open();
        return this;
    }

    /**
     * @param count
     */
    public Dispatcher setThreadCount(int count) {
        if (count >= 1) {
            this.threadCount = count;
        }
        return this;
    }

    public synchronized Dispatcher start() {
        started = true;
        taskList = dataCenter.listUnFinishedTask();
        checkTaskAndRun();
        return this;
    }

    private void checkTaskAndRun() {
        if (!started) {
            return;
        }

        if (taskList != null) {
            int leftThreadCount = threadCount - threadList.size();
            for (int i = 0; i < taskList.size() && i < leftThreadCount; i++) {
                TaskRunnable taskRunnable = new TaskRunnable();
                Thread thread = new Thread(taskRunnable);
                taskRunnable.setThread(thread);
                threadList.add(thread);
                thread.start();
                System.out.println("Dispatcher: start thread:" + i);
            }
        }
    }

    public Dispatcher addTask(Task task) {
        task.setCreateTime(System.currentTimeMillis());
        dataCenter.saveTask(task);

        if (taskList != null) {
            checkTaskAndRun();
        }
        return this;
    }

    public void updateTask(Task task) {
        task.setUpdateTime(System.currentTimeMillis());
        dataCenter.updateTask(task);
    }

    public void deleteTask(Task task) {
        dataCenter.deleteTask(task);
    }

    public void dispatch(Task task) {

    }

    public void waitForComplete() {
        long startTime = System.currentTimeMillis();
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (threadList == null || threadList.size() == 0) {
                break;
            }

            boolean allThreadFinished = true;
            for (Thread thread : threadList) {
                if (!thread.isInterrupted()) {
                    allThreadFinished = false;
                    break;
                }
            }

            if (allThreadFinished) {
                break;
            }
        }

        long time = System.currentTimeMillis() - startTime;
        System.out.println("Dispatcher wait for complete: " + time);

    }

    private class TaskRunnable implements Runnable {

        Thread thread;

        int sleepTime = 5000;
        int sleepCount = 10;
        int currentSleepCount = 0;

        public void setThread(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            System.out.println("Dispatcher: TaskRunnable start running");
            while (true) {
                Task task = getTask();
                if (task != null) {
                    // reset sleep count
                    currentSleepCount = 0;

                    try {
                        // do task fetch
                        IFetcher fetcher = fetcherFactory.createFetcher(task.getFetcherType());
                        Object object = fetcher.fetch(task);

                        // do parse
                        IParser parser = parseProvider.getParser(task.getParserType());
                        parser.parse(Dispatcher.this, task, object);

                        // if not TaskException throw, take task as finished
                        task.setFinished(1);
                        updateTask(task);
                    } catch (TaskException e) {
                        System.out.println(task.toString());
                        e.printStackTrace();
                    }
                } else {
                    // if no task, wait for next task or break
                    if (currentSleepCount < sleepCount) {
                        currentSleepCount++;
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        break;
                    }
                }
            }

            // 没有任务退出线程, 从运行的线程列表中移除当前线程
            threadList.remove(thread);
        }
    }

    /**
     * 获取下一个任务. 如果内存中没有时会尝试从数据库获取
     * @return
     */
    private synchronized Task getTask() {
        if (taskList == null || taskList.size() == 0) {
            taskList = dataCenter.listUnFinishedTask();
            if (taskList == null || taskList.size() == 0) {
                return null;
            }
        }
        Task task = taskList.get(0);
        taskList.remove(0);
        return task;
    }

}
