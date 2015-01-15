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

public class BlockTransformer implements IClassTransformer, Opcodes
{

    public static String OBSIDIAN = "lavaCreatedObsidian";
    public static String COBBLESTONE = "lavaCreatedCobblestone";
    public static String PORTAL_NETHER = "createdNetherPortal";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (name.equals("net.minecraft.block.BlockLiquid")) {
            return patchBlockLiquid(name, basicClass, false);
        }
        if (name.equals("alw")) {
            return patchBlockLiquid(name, basicClass, true);
        }
        if (name.equals("net.minecraft.block.BlockPortal")) {
            return patchBlockPortal(name, basicClass, false);
        }
        if (name.equals("amp")) {
            return patchBlockPortal(name, basicClass, true);
        }

        return basicClass;
    }

    public InsnList getInjectedCode(String event, boolean obfuscated)
    {
        InsnList toInject = new InsnList();
        toInject.add(new LdcInsnNode(event));
        toInject.add(new VarInsnNode(ALOAD, 1));
        toInject.add(new VarInsnNode(ILOAD, 2));
        toInject.add(new VarInsnNode(ILOAD, 3));
        toInject.add(new VarInsnNode(ILOAD, 4));
        if (obfuscated) {
            toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Ljava/lang/String;Lahb;III)V"));
        } else {
            toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Ljava/lang/String;Lnet/minecraft/world/World;III)V"));
        }

        return toInject;
    }

    public byte[] patchBlockLiquid(String name, byte[] bytes, boolean obfuscated)
    {
        String methodName = "func_149805_n";
        String description = "(Lnet/minecraft/world/World;III)V";

        if (obfuscated) {
            methodName = "n";
            description = "(Lahb;III)V";
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

                @SuppressWarnings("unchecked")
                ListIterator<AbstractInsnNode> it = m.instructions.iterator();

                while (it.hasNext()) {
                    currentNode = it.next();

                    if (currentNode.getOpcode() == GETSTATIC && ((FieldInsnNode) currentNode).name.equals("obsidian")) {
                        AbstractInsnNode nextNode = it.next();
                        InsnList toInject = getInjectedCode(OBSIDIAN, obfuscated);
                        m.instructions.insert(nextNode, toInject);
                        System.out.println("Inserting BlockLiquid:obsidian hooks");
                    } else if (currentNode.getOpcode() == GETSTATIC && ((FieldInsnNode) currentNode).name.equals("cobblestone")) {
                        AbstractInsnNode nextNode = it.next();
                        InsnList toInject = getInjectedCode(COBBLESTONE, obfuscated);
                        m.instructions.insert(nextNode, toInject);
                        System.out.println("Inserting BlockLiquid:cobblestone hooks");
                        flag = true;
                        break;
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

    public byte[] patchBlockPortal(String name, byte[] bytes, boolean obfuscated)
    {
        List<String> methodNames = Arrays.asList(new String[] { "func_150000_e", "tryToCreatePortal", "e" });
        String description = "(Lnet/minecraft/world/World;III)Z";

        if (obfuscated) {
            description = "(Lahb;III)Z";
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean flag = false;

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while (methods.hasNext()) {
            MethodNode m = methods.next();

            if (methodNames.contains(m.name) && m.desc.equals(description)) {
                AbstractInsnNode currentNode = null;
                AbstractInsnNode targetNode = null;

                @SuppressWarnings("unchecked")
                ListIterator<AbstractInsnNode> it = m.instructions.iterator();

                while (it.hasNext()) {
                    currentNode = it.next();

                    if (currentNode.getOpcode() == INVOKEVIRTUAL) {
                        String method = "func_150859_c";

                        if (obfuscated) {
                            method = "e";
                        }

                        if (((MethodInsnNode) currentNode).name.equals(method)) {
                            InsnList toInject = getInjectedCode(PORTAL_NETHER, obfuscated);
                            m.instructions.insertBefore(currentNode, toInject);
                            System.out.println("Inserting BlockPortal hooks");
                        }
                    }
                }

                break;
            }
        }

        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        classNode.accept(writer);

        return writer.toByteArray();
    }
}
