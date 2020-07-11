package com.yz.shell;

import com.yz.consoleInterFace.ShellInter;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilationFailedException;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class GroovyShellInter implements ShellInter {
    String name;
    GroovyShell shell;//解析器
    private PrintStream pstream;

    public GroovyShellInter() {
    }

    public GroovyShellInter(PrintStream pstream) {
        init();
        setPstream(pstream);
        shell.resetLoadedClasses();
    }

    public GroovyShellInter(PrintStream pstream, String name) {
        this.name = name;
        init();
        setPstream(pstream);
        shell.resetLoadedClasses();
    }

    public void init() {
        Binding binding = new Binding();
        shell = new GroovyShell(binding);
    }

    public boolean execute(String cmd) {
        if (cmd.contains("\\")) {
            cmd = cmd.replace("\\", "\\\\");
        }
        boolean flag;

        try {
            Object evaluate = shell.evaluate(cmd);
            flag = true;
        } catch (CompilationFailedException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean execFile(File file) {
        boolean flag;

        try {
            Object evaluate = shell.evaluate(file);
            flag = true;
        } catch (CompilationFailedException | IOException e) {
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    @Override
    public ShellInter getShellInter() {
        return this;
    }

    @Override
    public Object getData(String dataName) {
        return shell.getProperty(dataName);
    }

    @Override
    public void setData(String key, Object value) {
        shell.setProperty(key, value);
    }

    @Override
    public PrintStream getPstream() {
        return pstream;
    }

    @Override
    public void setPstream(PrintStream pstream) {
        this.pstream = pstream;
        shell.setProperty("out", pstream);
        shell.setProperty("err", pstream);
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

    public GroovyShell getShell() {
        return shell;
    }

    public void setShell(GroovyShell shell) {
        this.shell = shell;
    }
}
