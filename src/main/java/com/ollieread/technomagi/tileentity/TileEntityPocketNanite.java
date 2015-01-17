package com.ollieread.technomagi.tileentity;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;

import com.ollieread.technomagi.potion.PotionTM;
import com.ollieread.technomagi.tileentity.abstracts.Pocket;

public class TileEntityPocketNanite extends Pocket
{

    public TileEntityPocketNanite(boolean negative, int size)
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
            PotionTM potion = null;

            if (isNegative()) {
                potion = PotionTM.naniteDegeneration;
            } else {
                potion = PotionTM.naniteRegeneration;
            }

            if (player != null) {
                if (!player.isPotionActive(potion)) {
                    player.addPotionEffect(new PotionEffect(potion.id, 200, getModifier(player.getDistance(xCoord, yCoord, zCoord))));
                }
            }
        }
    }

    @Override
    public int getModifier(double distance)
    {
        return (4 - ((int) distance / 2));
    }

    @Override
    public PocketType getType()
    {
        return PocketType.PLAYER;
    }

}
