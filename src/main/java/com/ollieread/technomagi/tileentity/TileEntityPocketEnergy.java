package com.ollieread.technomagi.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import cofh.api.energy.IEnergyContainerItem;

import com.ollieread.technomagi.tileentity.abstracts.Pocket;

public class TileEntityPocketEnergy extends Pocket
{

    public TileEntityPocketEnergy(boolean negative, int size)
    {
        super(size);

        setNegative(negative);
    }

    @Override
    public boolean shouldPerform(int ticks)
    {
        return ticks == 20;
    }

    @Override
    public void perform()
    {
        List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord - (size / 2), yCoord - (size / 2), zCoord - (size / 2), xCoord + (size / 2), yCoord + (size / 2), zCoord + (size / 2)));

        for (EntityPlayer player : players) {
            ItemStack[] inventory = player.inventory.mainInventory;
            int amount = 10 * getModifier(player.getDistance(xCoord, yCoord, zCoord));

            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] != null) {
                    Item item = inventory[i].getItem();

                    if (item instanceof IEnergyContainerItem) {
                        if (isNegative()) {
                            ((IEnergyContainerItem) item).extractEnergy(inventory[i], amount, false);
                        } else {
                            ((IEnergyContainerItem) item).receiveEnergy(inventory[i], amount, false);
                        }
                    }
                }
            }

            inventory = player.inventory.armorInventory;

            for (int i = 0; i < inventory.length; i++) {
                if (inventory[i] != null) {
                    Item item = inventory[i].getItem();

                    if (item instanceof IEnergyContainerItem) {
                        if (isNegative()) {
                            ((IEnergyContainerItem) item).extractEnergy(inventory[i], amount, false);
                        } else {
                            ((IEnergyContainerItem) item).receiveEnergy(inventory[i], amount, false);
                        }
                    }
                }
            }
        }
    }

    @Override
    public int getModifier(double distance)
    {
        return 1;
    }

    @Override
    public PocketType getType()
    {
        return PocketType.PLAYER;
    }

}
