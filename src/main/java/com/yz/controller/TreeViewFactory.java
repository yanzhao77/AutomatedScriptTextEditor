package com.yz.controller;

import com.yz.coreFactroy.CoreFactroy;
import javafx.scene.control.TreeView;

public class TreeViewFactory {
    TreeView treeView; // 树表
    CoreFactroy coreFactroy; // 主控制器

    public TreeViewFactory(TreeView treeView, CoreFactroy coreFactroy) {
        this.treeView = treeView;
        this.coreFactroy = coreFactroy;
    }

    public TreeViewFactory(TreeView filePathTreeView) {
        this.treeView = filePathTreeView;
    }

    /**
     * 将这个文件的层级目录显示
     *
     * @param filePath 导入文件的绝对路径
     */
    public void addFilePath(String filePath) {
        System.out.println(filePath);
    }

}
