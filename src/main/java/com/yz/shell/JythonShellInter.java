package com.yz.shell;

import com.yz.consoleInterFace.ShellInter;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Properties;

public class JythonShellInter implements ShellInter {
    private PrintStream pstream;//信息输出流
    public String name;
    public PythonInterpreter interpreter;//解析器

    public JythonShellInter() {
        this("");
    }

    public JythonShellInter(String name) {
        this.name = name;
        init();
    }

    public JythonShellInter(PrintStream pstream) {
        this.pstream = pstream;
        init();
    }

    public JythonShellInter(PrintStream pstream, String name) {
        this.pstream = pstream;
        this.name = name;
        init();
    }

    public void init() {
        Properties properties = new Properties();
        properties.put("Python.console.encoding", "utf8");
//        properties.put("python.home", System.getProperty("user.dir") + "/lib/jython-standalone-2.7.1.jar");
//        properties.put("python.security.respectJavaAccessibility", "false");
        properties.put("python.import.site", "false");
        Properties properties1 = System.getProperties();
        PythonInterpreter.initialize(properties1, properties, new String[0]);
        PySystemState sys = Py.getSystemState();
//        sys.path.add(System.getProperty("user.dir") + "/lib/jython-standalone-2.7.1.jar");
        sys.setdefaultencoding("utf8");
        this.interpreter = new PythonInterpreter(null, sys);
        if (pstream != null) {
            setPstream(pstream);
        }
    }

    /**
     * 执行命令
     *
     * @param cmd
     * @return
     */
    public boolean execute(String cmd) {
        if (cmd.contains("\\")) {
            cmd = cmd.replace("\\", "\\\\");
        }
        boolean flag = false;
        try {
            PyString str = Py.newStringUTF8(cmd);
            interpreter.exec(str);
            flag = true;
        } catch (PyException e) {
            pstream.println(e.type + "\t" + e.value);
        }

        return flag;
    }

    @Override
    public boolean execFile(File file) {
        try {
            interpreter.execfile(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public Object getData(String dataName) {
        PyObject pyObject = null;
        try {
            pyObject = interpreter.get(dataName.replace("\\", "\\\\"));
        } catch (PyException e) {
            pstream.println(e.type + "\t" + e.value);
        }
        return pyObject.__tojava__(Object.class);
    }

    @Override
    public void setData(String key, Object value) {
        try {
            interpreter.set(key, value);
        } catch (PyException e) {
            pstream.println(e.type + "\t" + e.value);
        }
    }

    /**
     * 返回执行的方法块对象
     *
     * @param interpreter
     * @param funcName
     * @return
     */
    public PyFunction loadPythonFunc(PythonInterpreter interpreter, String funcName) {
        PyFunction function = interpreter.get(funcName, PyFunction.class);
        return function;
    }

    /**
     * 执行无参数方法
     *
     * @param pyFunction
     * @return
     */
    public PyObject execFunc(PyFunction pyFunction) {
        PyObject pyObject = pyFunction.__call__();
        return pyObject;

    }

    /**
     * 执行有参数方法
     *
     * @param pyFunction
     * @param params
     * @return
     */
    public Object execFuncToStr(PyFunction pyFunction, String... params) {
        PyString[] values = new PyString[params.length];
        for (int i = 0; i < params.length; i++) {
            if (params[i].contains("\\")) {
                params[i] = params[i].replace("\\", "\\\\");
            }
            values[i] = Py.newString(params[i]);
        }
        Object obj = null;
        try {
            PyObject pyObject1 = pyFunction.__call__(values);
            obj = pyObject1.__tojava__(Object.class);
        } catch (PyException e) {
            pstream.println(e.type + "\t" + e.value);
        }
        return obj;
    }

    /**
     * 执行无参数方法，返回obj
     *
     * @param pyFunction
     * @return
     */
    public Object execFuncToObj(PyFunction pyFunction) {
        Object object = null;
        try {
            PyObject pyObject = execFunc(pyFunction);
            object = pyObject.__tojava__(Object.class);
        } catch (PyException e) {
            pstream.println(e.type + "\t" + e.value);
        }
        return object;
    }

    @Override
    public ShellInter getShellInter() {
        return this;
    }

    public PrintStream getPstream() {
        return pstream;
    }

    public void setPstream(PrintStream pstream) {
        this.pstream = pstream;
        interpreter.setErr(pstream);
        interpreter.setOut(pstream);
        interpreter.set("a", 111);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PythonInterpreter getInterpreter() {
        return interpreter;
    }

    public void setInterpreter(PythonInterpreter interpreter) {
        this.interpreter = interpreter;
    }
}

