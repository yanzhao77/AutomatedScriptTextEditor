package com.yz.sample;

import java.lang.reflect.Constructor;

class Person {
    private String name; //姓名
    private int age; //年龄

    /**
     * 构造方法
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * 获取姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置新的姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获得年龄
     */
    public int getAge() {
        return age;
    }

    /**
     * 设置新的年龄
     */
    public void setAge(int age) {
        this.age = age;
    }
}

class Test7 {
    public static void main(String[] args) throws Exception {
        //获取Person类的字节码
        Class<Person> cla = (Class<Person>) Class.forName(Test7.class.getName());
        //通过字节码获得构造方法
//        Constructor<Person> constructor = cla.getConstructor(String.class, Integer.class);
        Constructor<Person> constructor = cla.getConstructor(String.class, int.class);
//因为Integer是baiint的包装du类，zhi虽然底层是int，但是JVM相对来说比较傻瓜dao
    }

}