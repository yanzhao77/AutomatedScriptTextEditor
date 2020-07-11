package com.yz.proxy;


import com.yz.coreFactroy.CoreFactroy;

import java.lang.reflect.Method;

public class ParseInvocationHandler extends Interceptor {
    Object obj;
    CoreFactroy coreFactory;

    public ParseInvocationHandler(Object obj, CoreFactroy coreFactory) {
        this.obj = obj;
        this.coreFactory = coreFactory;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        Object res = method.invoke(obj, args);
        //这里是脚本记录的地方
        coreFactory.getScriptPrintProxy().record(o, method, args);
        return res;
    }
}
