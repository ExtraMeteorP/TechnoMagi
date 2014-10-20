package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.proxy.Disguisable;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityHardlightGenerator extends TileEntityTM implements IPlayerLocked, IDisguisableTile
{

    protected Disguisable disguise = new Disguisable();
    protected boolean on = false;
    protected boolean proximity = false;
    protected PlayerLocked locked = null;
    protected int checked = 0;

    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (on) {

            }
        }
    }

    public void toggleStatus()
    {
        if (!worldObj.isRemote) {
            if (on) {
                on = false;
            } else {
                on = true;

                int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                ForgeDirection dir = ForgeDirection.getOrientation(meta);

                if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockLightAir);
                    TileEntityLightAir light = (TileEntityLightAir) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                    if (light != null) {
                        light.setMaster(xCoord, yCoord, zCoord);
                        light.spreadBlocks();
                    }
                }
            }
        }
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
