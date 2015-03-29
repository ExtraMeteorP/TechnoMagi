package com.ollieread.technomagi.common.block.machine.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.client.gui.window.WindowProcessorBasic;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.machine.container.ContainerProcessorBasic;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileResourceProcessorBasic extends TileResourceProcessor
{

    protected int maxFuelTime = 0;
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
                    int f = ItemStackHelper.getFuelValue(fuelStack);

                    if (f > 0) {
                        fuelTime = maxFuelTime = f;
                        decrStackSize(4, 1);
                        // this.sync();
                    }
                }
            }
        }

        super.updateEntity();
    }

    public int getMaxFuelTime()
    {
        return maxFuelTime;
    }

    public int getFuelTime()
    {
        return fuelTime;
    }

    public int getFuelTimeScaled(int scale)
    {
        return maxFuelTime > 0 ? fuelTime * scale / maxFuelTime : 0;
    }

    public void setFuelTime(int fuelTime)
    {
        this.fuelTime = fuelTime;
    }

    public void setMaxFuelTime(int maxFuelTime)
    {
        this.maxFuelTime = maxFuelTime;
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
        compound.setInteger("MaxFuelTime", maxFuelTime);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        fuelTime = compound.getInteger("FuelTime");
        maxFuelTime = compound.getInteger("MaxFuelTime");
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return new ContainerProcessorBasic(player.inventory, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Window getWindow(EntityPlayer player)
    {
        return new WindowProcessorBasic((ContainerProcessorBasic) getContainer(player));
    }

}
