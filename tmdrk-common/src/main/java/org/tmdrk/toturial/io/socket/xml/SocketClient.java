package org.tmdrk.toturial.io.socket.xml;

import cn.hutool.core.util.XmlUtil;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.log4j.Logger;
import org.tmdrk.toturial.io.socket.SocketTest;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.*;
import java.net.Socket;

/**
 * @ClassName SocketClient
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/11/17 23:37
 * @Version 1.0
 **/
public class SocketClient {
    private static org.apache.log4j.Logger logger = Logger.getLogger(SocketTest.class);
    public static void main(String[] args) throws Exception {
        String xml = "<xml>\r\n" +
                "<name>张山</name>\r\n" +
                "<amt>100000</amt>\r\n" +
                "<time>20171011091230</time>\r\n" +
                "<type>支出</type>\r\n" +
                "<opt>信用卡还款</opt>\r\n" +
                "<phone>18940916007</phone>\r\n" +
                "</xml>";
        //客户端
        //1、创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("localhost",10088);
        socket.setSoTimeout(10000); //十秒超时
        //2、获取输出流，向服务器端发送信息
        System.out.println("休眠10秒");
        Thread.sleep(5000);
        OutputStream os = socket.getOutputStream();//字节输出流
        os.write(xml.getBytes("UTF-8"));
        os.flush();
//        PrintWriter pw = new PrintWriter(os);//将输出流包装成打印流
//        pw.write("用户名：admin；密码：123");
//        pw.flush();
        socket.shutdownOutput();
        logger.info("客户端输出流关闭");
        //3、获取输入流，并读取服务器端的响应信息
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        logger.info("我是客户端，服务器说：");
        StringBuilder sb = new StringBuilder();
        while((info=br.readLine())!=null){
            sb.append(info);
        }
        System.out.println(sb);
        Document document = XmlUtil.parseXml(sb.toString());
        Element head = document.getElementById("Head");

        // 获取根节点名称
        String rootName = document.getDocumentElement().getTagName();
        System.out.println("根节点: " + rootName);

        System.out.println("递归解析--------------begin------------------");
        // 递归解析Element
        Element element = document.getDocumentElement();
        System.out.println("递归解析--------------end------------------");
        Message message = (Message)xmlToBean(sb.toString(), Message.class);
        System.out.println(JSON.toJSONString(message));
        //4、关闭资源
        br.close();
        is.close();
        os.close();
        socket.close();
        logger.info("客户端关闭");
    }

    public static Object xmlToBean(String xmlPath,Class<?> load) throws JAXBException, IOException{
        JAXBContext context = JAXBContext.newInstance(load);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Object object = unmarshaller.unmarshal(new StringReader(xmlPath));
        return object;
    }
}
@XmlRootElement(name = "Message")
class Message{

    Head head;

    Body body;

    @XmlElement(name = "Body")
    public Body getBody() {
        return body;
    }
    @XmlElement(name = "Head")
    public Head getHead() {
        return head;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public void setHead(Head head) {
        this.head = head;
    }
}
class Head{
    String rejCode;
    String rejMsg;
    String routerJnlNo;

    @XmlElement(name = "RouterJnlNo")
    public String getRouterJnlNo() {
        return routerJnlNo;
    }

    public void setRouterJnlNo(String routerJnlNo) {
        this.routerJnlNo = routerJnlNo;
    }

    @XmlElement(name = "_RejCode")
    public String getRejCode() {
        return rejCode;
    }

    public void setRejCode(String rejCode) {
        this.rejCode = rejCode;
    }

    @XmlElement(name = "_RejMsg")
    public String getRejMsg() {
        return rejMsg;
    }

    public void setRejMsg(String rejMsg) {
        this.rejMsg = rejMsg;
    }
}
class Body{
    String openAccountDate;
    String deptName;
    String isStaff;
    String managerId;

    @XmlElement(name = "OpenAccountDate")
    public String getOpenAccountDate() {
        return openAccountDate;
    }

    public void setOpenAccountDate(String OpenAccountDate) {
        this.openAccountDate = OpenAccountDate;
    }

    @XmlElement(name = "DeptName")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @XmlElement(name = "IsStaff")
    public String getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(String isStaff) {
        this.isStaff = isStaff;
    }

    @XmlElement(name = "ManagerId")
    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }


}