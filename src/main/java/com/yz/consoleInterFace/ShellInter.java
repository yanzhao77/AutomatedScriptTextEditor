package com.yz.consoleInterFace;


import java.io.File;
import java.io.PrintStream;

public interface ShellInter {

    /**
     * 执行命令
     *
     * @param command
     * @return
     */
    boolean execute(String command);

    /**
     * 执行脚本文件
     *
     * @param file
     * @return
     */
    boolean execFile(File file);
    /**
     * 返回解析器
     *
     * @return
     */
    ShellInter getShellInter();

    /**
     * 获得数据
     *
     * @param DataName
     * @return
     */
    Object getData(String DataName);

    /**
     * 保存对象
     *
     * @param key
     * @param value
     */
    void setData(String key, Object value);

    /**
     * 获得输出流
     *
     * @return
     */
    PrintStream getPstream();

    /**
     * 设置输出流
     *
     * @param pstream
     */
    void setPstream(PrintStream pstream);

    /**
     * 获得解析器名称
     *
     * @return
     */
    String getName();

    /**
     * 设置解析器名称
     *
     * @param name
     */
    void setName(String name);
}
