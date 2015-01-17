package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.item.crafting.RecipeManager;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineInventory;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;

public class TileEntityMachineFurnace extends TileEntityMachineInventory
{

    public TileEntityMachineFurnace()
    {
        super(Config.furnacePowerMax, Config.furnacePowerReceive, 0, new ComponentInventory(2));

        setMaxProgress(Config.furnaceProgressMax);
        setUsage(Config.furnacePowerUse);
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    @Override
    public boolean isProcessing()
    {
        return getProgress() > 0;
    }

    public boolean canProcess()
    {
        EntityPlayer player = getOwner(worldObj);

        if (player != null) {
            ItemStack input = getStackInSlot(0);

            if (input != null) {
                ItemStack recipe = RecipeManager.furnace.findOutput(input);

                if (recipe != null) {
                    ItemStack result = getStackInSlot(1);

                    if (result == null) {
                        return getEnergyStored(null) > getUsage();
                    }

                    if (result.isItemEqual(recipe) && (result.stackSize + recipe.stackSize) <= result.getMaxStackSize()) {
                        return getEnergyStored(null) > getUsage();
                    }
                }
            }
        }

        return false;
    }

    public void process()
    {
        if (energy.modifyEnergyStored(usage)) {
            setProgress(getProgress() + 1);

            if (getProgress() >= getMaxProgress()) {
                EntityPlayer player = getOwner(worldObj);

                if (player != null) {
                    ItemStack input = getStackInSlot(0);

                    if (input != null) {
                        ItemStack recipe = RecipeManager.furnace.findOutput(input);

                        decrStackSize(0, 1);

                        if (getStackInSlot(1) == null) {
                            setInventorySlotContents(1, recipe.copy());
                        } else if (getStackInSlot(1).isItemEqual(recipe)) {
                            getStackInSlot(1).stackSize += recipe.stackSize;
                        }
                    }
                }

                setProgress(0);
            }
        }
    }

}
