package com.yz.scriptPrint;

import com.yz.coreFactroy.CoreFactroy;
import com.yz.shell.GroovyShellInter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ScriptPrintProxy {
    CoreFactroy coreFactroy;
    ScriptPrinter scriptPrinter;
    ScriptModel scriptModel;//当前使用的录制脚本对象

    public void record(Method method, Object... values) {
        System.out.println(method.getName() + "\t" + Arrays.toString(values));
    }

    /**
     * 录制脚本方法
     *
     * @param clz
     * @param method
     * @param values
     */
    public void record(Object clz, Method method, Object... values) {
        if (scriptModel != null) {
            String params = generateText(method.getName(), values);
            scriptModel.getCommandList().add(params);
            if (coreFactroy.getShellInter() instanceof GroovyShellInter) {
                String classPath = "import" + "\t" + clz.getClass().getPackageName();
                scriptModel.getCommandList().add(classPath);
            }
        }
        record(method, values);
    }

    /**
     * 拼接字符串
     *
     * @param methodName
     * @param params
     * @return
     */
    @Deprecated
    public String generateText(String methodName, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(methodName);
        sb.append("(");
        if (params != null && params.length > 0) {
            sb.append(parseArray(params));
            sb.deleteCharAt(sb.length() - 1);

        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * 拼接字符串
     *
     * @param methodName
     * @param params
     * @return
     */
    @Deprecated
    public String generateText2(String methodName, Object... params) {
        StringBuilder sb = new StringBuilder();
        if (params != null && params.length > 0) {
            if (params.length == 2 && params[1] instanceof Class) {
                sb.append(",");
                Object[] objects = (Object[]) params[0];
                String string = totoString(objects);
                String classString = getClassToString(params[1], string);
                sb.append(classString + ",");
                sb.append(getClassToString(params[1]));
                sb.append(",");
                sb.deleteCharAt(sb.length() - 1);
            }
        } else {
            sb.append(methodName);
            sb.append("(");
            sb.append(parseArray(params));
            sb.deleteCharAt(sb.length() - 1);
            sb.append(")");
        }

        return sb.toString();
    }

    private String parseArray(Object[] array) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < array.length; i++) {
            //如果是一个数组就继续解析
            if (array[i].getClass().isArray()) {
                parseArray((Object[]) array[i]);
            } else {
                if (array[i] instanceof String) {
                    if (array[i].toString().contains("\\")) {
                        array[i] = array[i].toString().replace("\\", "\\\\");
                    }
                    sb.append("\"" + array[i] + "\"" + ",");
                } else if (array[i] instanceof Double) {
                    sb.append(array[i].toString() + ",");
                } else if (array[i] instanceof Object) {
                    sb.append("\"" + array[i].toString() + "\"" + ",");
                }
            }
        }
        return sb.toString();
    }

    private String getClassToString(Object param, String title) {
        if (param.toString().endsWith(String[].class.toString())) {
            return "new String[]" + title;
        } else if (param.toString().equals(Object[].class.toString())) {
            return "new Object[]" + title;
        } else if (param.toString().equals(double[].class.toString())) {
            return "new double[]" + title;
        } else if (param.toString().equals(Double[].class.toString())) {
            return "new Double[]" + title;
        }
        return "";
    }

    private String getClassToString(Object param) {
        if (param.toString().equals(String[].class.toString())) {
            return "String[].class";
        } else if (param.toString().equals(Object[].class.toString())) {
            return "new Object[].class";
        } else if (param.toString().equals(double[].class.toString())) {
            return "new double[].class";
        } else if (param.toString().equals(Double[].class.toString())) {
            return "new Double[].class";
        }
        return "";
    }

    private String totoString(Object[] a) {
        if (a == null) {
            return "null";
        }
        int imax = a.length - 1;
        if (imax == -1) {
            return "{}";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; ; i++) {
            if (a[i] instanceof String) {
                a[i] = "\"" + a[i] + "\"";
            }
            sb.append(a[i]);
            if (i == imax) {
                return sb.append("}").toString();
            }
            sb.append(",");
        }
    }

    public void setTemplatePath(String templatePath) {
        scriptPrinter.setTemplatePath(templatePath);
    }

    public void seScriptPrintertScriptModel(ScriptModel scriptModel) {
        scriptPrinter.getModelMap().put(scriptModel.getName(), scriptModel);
    }

    public void startWriteScriptFile(ScriptModel scriptModel) throws IOException {
        this.scriptModel = scriptModel;
        seScriptPrintertScriptModel(scriptModel);
        scriptPrinter.setScriptPrinterState(ScriptPrinter.START_RECORD, scriptModel.getName());
    }

    public void stopWriteScriptFile(ScriptModel scriptModel) throws IOException {
        scriptPrinter.setScriptPrinterState(ScriptPrinter.STOP_RECORD, scriptModel.getName());
    }

    public ScriptPrintProxy() {
        this.scriptPrinter = new ScriptPrinter();
    }

    public ScriptPrinter getScriptPrinter() {
        return scriptPrinter;
    }

    public void setScriptPrinter(ScriptPrinter scriptPrinter) {
        this.scriptPrinter = scriptPrinter;
    }

    public CoreFactroy getCoreFactroy() {
        return coreFactroy;
    }

    public void setCoreFactroy(CoreFactroy coreFactroy) {
        this.coreFactroy = coreFactroy;
    }

    public ScriptModel getScriptModel() {
        return scriptModel;
    }

    public void setScriptModel(ScriptModel scriptModel) {
        this.scriptModel = scriptModel;
    }
}
