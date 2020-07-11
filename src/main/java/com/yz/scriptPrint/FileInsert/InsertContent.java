package com.yz.scriptPrint.FileInsert;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class InsertContent {

    /**
     * 读取文件获取关键字位置
     *
     * @param filePath
     * @param keyword
     * @return
     */
    public static int findPositionForKeyword(String filePath, String keyword) {
        int position = 0;
        try {
            Path path = Paths.get(filePath);
            String data = Files.readString(path, Charset.forName("UTF-8"));
            if (data.contains(keyword)) {
                position = data.lastIndexOf(keyword) + keyword.length() + 2;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return position;
    }

    /**
     * 写出文件
     *
     * @param file
     * @param cont
     * @param flag
     */
    public static void writeFile(File file, String cont, boolean flag) {
        try {
            FileWriter fileWriter = new FileWriter(file, flag);
            fileWriter.write(cont);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 指定位置插入数据
     *
     * @param filePath    文件路径
     * @param keyword     插入内容
     * @param contentList 插入点位置
     */
    public static void insertCon(String filePath, String keyword, List<String> contentList) {
        int position = findPositionForKeyword(filePath, keyword);
        RandomAccessFile raf = null;

        try {
            raf = new RandomAccessFile(filePath, "rw");//rw可读可写
            raf.seek(position);

            //先将插入点后面的内容保存起来
            StringBuilder stringBuilder = new StringBuilder();
            byte[] bytes = new byte[1024];
            int len;
            while ((len = raf.read(bytes)) != -1) {
                stringBuilder.append(new String(bytes, 0, len));
            }
            //************************
            raf.seek(position);//设置插入点位置
            for (String command : contentList) {
                raf.write("\r\n".getBytes());
            }
            raf.write(stringBuilder.toString().getBytes());//恢复插入点后的 数据
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
