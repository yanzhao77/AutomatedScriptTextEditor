package com.yz.console;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// @SpringBootTest
class ConsoleApplicationTests {

    @Test
    void contextLoads() {
        int random = (int) (Math.random() * 100);
        System.out.println(random);
    }

    @Test
    public void asdsdfs() {
        String filepath = "C:\\Users\\yan34177\\Documents\\My eBooks\\help.xml";
        File file = new File(filepath);
        if (file.exists()) {
            //            System.out.println("文件存在");
            List<String> fileParentForFileParentPath = isFileParentForFileParentPath(file);
            System.out.println(Arrays.toString(fileParentForFileParentPath.toArray()));
        }
    }

    /**
     * 递归查询最大的层级目录
     *
     * @param file 当前打开的文件
     * @return 返回分区磁盘的名称
     */
    public String isFileParent(File file) {
        return file.getParent() != null ? isFileParent(file.getParentFile()) : file.getPath();
    }

    /**
     * 递归查询最大的层级目录
     *
     * @param file 当前打开的文件
     * @return 返回分区磁盘的名称
     */
    public List<String> isFileParentForFileParentPath(File file) {
        List<String> filePathList = new ArrayList<>();
        if (file.getParent() != null) {
            filePathList.add(file.getPath());
            return isFileParentForFileParentPath(file.getParentFile());
        } else {
            return filePathList;
        }
    }
}
