package com.yz.service;

import com.yz.CliUtilTool.FileUtilTools;
import com.yz.consoleInterFace.Imethod;
import com.yz.controller.ViewController;
import com.yz.coreFactroy.CoreFactroy;
import com.yz.proxy.Interceptor;
import com.yz.proxy.ProxyFactory;
import com.yz.scriptPrint.ScriptModel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ModelMethodImp implements IModelMethod {

    CoreFactroy coreFactroy;

    public ModelMethodImp(CoreFactroy coreFactroy) {
        this.coreFactroy = coreFactroy;
    }

    public Imethod getProxy() {
        return getProxy(Interceptor.PARSE_INVOCATIONHANDLER);
    }

    public Imethod getProxy(String... interceptors) {
        Imethod proxy = (Imethod) ProxyFactory. getCglibProxy(this, coreFactroy);
        Imethod proxy2 = (Imethod) ProxyFactory.getCglibProxy(this, coreFactroy, Interceptor.PARSE_INVOCATIONHANDLER);
        return proxy;
    }

    @Override
    public boolean runScript(String filePath) {
        return false;
    }

    public void newView() {
        coreFactroy.getController().getTextAera().clean();
    }

    public boolean openFile(String filePath) {
        List<String> strings = FileUtilTools.readFile(filePath);
        strings.forEach(value -> coreFactroy.getController().getTextAera().println(value));
        coreFactroy.getController().fileMap.put(filePath, strings);
        coreFactroy.getController().filePath = filePath;
        addMenuItem(filePath);
        return false;
    }

    public void addMenuItem(String filePath) {
        Menu open_recentMenu = coreFactroy.getController().getOpen_RecentMenu();
        MenuItem item = null;
        if (open_recentMenu.getItems().size() == 0) {
            item = new MenuItem(filePath);
            open_recentMenu.getItems().add(item);
        }
        for (MenuItem menuItem : open_recentMenu.getItems()) {
            if (!menuItem.getText().equals(filePath)) {
                item = new MenuItem(filePath);
                open_recentMenu.getItems().add(item);
            }
        }

        MenuItem finalItem = item;
        if (item != null) {
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    String text = finalItem.getText();
                    if (text != null && !text.equals("")) {
                        openFile(text);
                    }
                }
            });
        }
    }

    public void save(String path) {
        if (coreFactroy.getController().saveFilePath != null) {
            coreFactroy.getController().saveFilePath = path;
        }
        File file = FileUtilTools.creteFile(path);
//        String text = coreFactroy.getController().getTextAera().getText();
//        text = text.replaceAll(" >\0\0|...\0", "");
        List<String> strings = coreFactroy.getController().fileMap.get(coreFactroy.getController().filePath);
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string + "\n");
        }
        FileUtilTools.writeFile(sb.toString(), file);
    }

    public void saveAs(String path) {
        File file = FileUtilTools.creteFile(path);
        List<String> strings = coreFactroy.getController().fileMap.get(coreFactroy.getController().filePath);
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            sb.append(string + "\n");
        }
        FileUtilTools.writeFile(sb.toString(), file);
    }

    public void clear() {
        coreFactroy.getController().getTextAera().clean();
    }

    public void revert() {
        coreFactroy.getController().getTextAera().println("刷新界面");
    }

    public void quit() {
        System.exit(0);
    }

    public void myHelloApp() {
        String value = "" +
                "离离原上草\n一岁一枯荣\n野火烧不尽\n春风吹又生"
                + "\t\n";
        Alert alert = new Alert(Alert.AlertType.INFORMATION, value);
        alert.showAndWait();
    }

    @Override
    public void loadScript(String path) {
        ViewController controller = coreFactroy.getController();
        File file = new File(path);
        if (file.exists()) {
            controller.getTextAera().getShellInter().execFile(file);
        }
    }

    @Override
    public void startWriteScript(String path) {
        String filePath = path.substring(0, path.lastIndexOf("\\"));
        String fileName = path.substring(path.lastIndexOf("\\") + 1);
        ScriptModel scriptModel = new ScriptModel(fileName, filePath);
        try {
            coreFactroy.getScriptPrintProxy().startWriteScriptFile(scriptModel);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopWriteScript(String shellType) {
        if (coreFactroy.getScriptPrintProxy().getScriptModel() != null) {
            try {
                ScriptModel scriptModel = coreFactroy.getScriptPrintProxy().getScriptModel();
                String shelltype = coreFactroy.getShellInter().getName();
                coreFactroy.getScriptPrintProxy().getScriptPrinter().setTemplatePathForShellType(shelltype);
                coreFactroy.getScriptPrintProxy().stopWriteScriptFile(scriptModel);
                coreFactroy.getScriptPrintProxy().setScriptModel(null);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
