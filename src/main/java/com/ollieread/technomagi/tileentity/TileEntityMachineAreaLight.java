package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityBasic;
import com.ollieread.technomagi.tileentity.component.ComponentDisguisable;
import com.ollieread.technomagi.util.PlayerHelper;

public class TileEntityMachineAreaLight extends TileEntityBasic implements ITileEntityDisguisable, ITileEntityToolable
{

    protected ComponentDisguisable disguise = new ComponentDisguisable();

    protected boolean on;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        on = compound.getBoolean("On");

        disguise.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);

        disguise.writeToNBT(compound);
    }

    public boolean isOn()
    {
        return on;
    }

    public void toggleStatus()
    {
        if (!worldObj.isRemote) {
            if (on) {
                on = false;
            } else {
                on = true;

                if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockLightAir);
                    TileEntityAirLight light = (TileEntityAirLight) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                    if (light != null) {
                        light.setMaster(xCoord, yCoord, zCoord);
                        light.spreadBlocks();
                    }
                }
            }
        }
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            ItemDigitalTool digitalTool = (ItemDigitalTool) tool.getItem();

            if (tool != null) {
                if (player.isSneaking()) {
                    if (isDisguised()) {
                        setDisguise(null);
                        world.markBlockForUpdate(x, y, z);

                        return true;
                    }
                } else {
                    toggleStatus();
                    world.markBlockForUpdate(x, y, z);
                    PlayerHelper.addChatMessage(player, "Area light " + (isOn() ? "enabled" : "disabled"));

                    return true;
                }
            }
        }

        return false;
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
