package com.ollieread.technomagi.common.block.energy.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.client.gui.window.WindowBasicGenerator;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.energy.container.ContainerBasicGenerator;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileGeneratorBasic extends TileGenerator
{

    public TileGeneratorBasic()
    {
        super(2048, 10, 10);

        inventory = new Inventory("basic_generator", 2);
        perTick = 2;
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (progress.getProgress() <= 0) {
                ItemStack fuelStack = getStackInSlot(0);

                if (fuelStack != null) {
                    int value = ItemStackHelper.getFuelValue(fuelStack);
                    progress.setMaxProgress(value);
                    progress.setProgress(value);

                    if (progress.getMaxProgress() > 0) {
                        decrStackSize(0, 1);
                    }
                }
            }
        }

        super.updateEntity();
    }

    @Override
    public boolean canProcess()
    {
        return (this.getMaxEnergyStored(null) - (this.getEnergyStored(null) - perTick)) > perTick && progress.getProgress() > 0;
    }

    @Override
    public void process()
    {
        progress.decrementProgress();
        this.modifyEnergyStored(+perTick);
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return slot == 0 ? ItemStackHelper.fuelSlot.isItemStackApplicable(stack) : ItemStackHelper.energyContainerSlot.isItemStackApplicable(stack);
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return new ContainerBasicGenerator(player.inventory, this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public Window getWindow(EntityPlayer player)
    {
        return new WindowBasicGenerator((ContainerBasicGenerator) getContainer(player));
    }

}
