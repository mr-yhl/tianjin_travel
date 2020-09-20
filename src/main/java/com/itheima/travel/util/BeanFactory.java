package com.itheima.travel.util;


import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Date;

public class BeanFactory {
    // 根据id获取对应的对象实例
    public static Object getBean(String id) {
        try {
            // 1.加载beans.xml配置文件，获取io流
            InputStream in = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
            // 2.通过dom4j解析xml，获取document对象
            Document document = new SAXReader().read(in);
            // 3.编写xpath表达式
            String xpath = "//bean[@id='" + id + "']";
            // 4.获取节点对象
            Element element = (Element) document.selectSingleNode(xpath);
            // 5.获取class的属性值（全限定类名）
            String className = element.attributeValue("class");
            // 6.通过反射将全限定类名加载内JVM虚拟机，获得字节码对象
            Class clazz = Class.forName(className);
            // 7.创建对象实例并返回

            // 创建代理对象,实现日志功能-------------------------------开始
            // 创建目标对象
            Object instance = clazz.newInstance();

            // 代理逻辑
            InvocationHandler invocationHandler = new InvocationHandler(){

                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    Object obj = null;
                    // 记录日志
                    StringBuilder sb = new StringBuilder();
                    sb.append("执行时间"+new Date().toLocaleString());
                    sb.append(",类名"+instance.getClass().getName());
                    sb.append(",方法名"+method.getName());
                    sb.append(",入参"+ Arrays.toString(args));
                    try {
                        //调用目标对象方法
                        obj = method.invoke(instance, args);
                    } catch (Exception e) {
                        sb.append(",异常信息:" + e.getMessage());
                        throw new RuntimeException(e);
                    }
                    String log = sb.toString();
                    //System.out.println(log);
                    wirteLog(log);
                    return obj;
                }
            };
            // 使用jdk产生代理对象
            Object object = Proxy.newProxyInstance(
                    instance.getClass().getClassLoader(),
                    instance.getClass().getInterfaces(),
                    invocationHandler
            );


            // 创建代理对象,实现日志功能-------------------------------结束

            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("没有对应名称的对象实例");
        }
    }

    private static void wirteLog(String log) {
        try {
            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(new File("/usr/local/weblog/travel.txt"), true));

            writer.write(log);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
