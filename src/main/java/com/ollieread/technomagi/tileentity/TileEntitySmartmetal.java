package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.block.BlockSmartmetal;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityBasic;
import com.ollieread.technomagi.tileentity.component.ComponentDisguisable;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;
import com.ollieread.technomagi.util.ItemHelper;

public class TileEntitySmartmetal extends TileEntityBasic implements ITileEntityDisguisable, ITileEntityHasOwner
{

    protected ComponentDisguisable disguise = new ComponentDisguisable();
    protected ComponentOwner owner = new ComponentOwner();
    protected int mode = 0;

    public boolean setMode(ItemStack stack)
    {
        System.out.println(stack);
        if (ItemHelper.matches(stack, ItemHelper.item("wooden_door"))) {
            setMode(1);
        } else if (ItemHelper.matches(stack, ItemHelper.item("iron_door"))) {
            setMode(2);
        } else if (ItemHelper.matchesBlock(stack, Blocks.glass)) {
            setMode(3);
            setDisguise(null);
        } else if (ItemHelper.matchesBlock(stack, Blocks.trapdoor)) {
            setMode(4);
        }

        return false;
    }

    public void setMode(int mode)
    {
        this.mode = mode;

        worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, mode, 2);
        spreadMode();
    }

    private void spreadMode()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (int i = 0; i < directions.length; i++) {
            int x = xCoord + directions[i].offsetX;
            int y = yCoord + directions[i].offsetY;
            int z = zCoord + directions[i].offsetZ;
            Block block = worldObj.getBlock(x, y, z);
            int meta = worldObj.getBlockMetadata(x, y, z);

            if (block != null && block instanceof BlockSmartmetal) {
                if (meta != mode) {
                    TileEntitySmartmetal tile = (TileEntitySmartmetal) worldObj.getTileEntity(x, y, z);

                    tile.setMode(mode);
                }
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        mode = compound.getInteger("Mode");

        disguise.readFromNBT(compound.getCompoundTag("Disguise"));
        owner.readFromNBT(compound.getCompoundTag("Owner"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Mode", mode);

        NBTTagCompound disguiseCompound = new NBTTagCompound();
        NBTTagCompound ownerCompound = new NBTTagCompound();

        disguise.writeToNBT(disguiseCompound);
        owner.writeToNBT(ownerCompound);

        compound.setTag("Disguise", disguiseCompound);
        compound.setTag("Owner", ownerCompound);
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
