package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityForceTarget extends AbilityActive
{

    public ActiveAbilityForceTarget(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, Event event)
    {
        return charon.hasKnowledge("forceI");
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.hasKnowledge("forceI");
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof EntityInteractEvent) {
            EntityInteractEvent interact = (EntityInteractEvent) event;

            if (interact.target instanceof EntityLiving) {
                EntityLiving entity = (EntityLiving) interact.target;

                if (decreaseNanites(charon, 6)) {
                    if (!entity.worldObj.isRemote) {
                        Vec3 look = EntityHelper.getLookVector(interact.entityPlayer);
                        Vec3 eye = EntityHelper.getEyeVector(interact.entityPlayer);

                        Vec3 target = Vec3.createVectorHelper(look.xCoord, look.yCoord, look.zCoord);
                        Vec3 dest = null;

                        Random rand = new Random();

                        double d3 = (double) MathHelper.sqrt_double(look.xCoord * look.xCoord + look.yCoord * look.yCoord + look.zCoord * look.zCoord);

                        entity.motionX = look.xCoord / d3 * 5.0D;
                        entity.motionY = look.yCoord / d3 * 5.0D;
                        entity.motionZ = look.zCoord / d3 * 5.0D;
                        entity.attackEntityFrom(DamageSource.generic, 2.0F);

                        interact.entityPlayer.worldObj.playSoundEffect((double) interact.entityPlayer.posX + 0.5D, (double) interact.entityPlayer.posY + 0.5D, (double) interact.entityPlayer.posZ + 0.5D, Reference.MODID.toLowerCase() + ":cast", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                    }

                    return true;
                }
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
