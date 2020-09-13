package com.itheima.travel.util;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

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
            return clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("没有对应名称的对象实例");
        }
    }
}
