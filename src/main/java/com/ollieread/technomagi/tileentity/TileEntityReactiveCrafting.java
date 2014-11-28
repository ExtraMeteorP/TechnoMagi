package com.ollieread.technomagi.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.proxy.BasicInventory;
import com.ollieread.technomagi.common.proxy.Disguisable;

public class TileEntityReactiveCrafting extends TileEntityTM implements IDisguisableTile
{

    protected BasicInventory result = null;
    protected Disguisable disguise = null;
    protected int ticks = 0;
    protected int crafting = 0;

    public TileEntityReactiveCrafting()
    {
        result = new BasicInventory(1);
        disguise = new Disguisable();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        ticks = compound.getInteger("Ticks");
        crafting = compound.getInteger("Crafting");

        result.readFromNBT(compound);
        disguise.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Ticks", ticks);
        compound.setInteger("Crafting", crafting);

        result.writeToNBT(compound);
        disguise.writeToNBT(compound);
    }

    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (crafting > 0) {
                ticks++;

                if (ticks == crafting) {
                    /*
                     * ItemStack result = getResult();
                     * 
                     * if (result != null && result.getItem() != null) { Block
                     * block = Block.getBlockFromItem(result.getItem());
                     * 
                     * if (block != null) { worldObj.setBlock(xCoord, yCoord,
                     * zCoord, block, result.getItemDamage(), 2);
                     * 
                     * return; } }
                     */

                    worldObj.func_147480_a(xCoord, yCoord, zCoord, true);
                }
            }
        }
    }

    public void setResult(ItemStack stack, int crafting, ItemStack disguise)
    {
        this.crafting = crafting;
        this.result.setInventorySlotContents(0, stack);
        this.setDisguise(disguise);

        this.sync();
    }

    public ItemStack getResult()
    {
        return result.getStackInSlot(0);
    }

    public boolean isCrafting()
    {
        return ticks < crafting;
    }

    public boolean hasCrafted()
    {
        return ticks == crafting;
    }

    /* Everything below is just a proxy for the interfaces */

    /* DISGUISABLE */

    @Override
    public boolean isDisguised()
    {
        return disguise.isDisguised();
    }

    @Override
    public boolean setDisguise(ItemStack stack)
    {
        return disguise.setDisguise(stack);
    }

    @Override
    public ItemStack getDisguise()
    {
        return disguise.getDisguise();
    }

}
