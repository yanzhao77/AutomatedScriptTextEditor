package com.yz.coreFactroy;


import com.yz.consoleInterFace.ShellInter;
import com.yz.controller.ViewController;
import com.yz.scriptPrint.ScriptPrintProxy;

/**
 * 视图工厂
 */
public class CoreFactroy implements CoreImp {
    ViewController controller;
    ScriptPrintProxy scriptPrintProxy;//脚本写出
    ShellInter shellInter;

    public CoreFactroy() {
        scriptPrintProxy = new ScriptPrintProxy();
    }

    public ViewController getController() {
        return controller;
    }

    public void setScriptPrintProxy(ScriptPrintProxy scriptPrintProxy) {
        this.scriptPrintProxy = scriptPrintProxy;
    }

    public ScriptPrintProxy getScriptPrintProxy() {
        return scriptPrintProxy;
    }

    public ShellInter getShellInter() {
        return shellInter;
    }

    public void setShellInter(ShellInter shellInter) {
        this.shellInter = shellInter;
    }

    public void setController(ViewController controller) {
        this.controller = controller;
    }
}
