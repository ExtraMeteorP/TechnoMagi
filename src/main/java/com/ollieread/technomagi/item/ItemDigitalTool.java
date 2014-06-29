package com.ollieread.technomagi.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemDigitalTool extends ItemTM
{

    protected int focusType = 0;
    protected int[] focusLocation = new int[3];
    protected int focusId = 0;

    public ItemDigitalTool(String name)
    {
        super(name);

        setMaxDamage(100);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();

        stack.stackTagCompound.setInteger("FocusType", focusType);
        stack.stackTagCompound.setIntArray("FocusLocatioN", focusLocation);
        stack.stackTagCompound.setInteger("FocusId", focusId);
    }

    public void setFocusType(int type)
    {
        focusType = type;
    }

    public void setFocusId(int id)
    {
        focusId = id;
    }

    public void setFocusLocation(int x, int y, int z)
    {
        focusLocation = new int[] { x, y, z };
    }

    public int getFocusType()
    {
        return focusType;
    }

    public int getFocusId()
    {
        return focusId;
    }

    public int[] getFocusLocation()
    {
        return focusLocation;
    }

}
