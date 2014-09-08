package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 
 * @author ollie
 * 
 */
public class ItemDigitalTool extends ItemTM
{

    protected int[] focusLocation = new int[] { -1, -1, -1 };

    public ItemDigitalTool(String name)
    {
        super(name);

        setMaxDamage(100);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();

        stack.stackTagCompound.setIntArray("FocusLocation", focusLocation);
    }

    public static void resetFocusLocation(ItemStack stack)
    {
        stack.stackTagCompound.setIntArray("FocusLocation", new int[] { -1, -1, -1 });
    }

    public static void setFocusLocation(ItemStack stack, int x, int y, int z)
    {
        stack.stackTagCompound.setIntArray("FocusLocation", new int[] { x, y, z });
    }

    public static int[] getFocusLocation(ItemStack stack)
    {
        return stack.stackTagCompound.getIntArray("FocusLocation");
    }

    public static boolean hasFocusLocation(ItemStack stack)
    {
        int[] focusLocation = getFocusLocation(stack);

        return focusLocation[0] != -1 && focusLocation[1] != -1 && focusLocation[2] != -1;
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack stack = new ItemStack(item, 1);
        stack.stackTagCompound = new NBTTagCompound();
        resetFocusLocation(stack);

        list.add(stack);
    }

}
