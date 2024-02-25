package com.tencent.wxcloudrun;

import com.tencent.wxcloudrun.controller.CounterController;
import com.tencent.wxcloudrun.service.CounterService;
import com.tencent.wxcloudrun.service.impl.CounterServiceImpl;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;

public class testASM {

  public void testMethod() {
    CounterController controller = new CounterController();

  }

  public static void main(String[] args) {

    try {

      testASMprint();

    } catch (Exception ex) {
      System.out.println(ex);
    }

  }

  public static void testASMprint() throws IOException {

    ClassReader classReader = new ClassReader(CounterController.class.getName());
    ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM5) {
      @Override
      public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        //System.out.println("method: " + name + " declares " + descriptor);
        return new MethodVisitor(Opcodes.ASM5, super.visitMethod(access, name, descriptor, signature, exceptions)) {
          @Override
          public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);

            String s = owner.replace("/",".");
            if (s.startsWith("com.tencent.wxcloudrun.service")){
              //System.out.println("method call :" + owner.replace("/",".") + "." + name + " declares " + descriptor);
            }
            if (s.startsWith("com.tencent.wxcloudrun.service") || s.startsWith("com.tencent.wxcloudrun.model")){
              System.out.println("method call :" + owner.replace("/",".") + "." + name + " declares " + descriptor);
            }

          }
        };
      }
    };
    classReader.accept(classVisitor,Opcodes.ASM5);
  }

}
