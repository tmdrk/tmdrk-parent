package org.tmdrk.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

/**
 * @ClassName ProtobufTest
 * @Description TODO
 * @Author zhoujie
 * @Date 2019/8/13 0:27
 * @Version 1.0
 **/
public class ProtobufTest {
    public static void main(String[] args) throws InvalidProtocolBufferException {
        DataInfo.Student student = DataInfo.Student.newBuilder().setName("周静水压")
                .setAge(45).setAddress("中国上海").build();
        byte[] bytes = student.toByteArray();
        DataInfo.Student student1 = DataInfo.Student.parseFrom(bytes);
        System.out.println(student1);
        System.out.println(student1.getName());
        System.out.println(student1.getAge());
        System.out.println(student1.getAddress());
    }
}
