package com.ollieread.technomagi.asm.transformers;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import scala.actors.threadpool.Arrays;

public class ItemTransformer implements IClassTransformer, Opcodes
{

    public static String PORTAL_END = "fixedEndPortal";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (name.equals("net.minecraft.item.ItemEnderEye")) {
            return patchItemEnderEye(name, basicClass, false);
        }
        if (name.equals("acn")) {
            return patchItemEnderEye(name, basicClass, true);
        }

        return basicClass;
    }

    public byte[] patchItemEnderEye(String name, byte[] bytes, boolean obfuscated)
    {
        List<String> methodNames = Arrays.asList(new String[] { "func_77648_a", "onItemUse", "a" });
        String description = "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;IIIIFFF)Z";

        if (obfuscated) {
            description = "(Ladd;Lyz;Lahb;IIIIFFF)Z ";
        }

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

                int index = -1;

                while (it.hasNext()) {
                    currentNode = it.next();

                    if (currentNode.getOpcode() == GETSTATIC && ((FieldInsnNode) currentNode).name.equals("rotateRight")) {
                        while (index == -1) {
                            AbstractInsnNode prevNode = it.previous();

                            if (prevNode.getOpcode() == ISTORE) {
                                index = ((VarInsnNode) prevNode).var;
                            }
                        }
                    } else if (index > -1 && currentNode.getOpcode() == ILOAD) {
                        AbstractInsnNode nextNode = it.next();

                        if (((VarInsnNode) currentNode).var == index && nextNode != null && nextNode.getOpcode() == IFEQ) {
                            InsnList toInject = new InsnList();
                            // LabelNode label = new LabelNode();
                            // toInject.add(new VarInsnNode(ILOAD, index));
                            // toInject.add(new JumpInsnNode(IFEQ, label));
                            toInject.add(new VarInsnNode(ALOAD, 2));
                            toInject.add(new LdcInsnNode(PORTAL_END));
                            if (obfuscated) {
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Lyz;Ljava/lang/String;)V"));
                            } else {
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Lnet/minecraft/entity/player/EntityPlayer;Ljava/lang/String;)V"));
                            }

                            while (currentNode.getOpcode() != GOTO) {
                                currentNode = it.previous();
                            }

                            m.instructions.insert(currentNode, toInject);
                            flag = true;
                            break;
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

            return writer.toByteArray();
        }

        return bytes;
    }
}
