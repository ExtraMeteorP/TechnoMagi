package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import com.ollieread.technomagi.common.proxy.BasicInventory;
import com.ollieread.technomagi.common.proxy.PlayerLocked;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.crafting.ConstructManager;
import com.ollieread.technomagi.util.PacketHelper;

public class TileEntityConstruct extends TileEntityTM implements IInventory, IPlayerLocked
{
    protected PlayerLocked locked = null;
    protected BasicInventory inventory = null;

    protected Random rand = new Random();
    protected boolean isBuilding = false;
    protected int ticks = 0;

    public TileEntityConstruct()
    {
        locked = new PlayerLocked();
        inventory = new BasicInventory(5);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (isBuilding && canBuild()) {
                ticks++;
                if (ticks == 100) {
                    build();
                }
            }
        }
    }

    public boolean isBuilding()
    {
        return isBuilding;
    }

    public boolean canBuild()
    {
        ItemStack nanites = inventory.getStackInSlot(0);

        if (nanites != null && nanites.getItem() instanceof ItemNaniteContainer && nanites.getItemDamage() == 1) {
            if (ItemNaniteContainer.getEntity(nanites).equals("player")) {
                EntityPlayer player = worldObj.getPlayerEntityByName(locked.getPlayer());

                if (player != null) {
                    ItemStack[] stacks = new ItemStack[] { inventory.getStackInSlot(1), inventory.getStackInSlot(2), inventory.getStackInSlot(3), inventory.getStackInSlot(4) };

                    if (ConstructManager.getInstance().findMatchingRecipe(stacks, player) != null) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void build()
    {
        ItemStack[] stacks = new ItemStack[] { inventory.getStackInSlot(1), inventory.getStackInSlot(2), inventory.getStackInSlot(3), inventory.getStackInSlot(4) };

        EntityPlayer player = worldObj.getPlayerEntityByName(locked.getPlayer());

        if (player != null) {
            Block block = ConstructManager.getInstance().findMatchingRecipe(stacks, player);

            worldObj.setBlock(xCoord, yCoord, zCoord, block, getBlockMetadata(), 2);
            TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);

            if (tile instanceof IPlayerLocked) {
                ((IPlayerLocked) tile).setPlayer(getPlayer());
            }
        }
    }

    public void setBuilding(boolean building)
    {
        isBuilding = building;

        PacketHelper.syncTile(this);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        ticks = compound.getInteger("Ticks");
        isBuilding = compound.getBoolean("IsBuilding");

        locked.readFromNBT(compound);
        inventory.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Ticks", ticks);
        compound.setBoolean("IsBuilding", isBuilding);

        locked.writeToNBT(compound);
        inventory.writeToNBT(compound);
    }

    /* Everything below is just a proxy for the interfaces */

    /* INVENTORY */

    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int i, int q)
    {
        return inventory.decrStackSize(i, q);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack stack)
    {
        inventory.setInventorySlotContents(i, stack);
    }

    @Override
    public String getInventoryName()
    {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
    }

    @Override
    public void openInventory()
    {

    }

    @Override
    public void closeInventory()
    {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack stack)
    {
        return inventory.isItemValidForSlot(i, stack);
    }

    /* LOCKED */

    @Override
    public boolean hasPlayer()
    {
        return locked.hasPlayer();
    }

    @Override
    public void setPlayer(String name)
    {
        locked.setPlayer(name);
    }

    @Override
    public String getPlayer()
    {
        return locked.getPlayer();
    }

    @Override
    public boolean isPlayer(String name)
    {
        return locked.isPlayer(name);
    }

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

}
