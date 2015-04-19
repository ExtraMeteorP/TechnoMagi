package com.ollieread.technomagi.common.block.structure.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.tile.ITileDisguisable;
import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.component.Disguise;

public class TileShifted extends TileBase implements ITileDisguisable
{

    protected Disguise disguise = new Disguise();

    @Override
    public boolean isDisguised()
    {
        return disguise.isDisguised();
    }

    @Override
    public ItemStack getDisguise()
    {
        return disguise.getDisguise();
    }

    @Override
    public boolean setDisguiseBlock(ItemStack stack)
    {
        if (disguise.setDisguiseBlock(stack)) {
            worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, stack.getItemDamage(), 2);
            return true;
        }

        return false;
    }

    @Override
    public void removeDisguise()
    {
        disguise.removeDisguise();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagCompound disguiseCompound = new NBTTagCompound();
        disguise.writeToNBT(disguiseCompound);
        compound.setTag("Disguise", disguiseCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagCompound disguiseCompound = compound.getCompoundTag("Disguise");
        disguise.readFromNBT(disguiseCompound);
    }

    @Override
    public boolean canUpdate()
    {
        return false;
    }

}
