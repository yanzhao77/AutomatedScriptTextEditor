package com.yz.controller;

import com.yz.CliUtilTool.FileUtilTools;
import com.yz.CliUtilTool.ShellInit;
import com.yz.CliUtilTool.ViewTools;
import com.yz.console.ConsoleTextAera;
import com.yz.console.ViewFileValueTextAera;
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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.fxmisc.flowless.VirtualizedScrollPane;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ViewController implements Initializable {

  @FXML
  public AnchorPane mainAnchorPane; // 显示面板的区域
  @FXML
  public AnchorPane topAnchorPane;
  public MenuBar fileMenuPane; // 文件栏
  public MenuItem newMenuItem,
          openMenuItem, // 新建，打开，清空，保存，另存，刷新，退出
          clearMenuItem,
          saveMenuItem,
          saveAsMenuItem,
          revertMenuItem,
          quitMenuItem;
  public Menu ScriptMenuItem, open_RecentMenu; // 自动化脚本,最近文件
  public MenuItem loadScriptMenuItem; // 加载脚本
  public CheckMenuItem writeScriptMenuItem; // 记录脚本
  public MenuItem myHelloAppMenuItem; // 帮助
  public Menu helpMenu;
  public Menu editMenu;
  public Menu fileMenu;

  @FXML
  public FlowPane toolsPane; // 工具栏
  public ToolBar toolsBarPane; // 工具条显示区
  @FXML // 打开文件，打开项目，保存，另存，写出脚本，加载脚本
  public Button openFile, openProject, save, saveAs, writeScript, loadScript;
  @FXML
  public SplitPane mainSplitPane; // 主视图SplitPane
  @FXML
  public AnchorPane navigationMainAnchorPane; // 导航区主窗口
  @FXML
  public TabPane navigationTabMenuTabPane; // 导航栏面板
  @FXML
  public Tab navigationTab; // 导航栏显示面板
  @FXML
  public Tab navigationFileTab; // 导航栏文件显示区域
  @FXML
  public SplitPane filenavigationFileAnchorPane; // 导航栏文件显示区SplitPane
  @FXML
  public AnchorPane treeViewAnchorPane; // 树视图显示文件目录
  @FXML
  TreeView filePathTreeView; // 文件路径显示
  @FXML
  public AnchorPane treeConsoleAnchorPane; // 显示该节点的操作面板
  @FXML
  public AnchorPane viewMainAnchorPane; // 视图区主窗口
  @FXML
  public SplitPane viewSplitPane; // 视图显示区域
  @FXML
  public AnchorPane viewAnchorPane; // 视图显示区域主窗口

  public TabPane viewTabPane; // 视图显示区域TabPane
  // 默认的界面
  public Tab viewAnchorPaneTab; // 视图显示区域Tab
  public AnchorPane viewShowAnchorPane; // 视图显示区
  public Canvas viewCanvas; // 视图显示区画布
  public ViewFileValueTextAera viewTextArea; // 打开的文件内容

  @FXML
  AnchorPane textAreaAnchorPane; // 控制台输入
  @FXML
  public AnchorPane bottmAncorPane; // 底部栏
  TreeViewFactory treeViewFactory; // 树表的工厂类
  ConsoleTextAera textAera; // 控制台
  Stage stage; // 主画布
  CoreFactroy coreFactroy; // 主控制器
  IModelMethod method; // 方法类
  public Map<String, List<String>> fileMap; // 输入的文件
  public String filePath; // 输入的文件路径
  public String saveFilePath; // 保存文件的路径

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    actionInit();
    addConsoleTera();
    toolsBarPane.setPrefWidth(mainAnchorPane.getPrefWidth());
    viewPaneTextArea("new File 1");
  }

  /**
   * 初始化视图区
   *
   * @param tabName
   */
  public void viewPaneTextArea(String tabName) {
    viewAnchorPaneTab = new Tab(tabName);
    viewTabPane.getTabs().add(viewAnchorPaneTab);
    viewShowAnchorPane = new AnchorPane();
    viewTextArea = new ViewFileValueTextAera("", coreFactroy);

    viewShowAnchorPane.getChildren().add(viewTextArea);
    viewAnchorPaneTab.setContent(viewShowAnchorPane);
    AnchorPane.setTopAnchor(viewTextArea, 0.0);
    AnchorPane.setBottomAnchor(viewTextArea, 0.0);
    AnchorPane.setRightAnchor(viewTextArea, 0.0);
    AnchorPane.setLeftAnchor(viewTextArea, 0.0);
    viewCanvas = new Canvas();
    viewShowAnchorPane.getChildren().add(viewCanvas);
    AnchorPane.setTopAnchor(viewCanvas, 0.0);
    AnchorPane.setBottomAnchor(viewCanvas, 0.0);
    AnchorPane.setRightAnchor(viewCanvas, 0.0);
    AnchorPane.setLeftAnchor(viewCanvas, 0.0);
  }

  /**
   * 点击事件
   */
  private void actionInit() {
    String style = "-fx-background-image: url('image/123456.jpg')";
    openFile.setStyle(openFile.getStyle() + style);
    newMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                method.newView();
              }
            });
    openMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                String path = ViewTools.openFile(stage);
                if (path != null) {
                  method.openFile(path);
                }
              }
            });

    clearMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                method.clear();
              }
            });
    saveMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
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
    saveAsMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                String saveFilePath = ViewTools.saveFile(stage);
                if (saveFilePath != null) {
                  method.saveAs(saveFilePath);
                }
              }
            });

    revertMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                method.revert();
              }
            });

    quitMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                method.quit();
              }
            });

    myHelloAppMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                method.myHelloApp();
              }
            });
    loadScriptMenuItem.setOnAction(
            new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent actionEvent) {
                String path = ViewTools.openScriptFile(stage);
                if (path != null) {
                  method.loadScript(path);
                }
              }
            });
    writeScriptMenuItem
            .selectedProperty()
            .addListener(
                    new ChangeListener<Boolean>() {
                      @Override
                      public void changed(
                              ObservableValue<? extends Boolean> observableValue,
                              Boolean aBoolean,
                              Boolean t1) {
                        if (t1) {
                          String path = ViewTools.saveScriptFile(stage);
                          if (path != null) {
                            method.startWriteScript(path);
                          } else {
                            writeScriptMenuItem.setSelected(false);
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

  /**
   * 初始化控制台
   */
  public void addConsoleTera() {
    textAera = new ConsoleTextAera();
    VirtualizedScrollPane virtualizedScrollPane = new VirtualizedScrollPane(textAera);
    textAreaAnchorPane.getChildren().add(virtualizedScrollPane);
    textAreaAnchorPane.setTopAnchor(virtualizedScrollPane, 0.0);
    textAreaAnchorPane.setBottomAnchor(virtualizedScrollPane, 0.0);
    textAreaAnchorPane.setLeftAnchor(virtualizedScrollPane, 0.0);
    textAreaAnchorPane.setRightAnchor(virtualizedScrollPane, 0.0);
    textAera
            .textProperty()
            .addListener(
                    new ChangeListener<String>() {
                      @Override
                      public void changed(
                              ObservableValue<? extends String> observableValue, String s, String t1) {
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


  /**
   * 新建一个tab显示区域
   *
   * @param fileName
   * @return
   */
  public ViewFileValueTextAera initviewTabPaneForNewTab(String fileName) {
    Tab tab = new Tab(fileName);
    viewTabPane.getTabs().add(0, tab);
    viewTabPane.getTabs().add(tab);
    AnchorPane viewAnchorPane = new AnchorPane();
    ViewFileValueTextAera viewTextArea = new ViewFileValueTextAera("", coreFactroy);
    viewAnchorPane.getChildren().add(viewTextArea);
    tab.setContent(viewAnchorPane);
    AnchorPane.setTopAnchor(viewTextArea, 0.0);
    AnchorPane.setBottomAnchor(viewTextArea, 0.0);
    AnchorPane.setRightAnchor(viewTextArea, 0.0);
    AnchorPane.setLeftAnchor(viewTextArea, 0.0);
    Canvas viewCanvas = new Canvas();
    viewAnchorPane.getChildren().add(viewCanvas);
    AnchorPane.setTopAnchor(viewCanvas, 0.0);
    AnchorPane.setBottomAnchor(viewCanvas, 0.0);
    AnchorPane.setRightAnchor(viewCanvas, 0.0);
    AnchorPane.setLeftAnchor(viewCanvas, 0.0);
    return viewTextArea;
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
    return mainAnchorPane.getPrefHeight();
  }

  public double getPrefWidth() {
    return mainAnchorPane.getPrefWidth();
  }

  public Menu getOpen_RecentMenu() {
    return open_RecentMenu;
  }

  public void setOpen_RecentMenu(Menu open_RecentMenu) {
    this.open_RecentMenu = open_RecentMenu;
  }

  public TreeViewFactory getTreeViewFactory() {
    return treeViewFactory;
  }

  public void setTreeViewFactory(TreeViewFactory treeViewFactory) {
    this.treeViewFactory = treeViewFactory;
  }
}
