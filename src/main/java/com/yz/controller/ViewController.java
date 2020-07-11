package com.yz.controller;

import com.yz.CliUtilTool.FileUtilTools;
import com.yz.CliUtilTool.ShellInit;
import com.yz.CliUtilTool.ViewTools;
import com.yz.console.ConsoleTextAera;
import com.yz.consoleInterFace.Imethod;
import com.yz.coreFactroy.CoreFactroy;
import com.yz.service.IModelMethod;
import com.yz.service.ModelMethodImp;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewController implements Initializable {
    public MenuItem newMenuItem;
    public MenuItem openMenuItem;
    public Menu open_RecentMenu;


    public MenuItem clearMenuItem;
    public MenuItem saveMenuItem;
    public MenuItem saveAsMenuItem;
    public MenuItem revertMenuItem;

    public MenuItem quitMenuItem;

    public MenuItem myHelloAppMenuItem;
    @FXML
    public VBox mainVbox;
    public Menu ScriptMenuItem;
    public MenuItem scriptLoad;
    public CheckMenuItem writeScript;

    @FXML
    AnchorPane textAreaAnchorPane;


    ConsoleTextAera textAera;
    Stage stage;
    CoreFactroy coreFactroy;
    IModelMethod method;
    public Map<String, List<String>> fileMap;//输入的文件
    public String filePath;//输入的文件路径
    public String saveFilePath;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actionInit();
        addConsoleTera();

    }

    /**
     * 点击事件
     */
    private void actionInit() {
        newMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                method.newView();
            }
        });
        openMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String path = ViewTools.openFile(stage);
                if (path != null) {
                    method.openFile(path);
                }
            }
        });

        clearMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                method.clear();
            }
        });
        saveMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (saveFilePath == null) {
                    String saveFilePath = ViewTools.saveFile(stage);
                    if (saveFilePath != null) {
                        method.save(saveFilePath);
                    }
                } else {
                    method.save(saveFilePath);
                }
            }
        });
        saveAsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String saveFilePath = ViewTools.saveFile(stage);
                if (saveFilePath != null) {
                    method.saveAs(saveFilePath);
                }
            }
        });


        revertMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                method.revert();
            }
        });

        quitMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                method.quit();
            }
        });


        myHelloAppMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                method.myHelloApp();
            }
        });
        scriptLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String path = ViewTools.openScriptFile(stage);
                if (path != null) {
                    method.loadScript(path);
                }
            }
        });
        writeScript.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean aBoolean, Boolean t1) {
                if (t1) {
                    String path = ViewTools.saveScriptFile(stage);
                    if (path != null) {
                        method.startWriteScript(path);
                    } else {
                        writeScript.setSelected(false);
                    }

                } else {
                    method.stopWriteScript(textAera.getShellInter().getName());
                }
            }
        });

    }

    /**
     * 输出打印
     *
     * @param text
     */
    public void println(String text) {
        textAera.println(text);
    }

    /**
     * 彩色打印
     *
     * @param text  内容
     * @param color 颜色如（red）
     */
    public void println(String text, String color) {
        textAera.println(text, color);
    }

    public void addConsoleTera() {
        textAera = new ConsoleTextAera();
        VirtualizedScrollPane virtualizedScrollPane = new VirtualizedScrollPane(textAera);
        textAreaAnchorPane.getChildren().add(virtualizedScrollPane);
        textAreaAnchorPane.setTopAnchor(virtualizedScrollPane, 0.0);
        textAreaAnchorPane.setBottomAnchor(virtualizedScrollPane, 0.0);
        textAreaAnchorPane.setLeftAnchor(virtualizedScrollPane, 0.0);
        textAreaAnchorPane.setRightAnchor(virtualizedScrollPane, 0.0);
        textAera.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (filePath != null && fileMap != null) {
                    FileUtilTools.textAeraAddListener(filePath, textAera, fileMap);
                }
            }
        });
    }

    public void consoleInit() {
        textAera.init(coreFactroy);
        method = (IModelMethod) new ModelMethodImp(coreFactroy).getProxy();
        Object[] objects = ShellInit.addShellServer(coreFactroy);
        ShellInit.ShellInterInit(textAera.getShellInter(), objects);
        coreFactroy.setShellInter(textAera.getShellInter());
        coreFactroy.getScriptPrintProxy().setCoreFactroy(coreFactroy);
    }


    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public CoreFactroy getCoreFactroy() {
        return coreFactroy;
    }

    public void setCoreFactroy(CoreFactroy coreFactroy) {
        this.coreFactroy = coreFactroy;
    }

    public ConsoleTextAera getTextAera() {
        return textAera;
    }

    public void setTextAera(ConsoleTextAera textAera) {
        this.textAera = textAera;
    }

    public Imethod getImethod() {
        return method;
    }

    public void setImethod(Imethod imethod) {
        this.method = (ModelMethodImp) imethod;
    }


    public double getPrefHeight() {
        return mainVbox.getPrefHeight();
    }

    public double getPrefWidth() {
        return mainVbox.getPrefWidth();
    }

    public Menu getOpen_RecentMenu() {
        return open_RecentMenu;
    }

    public void setOpen_RecentMenu(Menu open_RecentMenu) {
        this.open_RecentMenu = open_RecentMenu;
    }
}
