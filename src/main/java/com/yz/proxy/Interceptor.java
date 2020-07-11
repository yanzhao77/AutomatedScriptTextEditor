package com.yz.proxy;

import java.lang.reflect.InvocationHandler;
import java.util.ArrayList;
import java.util.List;

public abstract class Interceptor implements InvocationHandler {
    public static final String PARSE_INVOCATIONHANDLER = Interceptor.class.getPackageName() + ".ParseInvocationHandler";
    public static final String PARSE_CGLIB = Interceptor.class.getPackageName() + ".CglibProxy";
    private static List<String> interceptorNames = new ArrayList<>() {
        {
            add(PARSE_INVOCATIONHANDLER);
            add(PARSE_CGLIB);
        }
    };

    public static boolean isContains(String interceptorName) {
        return interceptorNames.contains(interceptorName);
    }
}
