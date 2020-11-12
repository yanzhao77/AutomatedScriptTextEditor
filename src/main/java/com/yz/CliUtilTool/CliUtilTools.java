package com.yz.CliUtilTool;

import java.io.File;
import java.io.IOException;

public class CliUtilTools {

    /**
     * 创建临时文件
     *
     * @param fileName
     * @return
     */
    public static String cteateTempFile(String fileName) {
        File temp = null;
        String name = null;
        String suffix = null;
        if (fileName.contains(".")) {

            name = fileName.substring(0, fileName.indexOf("."));
            suffix = fileName.substring(fileName.indexOf("."));
        }
        try {
            temp = File.createTempFile(name, suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp.getParentFile().getPath();
    }
}
