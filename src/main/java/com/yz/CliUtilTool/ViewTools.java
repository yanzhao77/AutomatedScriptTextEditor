package com.yz.CliUtilTool;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ViewTools {
    static String filePath = null;

    /**
     * 打开文件
     *
     * @param stage
     * @return
     */
    public static String openFile(Stage stage) {
        List<FileChooser.ExtensionFilter> list = new ArrayList<>();
        list.add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        String fielPath = null;
        fielPath = openFileForFile(stage, "打开文件", list);
        return fielPath;
    }

    /**
     * 打开文件
     *
     * @param stage
     * @return
     */
    public static String openScriptFile(Stage stage) {
        List<FileChooser.ExtensionFilter> list = new ArrayList<>();
        list.add(new FileChooser.ExtensionFilter("Python脚本文件", "*.py"));
        list.add(new FileChooser.ExtensionFilter("groovy脚本文件", "*.groovy"));
        list.add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        String fielPath = null;
        fielPath = openFileForFile(stage, "打开文件", list);
        return fielPath;
    }

    public static String saveScriptFile(Stage stage) {
        List<FileChooser.ExtensionFilter> list = new ArrayList<>();

        list.add(new FileChooser.ExtensionFilter("Python脚本文件", "*.py"));
        list.add(new FileChooser.ExtensionFilter("groovy脚本文件", "*.groovy"));
        list.add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        String fielPath = null;
        fielPath = writFileChooser(stage, "打开文件", list);
        return fielPath;
    }

    public static String saveFile(Stage stage) {
        List<FileChooser.ExtensionFilter> list = new ArrayList<>();
        list.add(new FileChooser.ExtensionFilter("All Files", "*.*"));
        String fielPath = null;
        fielPath = writFileChooser(stage, "打开文件", list);
        return fielPath;
    }

    /**
     * 保存文件
     *
     * @param stage
     * @param title
     * @param list
     * @return
     */
    private static String writFileChooser(Stage stage, String title, List<FileChooser.ExtensionFilter> list) {
        FileChooser fileChooser = new FileChooser();
        if (filePath != null) {
            fileChooser.initialDirectoryProperty().set(new File(filePath));//记录上次位置
        }
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(list);
        File file = fileChooser.showSaveDialog(stage);
        if (file != null) {
            filePath = file.getPath().substring(0, file.getPath().lastIndexOf("\\"));
            return file.getPath();
        }
        return null;
    }


    /**
     * 打开文件窗口
     *
     * @param stage
     * @param title
     * @param list
     * @return
     */
    public static String openFileForFile(Stage stage, String title, List<FileChooser.ExtensionFilter> list) {
        FileChooser fileChooser = new FileChooser();
        if (filePath != null) {
            fileChooser.initialDirectoryProperty().set(new File(filePath));//记录上次位置
        }
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(list);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            filePath = file.getPath().substring(0, file.getPath().lastIndexOf("\\"));
            return file.getPath();
        }
        return null;
    }
}
