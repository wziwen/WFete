package com.wziwen.wfete.google_ip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ziwen.wen on 2017/3/16.
 * 通过网上提供的ping功能来找到本机可用的google ip
 */
public class FindIp {
    public static void main(String[] args) {

        String path = "C:\\Users\\ziwen.wen\\Desktop\\ping_ip_source.txt";
        String line;
        File file = new File(path);
        try {
            Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+");
            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            while ((line = reader.readLine()) != null) {
//                if (!line.contains("超时")) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        String ip = matcher.group(0);
                        boolean success = ping(ip);
                        if (success) {
                            System.out.println("ping success IP: " + ip);
                        } else {
//                            System.out.println("ping fail IP: " + ip);
                        }
                    }
//                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finished");
    }

    private static boolean ping(String ip) {
        int  timeOut =  3000 ;  //超时应该在3钞以上
        boolean status = false;     // 当返回值是true时，说明host是可用的，false则不可。
        try {
            status = InetAddress.getByName(ip).isReachable(timeOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return status;
    }
}
