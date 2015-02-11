package com.ollieread.technomagi.common.block.processor.tile;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.common.component.Inventory;

import cpw.mods.fml.common.registry.GameRegistry;

public class TileResourceProcessorBasic extends TileResourceProcessor
{

    protected int fuelTime = 0;

    public TileResourceProcessorBasic()
    {
        this.inventory = new Inventory("inventory.technomagi.processor", 5);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (fuelTime == 0) {
                ItemStack fuelStack = getStackInSlot(4);

                if (fuelStack != null) {
                    fuelTime = GameRegistry.getFuelValue(fuelStack);

                    if (fuelTime > 0) {
                        decrStackSize(4, 1);
                    }
                }
            }
        }

        super.updateEntity();
    }

    @Override
    public void consumeEnergy()
    {
        fuelTime--;
    }

    @Override
    public boolean hasSufficientEnergy()
    {
        return fuelTime > 0;
    }

    @Override
    public int getInputSlot()
    {
        return 0;
    }

    @Override
    public int getComponentSlot()
    {
        return 1;
    }

    @Override
    public int getOutputSlot()
    {
        return 2;
    }

    @Override
    public int getByProductSlot()
    {
        return 3;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("FuelTime", fuelTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        fuelTime = compound.getInteger("FuelTime");
    }

}
