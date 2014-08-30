package com.ollieread.technomagi.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * 
 * @author ollie
 * 
 */
public class ItemDigitalTool extends ItemTM
{

    protected int[] focusLocation = new int[3];

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

    public void resetFocusLocation()
    {
        focusLocation = new int[3];
    }

    public void setFocusLocation(int x, int y, int z)
    {
        focusLocation = new int[] { x, y, z };
    }

    public int[] getFocusLocation()
    {
        return focusLocation;
    }

}
