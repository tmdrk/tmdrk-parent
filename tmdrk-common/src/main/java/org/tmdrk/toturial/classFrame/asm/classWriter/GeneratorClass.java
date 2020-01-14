package org.tmdrk.toturial.classFrame.asm.classWriter;

import org.objectweb.asm.*;
import org.objectweb.asm.util.ASMifier;
import org.objectweb.asm.util.CheckClassAdapter;
import org.objectweb.asm.util.TraceClassVisitor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @ClassName GeneratorClass
 * @Description TODO
 * @Author zhoujie
 * @Date 2020/1/5 18:20
 * @Version 1.0
 **/
public class GeneratorClass extends ClassLoader{
    public static void main(String[] args) throws Exception {
//        generateHelloWorld();
//        generateInterface();
//        generateTemplateClass();
        getTestByte();
//        transferClass();
//        traceAndCheckClass();
//        traceClass();
//        getAndSetMethod();
    }

    public static void generateHelloWorld() throws Exception {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT+Opcodes.ACC_INTERFACE,
                "com/asm/HelloWorld", null, "java/lang/Object", new String[]{"com/asm/Base"});
        //定义类的属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "LESS", "I", null, new Integer(-1)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "EQUAL", "I", null, new Integer(0)).visitEnd();
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "GREATER", "I", null, new Integer(1)).visitEnd();

        //生成默认的构造方法
        MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC,
                "<init>",
                "()V",
                null,
                null);

        //生成构造方法的字节码指令
        mw.visitVarInsn(Opcodes.ALOAD, 0);
        mw.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(1, 1);
        mw.visitEnd();


        //定义类的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC+Opcodes.ACC_ABSTRACT, "helloWorld",
                "(Ljava/lang/Object;)I", null, null).visitEnd();
        cw.visitEnd(); //使cw类已经完成
        //生成main方法
//        MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        mw = cw.visitMethod(Opcodes.ACC_PUBLIC + Opcodes.ACC_STATIC,
                "main",
                "([Ljava/lang/String;)V",
                null,
                null);

        //生成main方法中的字节码指令
        mw.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "color",
                "Ljava/io/PrintStream;");

        mw.visitLdcInsn("Hello world!");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V");
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(2, 2);
        mw.visitEnd(); //使cw类已经完成

        //将cw转换成字节数组写到文件里面去
        byte[] data = saveByteCode(cw, "F://tmp//com//asm//HelloWorld.class");

//        //直接将二进制流加载到内存中
//        GeneratorClass loader = new GeneratorClass();
//        Class<?> exampleClass = loader.defineClass("HelloWorld", data, 0, data.length);
//
//        //通过反射调用main方法
//        exampleClass.getMethods()[0].invoke(null, new Object[] { null });
    }

    public static void generateInterface() throws Exception {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC+Opcodes.ACC_INTERFACE,
                "com/asm/Base", null, "java/lang/Object", null);

        //定义类的int属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "COUNT", "I", null, new Integer(-1)).visitEnd();

        //定义类的方法
        cw.visitMethod(Opcodes.ACC_PUBLIC, "helloWorld",
                "(Ljava/lang/Object;)I", null, null).visitEnd();

        cw.visitEnd(); //使cw类已经完成

        //将cw转换成字节数组写到文件里面去
        byte[] data = saveByteCode(cw, "F://tmp//com//asm//Base.class");
    }

    /**
     * @Author zhoujie
     * @Description //ASM基本操作模板
     * @Date 17:34 2020/1/6
     * @Param []
     * @return void
     **/
    public static void generateTemplateClass() throws Exception {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC+Opcodes.ACC_INTERFACE,
                "com/asm/TemplateClass", null, "java/lang/Object", null);

        //定义类的byte属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testByte", "B", null, 1).visitEnd();

        //定义类的short属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testShort", "S", null, 2).visitEnd();

        //定义类的int属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testInt", "I", null, new Integer(3)).visitEnd();

        //定义类的long属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testLong", "J", null, 4L).visitEnd();

        //定义类的float属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testFloat", "F", null, new Float(5)).visitEnd();

        //定义类的double属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testDouble", "D", null, new Double(6)).visitEnd();

        //定义类的char属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testChar", "C", null, 'b').visitEnd();

        //定义类的boolean属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testBoolean", "Z", null, false).visitEnd();

        //定义类的String属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "NAME", "Ljava/lang/String;", null, new String("zhoujie")).visitEnd();

        //定义类的int数组属性
        //最后一个参数字段为常量值，这个参数必须仅用于真正的常量字段，也就是final static字段。对于其他字段，他必须为null
