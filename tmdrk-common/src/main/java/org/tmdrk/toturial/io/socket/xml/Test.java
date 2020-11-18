package org.tmdrk.toturial.io.socket.xml;

import com.alibaba.fastjson.JSON;

import javax.xml.bind.JAXBException;
import java.io.IOException;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/11/18 1:10
 * @Version 1.0
 **/
public class Test {
    public static void main(String[] args) throws JAXBException, IOException {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<Message>\n" +
                "<Head>\n" +
                "<_RejCode>000000</_RejCode>\n" +
                "<_RejMsg>客户手机号不能为空</_RejMsg>" +
                "<RouterJnlNo>56165135135</RouterJnlNo>\n" +
                "</Head>\n" +
//                "<Body>\n" +
//                "<OpenAccountDate>20200202</OpenAccountDate>\n" +
//                "<DeptName>蕲春中银富登村镇银行有限公司</DeptName>\n" +
//                "<IsStaff>Y</IsStaff>\n" +
//                "<ManagerId>E5215554</ManagerId>\n" +
//                "</Body>\n" +
                "</Message>";
        Message message = (Message)SocketClient.xmlToBean(xml,Message.class);
        System.out.println(JSON.toJSONString(message));
    }
}
