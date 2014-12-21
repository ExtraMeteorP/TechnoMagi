package com.ollieread.technomagi.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTargetedNanites extends ItemTMNBT
{

    @SideOnly(Side.CLIENT)
    public IIcon overlay;

    public ItemTargetedNanites(String name)
    {
        super(name);
    }

    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        overlay = register.registerIcon(getIconString() + "_" + "overlay");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamageForRenderPass(int dmg, int pass)
    {
        return pass == 0 ? overlay : super.getIconFromDamageForRenderPass(dmg, pass);
    }

    public static ItemStack resetNBT(ItemStack stack)
    {
        stack.stackTagCompound = new NBTTagCompound();
        stack.stackTagCompound.setString("Target", "none");
        stack.stackTagCompound.setInteger("Duration", 0);
        stack.stackTagCompound.setBoolean("Permanent", false);
        stack.stackTagCompound.setBoolean("Spreadable", false);
        stack.stackTagCompound.setBoolean("Interspecies", false);
        stack.stackTagCompound.setIntArray("Effects", new int[] {});

        return stack;
    }

    public static void setAll(ItemStack stack, Class entityClass, int duration, boolean permanent, boolean spreadable, boolean interspecies, int[] effects)
    {
        setTarget(stack, entityClass);
        setDuration(stack, duration);
        setPermanent(stack, permanent);
        setSpreadable(stack, spreadable);
        setInterspecies(stack, interspecies);
        setEffects(stack, effects);
    }

    public static void setTarget(ItemStack stack, Class entityClass)
    {
        NBTTagCompound compound = getNBT(stack);
        String entityName;

        if (entityClass == null) {
            entityName = "none";
        } else if (!entityClass.equals(EntityPlayer.class)) {
            entityName = (String) EntityList.classToStringMapping.get(entityClass);
        } else {
            entityName = "player";
        }

        compound.setString("Target", entityName);
    }

    public static String getTarget(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);
        return compound.getString("Target");
    }

    public static void setDuration(ItemStack stack, int duration)
    {
        NBTTagCompound compound = getNBT(stack);
        compound.setInteger("Duration", duration);
    }

    public static boolean getDuration(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);
        return compound.getBoolean("Duration");
    }

    public static void setPermanent(ItemStack stack, boolean bool)
    {
        NBTTagCompound compound = getNBT(stack);
        compound.setBoolean("Permanent", bool);
    }

    public static boolean getPermanent(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);
        return compound.getBoolean("Permanent");
    }

    public static void setSpreadable(ItemStack stack, boolean bool)
    {
        NBTTagCompound compound = getNBT(stack);
        compound.setBoolean("Spreadable", bool);
    }

    public static boolean getSpreadable(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);
        return compound.getBoolean("Spreadable");
    }

    public static void setInterspecies(ItemStack stack, boolean bool)
    {
        NBTTagCompound compound = getNBT(stack);
        compound.setBoolean("Interspecies", bool);
    }

    public static boolean getInterspecies(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);
        return compound.getBoolean("Interspecies");
    }

    public static void setEffects(ItemStack stack, int[] effects)
    {
        NBTTagCompound compound = getNBT(stack);
        compound.setIntArray("Effects", effects);
    }

    public static int[] getEffects(ItemStack stack)
    {
        NBTTagCompound compound = getNBT(stack);
        return compound.getIntArray("Effects");
    }

}
