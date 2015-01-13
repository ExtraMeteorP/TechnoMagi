package com.ollieread.technomagi.asm;

import java.util.Iterator;

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

public class TechnoMagiCoreClassTransformer implements IClassTransformer, Opcodes
{

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (name.equals("net.minecraft.block.BlockLiquid")) {
            return patchBlockLiquid(name, basicClass, false);
        }

        if (name.equals("net.minecraft.block.alw")) {
            return patchBlockLiquid(name, basicClass, true);
        }

        return basicClass;
    }

    public byte[] patchBlockLiquid(String name, byte[] bytes, boolean obfuscated)
    {
        String methodName = "func_149805_n";
        String description = "(Lnet/minecraft/world/World;III)V";

        if (obfuscated) {
            methodName = "n";
            description = "(Lnet/minecraft/world/ahb;III)V";
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean flag = false;

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while (methods.hasNext()) {
            MethodNode m = methods.next();
            int loc_index = -1;

            if (m.name.equals(methodName) && m.desc.equals(description)) {
                AbstractInsnNode currentNode = null;
                AbstractInsnNode targetNode = null;

                @SuppressWarnings("unchecked")
                Iterator<AbstractInsnNode> it = m.instructions.iterator();

                int index = -1;

                while (it.hasNext()) {
                    index++;
                    currentNode = it.next();

                    if (currentNode.getType() == AbstractInsnNode.FIELD_INSN) {
                        if (((FieldInsnNode) currentNode).desc.equals("obsidian")) {
                            loc_index = index;
                            targetNode = m.instructions.get(loc_index + 6);
                            InsnList toInject = new InsnList();
                            toInject.add(new LdcInsnNode("lavaCreatedObsidian"));
                            toInject.add(new VarInsnNode(ALOAD, 1));
                            toInject.add(new VarInsnNode(ILOAD, 2));
                            toInject.add(new VarInsnNode(ILOAD, 3));
                            toInject.add(new VarInsnNode(ILOAD, 4));
                            if (obfuscated) {
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/technomagi/util/EventHelper", "findAndFireForPlayers", "(Ljava/lang/String;Lnet/minecraft/world/ahb;III)V"));
                            } else {
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/technomagi/util/EventHelper", "findAndFireForPlayers", "(Ljava/lang/String;Lnet/minecraft/world/World;III)V"));
                            }

                            m.instructions.insertBefore(targetNode, toInject);
                        } else if (((FieldInsnNode) currentNode).desc.equals("cobblestone")) {
                            loc_index = index;
                            targetNode = m.instructions.get(loc_index + 6);
                            InsnList toInject = new InsnList();
                            toInject.add(new LdcInsnNode("lavaCreatedCobblestone"));
                            toInject.add(new VarInsnNode(ALOAD, 1));
                            toInject.add(new VarInsnNode(ILOAD, 2));
                            toInject.add(new VarInsnNode(ILOAD, 3));
                            toInject.add(new VarInsnNode(ILOAD, 4));
                            if (obfuscated) {
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/technomagi/util/EventHelper", "findAndFireForPlayers", "(Ljava/lang/String;Lnet/minecraft/world/ahb;III)V"));
                            } else {
                                toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/technomagi/util/EventHelper", "findAndFireForPlayers", "(Ljava/lang/String;Lnet/minecraft/world/World;III)V"));
                            }

                            m.instructions.insertBefore(targetNode, toInject);
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
            ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            classNode.accept(writer);

            return writer.toByteArray();
        }

        return bytes;
    }
}
