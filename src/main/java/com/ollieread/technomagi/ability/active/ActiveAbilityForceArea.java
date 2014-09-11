package com.ollieread.technomagi.ability.active;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityForceArea extends AbilityActive
{

    public ActiveAbilityForceArea(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, Event event)
    {
        return charon.hasKnowledge("forceII");
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.hasKnowledge("forceII");
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;
            EntityPlayer player = interact.entityPlayer;

            if (decreaseNanites(charon, 6)) {
                if (!player.worldObj.isRemote) {
                    int i = MathHelper.floor_double(player.posX - 5D);
                    int j = MathHelper.floor_double(player.posX + 5D);
                    int k = MathHelper.floor_double(player.posY - 5D);
                    int i2 = MathHelper.floor_double(player.posY + 5D);
                    int l = MathHelper.floor_double(player.posZ - 5D);
                    int j2 = MathHelper.floor_double(player.posZ + 5D);

                    List list = player.worldObj.getEntitiesWithinAABBExcludingEntity(player, AxisAlignedBB.getBoundingBox((double) i, (double) k, (double) l, (double) j, (double) i2, (double) j2));
                    Vec3 vec3 = Vec3.createVectorHelper(player.posX, player.posY, player.posZ);

                    Random rand = new Random();

                    for (int i1 = 0; i1 < list.size(); ++i1) {
                        Entity entity = (Entity) list.get(i1);
                        double d5 = entity.posX - player.posX;
                        double d6 = entity.posY + (double) entity.getEyeHeight() - player.posY;
                        double d7 = entity.posZ - player.posZ;
                        double d9 = (double) MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);

                        if (decreaseNanites(charon, 3) && d9 != 0.0D) {
                            d5 /= d9;
                            d6 /= d9;
                            d7 /= d9;
                            entity.motionX += d5;
                            entity.motionY += d6;
                            entity.motionZ += d7;
                            entity.attackEntityFrom(DamageSource.generic, 2.0F);
                        }
                    }

                    interact.entityPlayer.worldObj.playSoundEffect((double) interact.entityPlayer.posX + 0.5D, (double) interact.entityPlayer.posY + 0.5D, (double) interact.entityPlayer.posZ + 0.5D, Reference.MODID.toLowerCase() + ":cast", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                }

                return true;
            }
        }

        return false;
    }

    @Override
    public String[] getEnhancements()
    {
        return null;
    }
}
