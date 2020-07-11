package com.yz.controller;

import com.yz.coreFactroy.CoreFactroy;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class ViewControl {
    public Stage mainStage;
    CoreFactroy coreFactroy;
    public ViewController windows;

    public ViewControl() {
        coreFactroy = new CoreFactroy();
        CreateModules();
        windows.setCoreFactroy(coreFactroy);
        coreFactroy.setController(windows);
        windows.consoleInit();
        windows.fileMap = new HashMap<>();
    }


    public void CreateModules() {
        mainStage = new Stage();
        Image image = new Image("image/scriptDemo.png");
        mainStage.getIcons().add(image);
        String xml = "static/fxml/sample.fxml";
        windows = showWindows(xml, mainStage);

    }

    public ViewController showWindows(String fxmlPath, Stage stage) {
        FXMLLoader loader = CreateWindowsformScene(fxmlPath);
        ViewController controller = loader.getController();
        double screenwindth = controller.getPrefWidth();
        double screenHeight = controller.getPrefHeight();
//        int screenwindth = (Toolkit.getDefaultToolkit().getScreenSize().width) / 3 * 2;
//        int screenHeight = (Toolkit.getDefaultToolkit().getScreenSize().height) / 3 * 2 + 30;
        Scene scene = new Scene(load, screenwindth, screenHeight);
        stage.setScene(scene);
        controller.setStage(stage);
        stage.show();
        return controller;
    }

    Parent load = null;

    private FXMLLoader CreateWindowsformScene(String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlPath));
        Object load = null;
        try {
            load = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.load = (Parent) load;
        return loader;
    }
}
