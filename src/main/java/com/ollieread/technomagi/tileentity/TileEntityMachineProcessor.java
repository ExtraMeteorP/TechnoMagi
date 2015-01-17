package com.ollieread.technomagi.tileentity;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.item.crafting.RecipeManager;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineInventory;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;

public class TileEntityMachineProcessor extends TileEntityMachineInventory
{

    public TileEntityMachineProcessor()
    {
        super(Config.separatorPowerMax, Config.separatorPowerRecieve, 0, new ComponentInventory(3));

        setMaxProgress(Config.separatorProgressMax);
        setUsage(Config.separatorPowerUse);
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
                ItemStack recipe = RecipeManager.processor.findOutput(input);

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
            progress++;

            if (progress >= maxProgress) {
                EntityPlayer player = getOwner(worldObj);

                if (player != null) {
                    ItemStack input = getStackInSlot(0);
                    ItemStack recipe = RecipeManager.processor.findOutput(input);

                    decrStackSize(0, 1);

                    if (inventory.getStackInSlot(1) == null) {
                        inventory.setInventorySlotContents(1, recipe.copy());
                    } else if (inventory.getStackInSlot(1).isItemEqual(recipe)) {
                        inventory.getStackInSlot(1).stackSize += recipe.stackSize;
                    }

                    Map<Integer, ItemStack> extra = RecipeManager.processor.getExtra(input);

                    if (extra != null) {
                        ItemStack extraStack = null;
                        for (Iterator<Entry<Integer, ItemStack>> i = extra.entrySet().iterator(); i.hasNext();) {
                            Entry<Integer, ItemStack> entry = i.next();

                            if (rand.nextInt(entry.getKey()) == 0) {
                                extraStack = entry.getValue();
                                break;
                            }
                        }

                        if (inventory.getStackInSlot(2) == null) {
                            inventory.setInventorySlotContents(2, extraStack.copy());
                        } else if (inventory.getStackInSlot(2).isItemEqual(extraStack)) {
                            inventory.getStackInSlot(2).stackSize += extraStack.stackSize;
                        }
                    }
                }

                setProgress(0);
            }
        }
    }

}