//        Integer[] ints = new Integer[]{3,6};
        cw.visitField(0,
                "testInts", "[I", null, null).visitEnd();

        //定义类的map属性
        cw.visitField(0,
                "testMap", "Ljava/util/Map;", null, null).visitEnd();

        //定义类的方法
        cw.visitMethod(0, "helloWorld",
                "(ILjava/lang/String;)Ljava/lang/String;", null, null).visitEnd();

        cw.visitEnd(); //使cw类已经完成

        //将cw转换成字节数组写到文件里面去
        byte[] data = saveByteCode(cw, "F://tmp//com//asm//TemplateClass.class");
    }

    /**
     * @Author zhoujie
     * @Description //测试获取字节码字节流
     * @Date 19:00 2020/1/7
     * @Param []
     * @return byte[]
     **/
    public static byte[] getTestByte() throws Exception {
        //生成一个类只需要ClassWriter组件即可
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                "com/asm/HelloClass", null, "java/lang/Object", null);

        //定义类的int属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testInt", "I", null, new Integer(3)).visitEnd();

        //定义类的String属性
        cw.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "NAME", "Ljava/lang/String;", null, new String("zhoujie")).visitEnd();

        //初始化一个无参的构造函数
        MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        constructor.visitCode();
        //这里请看截图
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        //执行父类的init初始化
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        //从当前方法返回void
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();

        //定义类的方法
        MethodVisitor mw = cw.visitMethod(Opcodes.ACC_PUBLIC, "helloWorld",
                "()V", null, null);
        mw.visitCode();
        //生成main方法中的字节码指令
        mw.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "color",
                "Ljava/io/PrintStream;");

        mw.visitLdcInsn("Hello world!");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V",false);
        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(2, 1);             //两个变量maxStack, maxLocals，设置操作数栈和局部变量表大小
        mw.visitEnd();

        cw.visitEnd(); //使cw类已经完成

        //将cw转换成字节数组写到文件里面去
        byte[] data = saveByteCode(cw, "F://tmp//com//asm//HelloClass.class");

        //        //直接将二进制流加载到内存中
