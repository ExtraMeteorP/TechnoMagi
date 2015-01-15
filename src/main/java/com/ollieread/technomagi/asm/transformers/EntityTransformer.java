package com.ollieread.technomagi.asm.transformers;

import java.util.Iterator;
import java.util.ListIterator;

import net.minecraft.launchwrapper.IClassTransformer;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

public class EntityTransformer implements IClassTransformer, Opcodes
{

    public static String OBSIDIAN = "lavaCreatedObsidian";
    public static String COBBLESTONE = "lavaCreatedCobblestone";
    public static String PORTAL_NETHER = "createdNetherPortal";
    public static String PORTAL_END = "fixedEndPortal";

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass)
    {
        if (name.equals("net.minecraft.entity.passive.EntityAnimal")) {
            return patchEntityAnimal(name, basicClass, false);
        }
        if (name.equals("wf")) {
            return patchEntityAnimal(name, basicClass, true);
        }

        return basicClass;
    }

    public InsnList getInjectedCode(boolean obfuscated)
    {
        InsnList toInject = new InsnList();
        // ALOAD 0
        // GETFIELD net/minecraft/entity/passive/EntityAnimal.field_146084_br :
        // Lnet/minecraft/entity/player/EntityPlayer;
        // ALOAD 0
        // INVOKEVIRTUAL java/lang/Object.getClass ()Ljava/lang/Class;
        // INVOKESTATIC com/ollieread/technomagi/util/EventHelper.entityBirth
        // (Ljava/lang/Class;)Ljava/lang/String;
        toInject.add(new VarInsnNode(ALOAD, 0));
        if (obfuscated) {
            // toInject.add(new FieldInsnNode(GETFIELD,
            // "net.minecraft.entity.passive.EntityAnimal", "field_146084_br"));
        } else {
            toInject.add(new FieldInsnNode(GETFIELD, "net/minecraft/entity/passive/EntityAnimal", "field_146084_br", "Lnet/minecraft/entity/player/EntityPlayer;"));
        }
        toInject.add(new VarInsnNode(ALOAD, 0));
        toInject.add(new MethodInsnNode(INVOKEVIRTUAL, "java/lang/Object", "getClass", "()Ljava/lang/Class"));
        toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/technomagi/util/EventHelper", "entityBirth", "(Ljava/lang/Class;)Ljava/lang/String"));
        if (obfuscated) {
            toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Ljava/lang/String;Lnet/minecraft/world/ahb;III)V"));
        } else {
            toInject.add(new MethodInsnNode(INVOKESTATIC, "com/ollieread/ennds/event/EnndsHooks", "onResearchableEvent", "(Ljava/lang/String;Lnet/minecraft/world/World;III)V"));
        }

        return toInject;
    }

    public byte[] patchEntityAnimal(String name, byte[] bytes, boolean obfuscated)
    {
        String methodName = "attackEntity";
        String description = "(Lnet/minecraft/entity/Entity;F)V";

        if (obfuscated) {
            methodName = "a";
            description = "(Lsa;F)V";
        }

        ClassNode classNode = new ClassNode();
        ClassReader classReader = new ClassReader(bytes);
        classReader.accept(classNode, 0);
        boolean flag = false;

        Iterator<MethodNode> methods = classNode.methods.iterator();

        while (methods.hasNext()) {
            MethodNode m = methods.next();

            if (m.name.equals(methodName) && m.desc.equals(description)) {
                AbstractInsnNode currentNode = null;

                @SuppressWarnings("unchecked")
                ListIterator<AbstractInsnNode> it = m.instructions.iterator();

                while (it.hasNext()) {
                    currentNode = it.next();

                    if (currentNode.getOpcode() == CHECKCAST) {
                        AbstractInsnNode nextNode = it.next();

                        if (nextNode.getOpcode() == INVOKESPECIAL) {
                            InsnList toInject = getInjectedCode(obfuscated);
                            m.instructions.insert(nextNode, toInject);
                            System.out.println("Inserting EntityAnimal:procreate hook");
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
