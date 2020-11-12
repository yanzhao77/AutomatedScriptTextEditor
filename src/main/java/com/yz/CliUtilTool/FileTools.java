package com.yz.CliUtilTool;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileTools {
    static int size = 1;//主要是为了控制循环的次数，因为是定时刷，每次刷的文件行数可能不一样
    static long chars = 0;//chars指的是字符数

    /**
     * 读取文件内容
     *
     * @param fileName
     */
    public Map readANDwrite(String fileName) {

        //大集合，以sessionid为键，以一次session的所有访问记录list为值进行存储
        Map<String, Map> bigMap = new HashMap();

        //一次session的访问记录集合
        File file = new File(fileName);

        //java提供的一个可以分页读取文件的类,此类的实例支持对随机访问文件的读取和写入
        RandomAccessFile rf = null;

        String tempString = null;
        try {

            //初始化RandomAccessFile，参数一个为文件路径，一个为权限设置，这点与Linux类似，r为读，w为写
            rf = new RandomAccessFile(fileName, "rw");

            //设置到此文件开头测量到的文件指针偏移量，在该位置发生下一个读取或写入操作
            rf.seek(chars);


            //获取文件的行数
            int fileSize = getTotalLines(file);
            for (int i = size - 1; i < fileSize; i++) {//从上一次读取结束时的文件行数到本次读取文件时的总行数中间的这个差数就是循环次数

                //一行一行读取
                tempString = rf.readLine();
                //文件中文乱码处理
                tempString = tempString.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
                tempString = tempString.replaceAll("\\+", "%2B");
                tempString = java.net.URLDecoder.decode(tempString, "GB2312");

                //将字符串JSON转换为实体JSON，以便能通过key取value
//                JSONObject json = JSONObject.fromObject(tempString);
//                String refPage = json.get("refPage").toString();
                String refPage = "";
                System.out.println(refPage);
                Map tmap = new HashMap();

                if (bigMap.containsKey(refPage))
                    tmap = (Map) bigMap.get(refPage);
                else {
                    tmap = new HashMap();
                }
                // 计数
                String tCount = "count";
                int pvCount = 1;
                if (tmap.containsKey(tCount)) {
                    pvCount = (Integer) tmap.get(tCount);
                }
                pvCount++;
                tmap.put(tCount, pvCount);
                bigMap.put(refPage, tmap);
            }
            //返回此文件中的当前偏移量。
            chars = rf.getFilePointer();
            size = fileSize;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (rf != null) {
                try {
                    rf.close();
                } catch (IOException e1) {
                }
            }
        }
        return bigMap;
    }

    //获取文件的行数
    static int getTotalLines(File file) throws IOException {
        FileReader in = new FileReader(file);
        LineNumberReader reader = new LineNumberReader(in);
        String s = reader.readLine();
        int lines = 0;
        while (s != null) {
            lines++;
            s = reader.readLine();
        }
        reader.close();
        in.close();
        return lines;
    }
}
