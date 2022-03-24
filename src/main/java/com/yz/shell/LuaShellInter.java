package com.yz.shell;

import com.yz.consoleInterFace.ShellInter;
import org.luaj.vm2.Globals;
import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaUserdata;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.compiler.LuaC;
import org.luaj.vm2.lib.jse.JsePlatform;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;

public class LuaShellInter implements ShellInter {
    String name;
    Globals shell;//解析器
    private PrintStream pstream;

    public LuaShellInter() {
        this("");
    }

    public LuaShellInter(String name) {
        this.name = name;
        init();
    }

    public LuaShellInter(PrintStream pstream) {
        this.pstream = pstream;
        init();
    }

    public LuaShellInter(PrintStream pstream, String name) {
        this.pstream = pstream;
        this.name = name;
        init();
    }

    public void init() {
        shell = JsePlatform.standardGlobals();
    }

    public boolean execute(String cmd) {
        if (cmd.contains("\\")) {
            cmd = cmd.replace("\\", "\\\\");
        }
        boolean flag;

        try {
            Object evaluate = shell.load(cmd).touserdata();
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean execFile(File file) {
        boolean flag=true;

//        try {
//            Object evaluate = shell.load().touserdata();
//            flag = true;
//        } catch (ParseException | IOException e) {
//            e.printStackTrace();
//            flag = false;
//        }
        return flag;
    }

    @Override
    public ShellInter getShellInter() {
        return this;
    }

    @Override
    public Object getData(String dataName) {
        return shell.get(dataName).touserdata();
    }

    @Override
    public void setData(String key, Object value) {
        shell.set(key, new LuaUserdata(value));
    }

    @Override
    public PrintStream getPstream() {
        return pstream;
    }

    @Override
    public void setPstream(PrintStream pstream) {
        this.pstream = pstream;
//        shell.set("out", pstream);
//        shell.setProperty("err", pstream);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 将消息发到流
     *
     * @param printStream
     */
    public void pStreamConsoleListtener(PrintStream printStream) {
        pstream = printStream;
        System.setOut(pstream);//拦截输出流
    }


}
