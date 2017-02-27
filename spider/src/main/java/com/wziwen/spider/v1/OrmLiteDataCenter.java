package com.wziwen.spider.v1;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.wziwen.spider.IDataCenter;
import com.wziwen.spider.Task;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ziwen.wen on 2017/2/10.
 * DataCenter using OrmLite
 * 数据库存储, 默认使用jdbc:sqlite:ormLite.db, 可以设定数据库名称{@link this#setDbName(String)};
 * 或者直接使用自己的连接(比如Android上用 {@link com.j256.ormlite.android.AndroidConnectionSource}
 */
public class OrmLiteDataCenter implements IDataCenter<Task> {

    ConnectionSource connectionSource;
    Dao<Task, String> taskDao;
    String dbName = "ormLite.db";

    public OrmLiteDataCenter() {
    }

    public void setConnectionSource(ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    private JdbcConnectionSource createJDBCSource() throws SQLException {
        String databaseUrl = "jdbc:sqlite:" + dbName;
        return new JdbcConnectionSource(databaseUrl);
    }

    public void open() {
        try {
            if (connectionSource == null) {
                connectionSource = createJDBCSource();
            }
            taskDao = DaoManager.createDao(connectionSource, Task.class);

            TableUtils.createTable(connectionSource, Task.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (connectionSource != null) {
            try {
                taskDao = null;
                connectionSource.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void saveTask(Task task) {
        try {
            taskDao.create(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(Task task) {
        try {
            taskDao.delete(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateTask(Task task) {
        try {
            taskDao.update(task);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Task> listTask() {
        List<Task> taskList = null;
        try {
            taskList = taskDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;
    }

    public List<Task> listUnFinishedTask() {
        List<Task> taskList = null;
        try {
            taskDao.queryBuilder();
            QueryBuilder<Task, String> qb = taskDao.queryBuilder();
            qb.where().eq("finished", 0);
            taskList = taskDao.query(qb.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return taskList;

    }
}
