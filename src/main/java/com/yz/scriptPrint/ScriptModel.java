package com.yz.scriptPrint;

import com.yz.CliUtilTool.CliUtilTools;

import java.util.ArrayList;
import java.util.List;

public class ScriptModel {
    String name;
    String path;
    String scriptPath;
    List<String> commandList;
    List<String> methodPackagePathList;

    public ScriptModel(String name, String path) {
        this.name = name;
        this.path = path;
        this.scriptPath = path + "\\" + name;
        commandList = new ArrayList<>();
        methodPackagePathList = new ArrayList<>();
    }

    public ScriptModel(String name) {
        this.name = name;
        int value = (int) (Math.random() * 100);
        path = CliUtilTools.cteateTempFile(String.valueOf(value));
        commandList = new ArrayList<>();
        methodPackagePathList = new ArrayList<>();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<String> commandList) {
        this.commandList = commandList;
    }

    public List<String> getMethodPackagePathList() {
        return methodPackagePathList;
    }

    public void setMethodPackagePathList(List<String> methodPackagePathList) {
        this.methodPackagePathList = methodPackagePathList;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScriptPath() {
        return scriptPath;
    }

    public void setScriptPath(String scriptPath) {
        this.scriptPath = scriptPath;
    }
}
