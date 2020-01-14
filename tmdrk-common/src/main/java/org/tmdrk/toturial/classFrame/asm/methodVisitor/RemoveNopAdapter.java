package org.tmdrk.toturial.classFrame.asm.methodVisitor;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @ClassName RemoveNopAdapter
 * @Description 3.2.3 转换方法
 *   删除方法中的NOP指令（因为他们不做任何事情，所以删除他们没有任何问题）
 *   这个方法适配器可以在一个类适配器内部使用
 * @Author zhoujie
 * @Date 2020/1/7 16:56
 * @Version 1.0
 **/
public class RemoveNopAdapter extends MethodVisitor {

    public RemoveNopAdapter(int i, MethodVisitor methodVisitor) {
        //Opcodes.ASM5
        super(i, methodVisitor);
    }

    @Override
    public void visitInsn(int opcode) {
        if(opcode != Opcodes.NOP){
            mv.visitInsn(opcode);
        }
    }
}
