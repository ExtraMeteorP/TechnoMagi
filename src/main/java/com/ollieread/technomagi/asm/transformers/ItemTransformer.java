package com.ollieread.technomagi.asm.transformers;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class ItemTransformer implements IClassTransformer, Opcodes
{

    public static String PORTAL_END = "fixedEndPortal";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (name.equals("net.minecraft.item.ItemEnderEye")) {
            return patchItemEnderEye(name, basicClass, false);
        }

        return basicClass;
    }

    @SuppressWarnings("resource")
    public byte[] patchItemEnderEye(String name, byte[] bytes, boolean obfuscated)
    {
        List<String> methodNames = Arrays.asList(new String[] { "func_77648_a", "onItemUse" });
        String description = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)Z";

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean flag = false;

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while (methods.hasNext()) {
            MethodNode m = methods.next();
            int loc_index = -1;

            if (methodNames.contains(m.name) && m.desc.equals(description)) {
                AbstractInsnNode currentNode = null;

                @SuppressWarnings("unchecked")
                ListIterator<AbstractInsnNode> it = m.instructions.iterator();

                while (it.hasNext()) {
                    currentNode = it.next();

                    if (currentNode.getOpcode() == ILOAD) {
                        int index1 = ((VarInsnNode) currentNode).var;
                        AbstractInsnNode nextNode = it.next();

                        if (nextNode != null && nextNode.getOpcode() == ILOAD) {
                            int index2 = ((VarInsnNode) nextNode).var;
                            nextNode = it.next();

                            if (nextNode != null && nextNode.getOpcode() == IF_ICMPGT) {
                                InsnList toInject = new InsnList();
                                LabelNode label = new LabelNode();
                                toInject.add(new VarInsnNode(ILOAD, index1));
                                toInject.add(new VarInsnNode(ILOAD, index2));
                                toInject.add(new JumpInsnNode(IF_ICMPLE, label));
                                toInject.add(new VarInsnNode(ALOAD, 2));
                                toInject.add(new LdcInsnNode(PORTAL_END));
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V"));
                                toInject.add(label);
                                m.instructions.insertBefore(currentNode, toInject);
                                flag = true;
                                break;
                            }
                        }
                    }

                }
                if (flag) {
                    break;
                }
            }
        }

        if (flag) {
            System.out.println("Inserting ItemEnderEye hooks");
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
            classNode.accept(writer);

            FileOutputStream file;
            try {
                file = new FileOutputStream("ItemEnderEye.class");
                file.write(writer.toByteArray());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            return writer.toByteArray();
        }

        return bytes;
    }
}
