package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;

import com.ollieread.technomagi.tileentity.abstracts.TileEntityPocket;

import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TileEntityPocketGrowth extends TileEntityPocket
{

    public TileEntityPocketGrowth(boolean negative, int size)
    {
        super(size);

        setNegative(negative);
    }

    @Override
    public boolean shouldPerform(int ticks)
    {
        return false;
    }

    @Override
    public void perform()
    {

    }

    @Override
    public int getModifier(double distance)
    {
        return (4 - ((int) distance / 2));
    }

    public double getDistance(double x, double y, double z)
    {
        double d3 = xCoord - x;
        double d4 = yCoord - y;
        double d5 = zCoord - z;
        return (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
    }

    @SubscribeEvent
    public void onBonemeal(BonemealEvent event)
    {
        if (!worldObj.isRemote) {
            if (isNegative() && getDistance(event.x, event.y, event.z) <= (size / 2)) {
                event.setResult(Result.DENY);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onSaplingGrow(SaplingGrowTreeEvent event)
    {
        if (!worldObj.isRemote) {
            if (isNegative() && getDistance(event.x, event.y, event.z) <= (size / 2)) {
                event.setResult(Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (!worldObj.isRemote) {
            if (isNegative() && getDistance(event.x, event.y, event.z) <= (size / 2)) {
                ItemStack heldItem = event.entityPlayer.getCurrentEquippedItem();

                if (heldItem != null && heldItem.getItem() != null) {
                    Block block = Block.getBlockFromItem(heldItem.getItem());

                    if (block != null) {
                        if (!(block instanceof IPlantable)) {
                            event.setCanceled(true);
                            event.setResult(Result.DENY);
                            event.useBlock = Result.DENY;
                            event.useItem = Result.DENY;
                        }
                    } else if (heldItem.getItem() instanceof IPlantable) {
                        event.setCanceled(true);
                        event.setResult(Result.DENY);
                        event.useBlock = Result.DENY;
                        event.useItem = Result.DENY;
                    }
                }
            }
        }
    }

    @Override
    public PocketType getType()
    {
        return PocketType.AREA;
    }

}
