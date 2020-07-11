package com.yz.proxy;

import com.yz.coreFactroy.CoreFactroy;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy extends Interceptor implements MethodInterceptor {
    Object obj;
    CoreFactroy coreFactory;

    public Object getCoreFactory() {
        return coreFactory;
    }

    public void setCoreFactory(Object coreFactory) {
        this.coreFactory = (CoreFactroy) coreFactory;
    }

    /**
     * 创建代理对象
     *
     * @param target    要代理对象
     * @param args      构造器参数类型
     * @param argsValue 构造器参数
     * @param <T>
     * @return 返回代理对象
     */
    public <T> T getInstance(Class<T> target, Class[] args, Object[] argsValue) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(this);
        return (T) enhancer.create(args, argsValue);
    }

    /**
     * 创建无参数代理对象
     *
     * @param target 代理对象
     * @param <T>
     * @return
     */
    public <T> T getInstance(Class<T> target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    /**
     * 创建无参数代理对象
     *
     * @param target
     * @param <T>
     * @return
     */
    public <T> T getInstance(T target) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    /**
     * jdk代理的aop方法
     *
     * @param poxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object poxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(poxy, args);
        //这里是脚本记录的地方
        coreFactory.getScriptPrintProxy().record(poxy, method, args);
        return invoke;
    }


    /**
     * cglib代理的aop方法
     *
     * @param poxy
     * @param method
     * @param args
     * @param methodProxy
     * @return
     * @throws Throwable
     */
    @Override
    public Object intercept(Object poxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object res = methodProxy.invokeSuper(poxy, args);
        //这里是脚本记录的地方
        coreFactory.getScriptPrintProxy().record(poxy, method, args);
        return res;
    }
}