//        GeneratorClass loader = new GeneratorClass();
//        Class<?> exampleClass = loader.defineClass(null, data, 0, data.length);
        MyClassLoader mcl = new MyClassLoader();
        Class<?> exampleClass = mcl.defineClass(null,data);

        //通过反射调用main方法
        Method[] declaredMethods = exampleClass.getDeclaredMethods();
        for (Method method:declaredMethods){
            System.out.println(method.getName());
        }
        exampleClass.getDeclaredMethods()[0].invoke(exampleClass.newInstance(), new Object[] { });
        return data;
    }

    /**
     * @Author zhoujie
     * @Description 保存字节码到本地目录
     * @Date 19:05 2020/1/7
     * @Param [cw, filePath]
     * @return byte[]
     **/
    private static byte[] saveByteCode(ClassWriter cw, String filePath) throws IOException {
        byte[] data = cw.toByteArray();

        File file = new File(filePath);
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();
        return data;
    }

    public static void transferClass() throws Exception {
        byte[] b1 = getTestByte();
        ClassWriter cw = new ClassWriter(0);
        ClassReader cr = new ClassReader(b1);
        cr.accept(cw,0);
        byte[] b2 = cw.toByteArray();
        System.out.println(b1==b2);
    }

    /**
     * @Author zhoujie
     * @Description trace（Textifier(默认) ,ASMifier）并校验Class文件生成
     * @Date 19:06 2020/1/7
     * @Param []
     * @return void
     **/
    public static void traceAndCheckClass() throws Exception {
        ClassWriter cw = new ClassWriter(0);
        TraceClassVisitor cv = new TraceClassVisitor(cw, new ASMifier(),new PrintWriter(System.err));
        CheckClassAdapter ca = new CheckClassAdapter(cv);

        //通过visit方法确定类的头部信息
        ca.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                "com/asm/TraceAndCheckClass", null, "java/lang/Object", null);

        //定义类的int属性
        ca.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "testInt", "I", null, new Integer(3)).visitEnd();

        //定义类的String属性
        ca.visitField(Opcodes.ACC_PUBLIC+Opcodes.ACC_FINAL+Opcodes.ACC_STATIC,
                "NAME", "Ljava/lang/String;", null, new String("zhoujie")).visitEnd();


        //初始化一个无参的构造函数
        MethodVisitor constructor = ca.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
        //这里请看截图
        constructor.visitCode();
        constructor.visitVarInsn(Opcodes.ALOAD, 0);
        //执行父类的init初始化
        constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        //从当前方法返回void
        constructor.visitInsn(Opcodes.RETURN);
        constructor.visitMaxs(1, 1);
        constructor.visitEnd();

        //定义类的方法
        MethodVisitor mw = ca.visitMethod(Opcodes.ACC_PUBLIC, "helloWorld",
                "()V", null, null);
        mw.visitCode();
        //生成main方法中的字节码指令
        mw.visitFieldInsn(Opcodes.GETSTATIC,
                "java/lang/System",
                "color",
                "Ljava/io/PrintStream;");

        mw.visitLdcInsn("Hello world!");
        mw.visitMethodInsn(Opcodes.INVOKEVIRTUAL,
                "java/io/PrintStream",
                "println",
                "(Ljava/lang/String;)V");

        mw.visitInsn(Opcodes.RETURN);
        mw.visitMaxs(1, 1);

        ca.visitEnd(); //使cw类已经完成

        //将cw转换成字节数组写到文件里面去
        byte[] data = saveByteCode(cw, "F://tmp//com//asm//TraceAndCheckClass.class");
    }

    /**
     * @Author zhoujie
     * @Description 使用字节码指令生成源码
     * public class GetAndSetTest {
     *     private int money;
     *     public int getMoney() {
     *         return this.money;
     *     }
     *     public void setMoney(int money) {
     *         this.money = money;
     *     }
     * }
     * @Date 21:22 2020/1/7
     * @Param []
     * @return void
     **/
    public static void getAndSetMethod() throws Exception {
        ClassWriter cw = new ClassWriter(0);
        //通过visit方法确定类的头部信息
        cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC,
                "com/asm/GetAndSetTest", null, "java/lang/Object", null);

        //定义类的int属性
        cw.visitField(Opcodes.ACC_PRIVATE,
                "money", "I", null, null).visitEnd();

        //定义类的get方法
        MethodVisitor gmv = cw.visitMethod(Opcodes.ACC_PUBLIC, "getMoney",
                "()I", null, null);
        gmv.visitCode();
        gmv.visitVarInsn(Opcodes.ALOAD, 0);
        //var1 GETFIELD owner name desc(pop对象的引用，pushname的值到操作栈中)，PUTFIELD owner name desc(pop对象的值和引用，
        // 将值存储到name的field)，GETSTATIC和PUTSTATIC指令用来操作静态对象，作用一样；
        gmv.visitFieldInsn(Opcodes.GETFIELD, "com/asm/GetAndSetTest", "money", "I");
        gmv.visitInsn(Opcodes.IRETURN);
        gmv.visitMaxs(1, 1);
        gmv.visitEnd();
        //定义类的set方法
        MethodVisitor smv = cw.visitMethod(Opcodes.ACC_PUBLIC, "setMoney",
                "(I)V", null, null);
        smv.visitCode();
        smv.visitVarInsn(Opcodes.ILOAD, 1);
        Label label = new Label();
        smv.visitJumpInsn(Opcodes.IFLT, label);
        smv.visitVarInsn(Opcodes.ALOAD, 0);
        smv.visitVarInsn(Opcodes.ILOAD, 1);
        smv.visitFieldInsn(Opcodes.PUTFIELD, "com/asm/GetAndSetTest", "money", "I");
        Label end = new Label();
        smv.visitJumpInsn(Opcodes.GOTO, end);
        smv.visitLabel(label);
        smv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        smv.visitTypeInsn(Opcodes.NEW, "java/lang/IllegalArgumentException");
        smv.visitInsn(Opcodes.DUP);
        smv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "()V");
        smv.visitInsn(Opcodes.ATHROW);
        smv.visitLabel(end);
        smv.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
        smv.visitInsn(Opcodes.RETURN);
        smv.visitMaxs(2, 2);
        smv.visitEnd();

        cw.visitEnd();

        //将cw转换成字节数组写到文件里面去
        byte[] data = saveByteCode(cw, "F://tmp//com//asm//GetAndSetTest.class");
    }
}
