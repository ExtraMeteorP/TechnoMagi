package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.crafting.ConstructRecipe;
import com.ollieread.technomagi.item.crafting.RecipeManager;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityBasic;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;
import com.ollieread.technomagi.util.PacketHelper;

public class TileEntityConstruct extends TileEntityBasic implements IInventory, ITileEntityHasOwner, ITileEntityGui
{
    protected ComponentOwner owner = null;
    protected ComponentInventory inventory = null;

    protected Random rand = new Random();
    protected boolean isBuilding = false;
    protected int ticks = 0;

    public TileEntityConstruct()
    {
        owner = new ComponentOwner();
        inventory = new ComponentInventory(5);
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
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (isBuilding || canBuild()) {
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
                EntityPlayer player = owner.getOwner(worldObj);

                if (player != null) {
                    ItemStack[] stacks = new ItemStack[] { inventory.getStackInSlot(1), inventory.getStackInSlot(2), inventory.getStackInSlot(3), inventory.getStackInSlot(4) };

                    if (RecipeManager.construct.find(stacks, player) != null) {
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

        EntityPlayer player = owner.getOwner(worldObj);

        if (player != null) {
            ConstructRecipe recipe = RecipeManager.construct.find(stacks, player);
            ItemStack output = recipe.getRecipeOutput();

            worldObj.setBlock(xCoord, yCoord, zCoord, Block.getBlockFromItem(output.getItem()), output.getItemDamage(), 2);
            TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);

            if (tile instanceof ITileEntityHasOwner) {
                ((ITileEntityHasOwner) tile).setOwner(player.getCommandSenderName());
            }

            if (tile instanceof ITileEntityFacing) {
                ((ITileEntityFacing) tile).setFacing(getBlockMetadata());
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

        owner.readFromNBT(compound);
        inventory.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Ticks", ticks);
        compound.setBoolean("IsBuilding", isBuilding);

        owner.writeToNBT(compound);
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

    /* OWNER */

    @Override
    public boolean isOwner(String name)
    {
        return owner.isOwner(name);
    }

    @Override
    public void setOwner(String name)
    {
        owner.setOwner(name);
    }

    @Override
    public EntityPlayer getOwner(World world)
    {
        return owner.getOwner(world);
    }

}
