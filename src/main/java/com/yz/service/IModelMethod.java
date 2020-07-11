package com.yz.service;

import com.yz.consoleInterFace.Imethod;

public interface IModelMethod extends Imethod {

    /**
     * 新建
     */
    public void newView();

    /**
     * 打开文件
     *
     * @param filePath
     * @return
     */
    public boolean openFile(String filePath);

    /**
     * 保存
     *
     * @param path
     */
    public void save(String path);

    /**
     * 另存为
     *
     * @param path
     */
    public void saveAs(String path);

    /**
     * 清空
     */
    public void clear();

    /**
     * 还原
     */
    public void revert();

    /**
     * 退出
     */
    public void quit();

    /**
     * 版本显示
     */
    public void myHelloApp();

    /**
     * 加载脚本
     *
     * @param path
     */
    void loadScript(String path);

    /**
     * 写出脚本
     *
     * @param path
     */
    void startWriteScript(String path);

    /**
     * 关闭脚本写出
     */
    void stopWriteScript(String shellType);

    /**
     * 添加最近文件
     *
     * @param filePath
     */
    void addMenuItem(String filePath);
}
