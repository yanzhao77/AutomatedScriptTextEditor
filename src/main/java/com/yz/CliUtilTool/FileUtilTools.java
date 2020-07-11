package com.yz.CliUtilTool;

import com.yz.console.ConsoleTextAera;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FileUtilTools {

    /**
     * 创建文件以及级联文件夹
     *
     * @param path
     * @return
     */
    public static File creteFile(String path) {
        File file = new File(path);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static File writeFile(String value, File file) {
        File newFile = creteFile(file.getPath());
        try {
            FileWriter writer = new FileWriter(file, false);
            PrintWriter printWriter = new PrintWriter(writer);
            printWriter.println(value);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFile;
    }

    public static List<String> readFile(String path) {
        List<String> stringList = new ArrayList<>();
        File file = new File(path);
        BufferedReader reader = null;

        try {
            //以行为单位读取文件内容，一次读一整行：
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
//                System.out.println("line " + line + ": ");
                stringList.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return stringList;
    }

    public static void textAeraAddListener(String filePath, ConsoleTextAera textAera, Map<String, List<String>> map) {
        List<String> strings = new ArrayList<>();
        String text = textAera.getText();
        text = text.replaceAll(" >\0\0|...\0", "");
        String[] split = text.split("\n");
        for (String value : split) {
            strings.add(value);
        }
        map.put(filePath, strings);
    }
}
