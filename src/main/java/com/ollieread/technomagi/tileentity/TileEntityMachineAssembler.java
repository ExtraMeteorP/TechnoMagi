package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.item.crafting.IRecipeTM;
import com.ollieread.technomagi.item.crafting.RecipeManager;
import com.ollieread.technomagi.network.message.MessageSyncTileEntityTM;
import com.ollieread.technomagi.tileentity.abstracts.MachineInventory;
import com.ollieread.technomagi.tileentity.component.Inventory;
import com.ollieread.technomagi.util.PacketHelper;

public class TileEntityMachineAssembler extends MachineInventory
{

    protected boolean isCrafting = false;

    public TileEntityMachineAssembler()
    {
        super(Config.craftingPowerMax, Config.craftingPowerRecieve, 0, new Inventory(10));

        setMaxProgress(Config.craftingProgressMax);
        setUsage(Config.craftingPowerUse);
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
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        isCrafting = compound.getBoolean("IsCrafting");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("IsCrafting", isCrafting);
    }

    public void setCrafting(boolean crafting)
    {
        isCrafting = crafting;
        PacketHelper.syncTile(new MessageSyncTileEntityTM(this));
    }

    public boolean canProcess()
    {
        EntityPlayer player = getOwner(worldObj);

        if (player != null) {
            IRecipeTM recipe = null;

            if (isCrafting) {
                recipe = RecipeManager.assembler.find(getInventory(), worldObj);
            } else {
                recipe = RecipeManager.assembler.find(getInventory(), worldObj, player);
            }

            if (recipe != null) {
                return getEnergyStored(null) > getUsage();
            }
        }

        return false;
    }

    public boolean isProcessing()
    {
        return isCrafting;
    }

    public void process()
    {
        if (energy.modifyEnergyStored(getUsage())) {
            setProgress(getProgress() + 1);

            if (getProgress() >= getMaxProgress()) {
                IRecipeTM recipe = RecipeManager.assembler.find(getInventory(), worldObj);

                setProgress(0);
                setCrafting(false);
            } else {

            }
        }
    }

}
