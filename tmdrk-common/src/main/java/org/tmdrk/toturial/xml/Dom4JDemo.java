package org.tmdrk.toturial.xml;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.Iterator;
import java.util.List;

/**
 * TODO
 *
 * @author Jie.Zhou
 * @date 2020/7/30 18:06
 */
public class Dom4JDemo {
    public static void main(String[] args) throws Exception {
        //1.创建Reader对象
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = reader.read("<p>毛锴锴(先生) 185****0708 <br>石排湾郊野公园石排湾郊野公园<br>-<br>鱼香肉丝  x2<br>招牌羊排  x1<br>好吃好吃  x1<br></p>");
        //3.获取根节点
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        while (iterator.hasNext()){
            Element stu = (Element) iterator.next();
            List<Attribute> attributes = stu.attributes();
            System.out.println("======获取属性值======");
            for (Attribute attribute : attributes) {
                System.out.println(attribute.getValue());
            }
            System.out.println("======遍历子节点======");
            Iterator iterator1 = stu.elementIterator();
            while (iterator1.hasNext()){
                Element stuChild = (Element) iterator1.next();
                System.out.println("节点名："+stuChild.getName()+"---节点值："+stuChild.getStringValue());
            }
        }
    }
}
