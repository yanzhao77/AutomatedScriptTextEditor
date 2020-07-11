package com.yz.scriptPrint;

import com.yz.scriptPrint.FileInsert.InsertContent;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ScriptPrinter implements State {
    //静态常量
    public static final int STOP_RECORD = 0;
    public static final int START_RECORD = 1;


    private int objNum;//对象计数器
    Map<String, ScriptModel> modelMap;//key为脚本名称，
    private String templatePath;//模板文件路径

    public ScriptPrinter() {

        modelMap = new HashMap<>();
    }

    public ScriptPrinter(ScriptModel scriptModel) {

        modelMap = new HashMap<>();
        modelMap.put(scriptModel.getName(), scriptModel);
    }

    @Override
    public void setScriptPrinterState(int state, String scriptName) throws IOException {
        if (state == 1) {
            openScriptFile(scriptName);
        } else if (state == 0) {
            closeScriptFile(scriptName);
        }
    }

    void openScriptFile(String scriptName) throws IOException {
        ScriptModel scriptModel = modelMap.get(scriptName);
        String scriptPath = scriptModel.getScriptPath();
        File file = new File(scriptPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        objNum = 5;
//        addScriptFile(file);
    }


    void closeScriptFile(String scriptName) {
        ScriptModel scriptModel = modelMap.get(scriptName);
        String templatePath = getTemplatePath();
        File file = new File(templatePath);
        if (file.exists()) {
            String path = file.getParentFile().getPath();
            createScript(path, scriptModel);
        }
    }

    /**
     * temp模板文件写出
     *
     * @param scriptTemplatePath
     * @param scriptModel
     */
    private void createScript(String scriptTemplatePath, ScriptModel scriptModel) {
        FileWriter writer = null;

        try {
            Configuration configuration = new Configuration();
            //指定写出文件的编码格式
            configuration.setDefaultEncoding("UTF-8");
            //设置模板文件路径
            configuration.setDirectoryForTemplateLoading(new File(scriptTemplatePath));
            //设置异常处理器
            configuration.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            //获得模板文件路径
            Template template;

            String templatePath = getTemplatePath();
            String templateFileName = templatePath.substring(templatePath.lastIndexOf("/") + 1);
            template = configuration.getTemplate(templateFileName);
            File file = new File(scriptModel.getScriptPath());
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file);
            //生成文件
            template.process(scriptModel, writer);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public int getObjNum() {
        return objNum;
    }

    public void setObjNum(int objNum) {
        this.objNum = objNum;
    }

    public Map<String, ScriptModel> getModelMap() {
        return modelMap;
    }

    public void setModelMap(Map<String, ScriptModel> modelMap) {
        this.modelMap = modelMap;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePathForShellType(String shellType) {
        String shellName = null;
        if (shellType.toLowerCase().equals("groovy")) {
            shellName = "Groovy";
        } else if (shellType.toLowerCase().equals("python")) {
            shellName = "Python";
        }
        URL resource = getClass().getClassLoader().getResource("templates/scriptTemplateFor" + shellName + ".ftl");
        this.templatePath = resource.getFile();
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 手动写出文件
     *
     * @param file
     */
    private void addScriptFile(File file) {
        InsertContent.writeFile(file, getTemplateCont(), false);
    }

    private String getTemplateCont() {
        return "";
    }
}
