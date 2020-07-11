package com.yz.proxy;


import com.yz.coreFactroy.CoreFactroy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    /**
     * cglib代理对象
     *
     * @param classObj
     * @param corFactory
     * @return
     */
    public static Object getCglibProxy(Object classObj, Object corFactory) {
        CglibProxy cglibProxy = new CglibProxy();
        Object porxy = null;
        porxy = cglibProxy.getInstance(classObj.getClass(),
                new Class[]{corFactory.getClass()}, new Object[]{corFactory});
        cglibProxy.setCoreFactory(corFactory);
        if (porxy != null) {
            return porxy;
        } else {
            return classObj;
        }
    }

    /**
     * jdk代理
     *
     * @param obj
     * @param corFactory
     * @param interceptors
     * @return
     */
    public static Object getCglibProxy(Object obj, CoreFactroy corFactory, String... interceptors) {
        Object porxy = obj;
        for (String temp : interceptors) {
            if (!Interceptor.isContains(temp)) {
                continue;
            }

            try {
                Class c = Class.forName(temp);
                Constructor<Interceptor> constructor = c.getDeclaredConstructor(Object.class, CoreFactroy.class);
                Interceptor interceptor = constructor.newInstance(porxy, corFactory);
                porxy = Proxy.newProxyInstance(porxy.getClass().getClassLoader(),
                        porxy.getClass().getInterfaces(), interceptor);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return porxy;
    }
}
