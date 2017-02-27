package com.wziwen.spider.v1;

import com.wziwen.spider.Dispatcher;
import com.wziwen.spider.IParser;
import com.wziwen.spider.Task;

import java.io.*;

/**
 * Created by wen on 2017/2/15.
 */
public class FileSaver implements IParser<InputStream> {

    public void parse(Dispatcher dispatcher, Task task, InputStream inputStream) throws TaskException{
        String path = "file_saver.txt";

        File file = new File(path);
        byte[] buffer = new byte[4096];
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            int length;
            while (-1 != (length = inputStream.read(buffer))) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new TaskException("FileSaver parse error", e);
        } finally {
            // handle stream close error
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
