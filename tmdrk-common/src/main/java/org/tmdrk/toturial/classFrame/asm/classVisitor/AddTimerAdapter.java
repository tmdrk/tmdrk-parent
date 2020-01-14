package org.tmdrk.toturial.classFrame.asm.classVisitor;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.AnalyzerAdapter;

import static org.objectweb.asm.Opcodes.*;

/**
 * @ClassName AddTimerAdapter
 * @Description 在每个类中添加静态计时器字段，每个方法执行时间添加到这个计时器字段中。
 * @Author zhoujie
 * @Date 2020/1/7 23:17
 * @Version 1.0
 **/
public class AddTimerAdapter extends ClassVisitor {
    private String owner;
    private boolean isInterface;

    public AddTimerAdapter(ClassVisitor cv) {
        super(ASM4, cv);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        cv.visit(version, access, name, signature, superName, interfaces);
        owner = name;
        isInterface = (access & ACC_INTERFACE) != 0;
    }
    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        if (!isInterface && mv != null && !name.equals("<init>")) {
//            mv = new AddTimerMethodAdapter(mv);   //普通实现
            mv = new AddTimerMethodAdapter(0,"",access,name,desc,mv); //AnalyzerAdapter实现
        }
        return mv;
    }
    @Override
    public void visitEnd() {
        if (!isInterface) {
            FieldVisitor fv = cv.visitField(ACC_PUBLIC + ACC_STATIC, "timer", "J", null, null);
            if (fv != null) {
                fv.visitEnd();
            }
        }
        cv.visitEnd();
    }

    //普通实现，需要考虑调节操作数栈和局部变量表大小
//    class AddTimerMethodAdapter extends MethodVisitor {
//        public AddTimerMethodAdapter(MethodVisitor mv) {
//            super(ASM4, mv);
//        }
//        @Override public void visitCode() {
//            mv.visitCode();
//            mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
//            mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
//            mv.visitInsn(LSUB);
//            mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
//        }
//        @Override public void visitInsn(int opcode) {
//            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
//                mv.visitFieldInsn(GETSTATIC, owner, "timer", "J");
//                mv.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
//                mv.visitInsn(LADD);
//                mv.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
//            }
//            mv.visitInsn(opcode);
//        }
//        //调节操作数栈大小
//        @Override public void visitMaxs(int maxStack, int maxLocals) {
//            mv.visitMaxs(maxStack + 4, maxLocals);//COMPUTE_MAXS
//        }
//    }

    //AnalyzerAdapter实现，不需要考虑调节操作数栈和局部变量表大小
    class AddTimerMethodAdapter extends AnalyzerAdapter {

        protected AddTimerMethodAdapter(int i, String owner, int access, String name, String desc, MethodVisitor methodVisitor) {
            super(ASM4, owner, access, name, desc, methodVisitor);
        }

        @Override public void visitCode() {
            super.visitCode();
            super.visitFieldInsn(GETSTATIC, owner, "timer", "J");
            super.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
            super.visitInsn(LSUB);
            super.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
        }
        @Override public void visitInsn(int opcode) {
            if ((opcode >= IRETURN && opcode <= RETURN) || opcode == ATHROW) {
                super.visitFieldInsn(GETSTATIC, owner, "timer", "J");
                super.visitMethodInsn(INVOKESTATIC, "java/lang/System", "currentTimeMillis", "()J");
                super.visitInsn(LADD);
                super.visitFieldInsn(PUTSTATIC, owner, "timer", "J");
            }
            super.visitInsn(opcode);
        }
    }
}
