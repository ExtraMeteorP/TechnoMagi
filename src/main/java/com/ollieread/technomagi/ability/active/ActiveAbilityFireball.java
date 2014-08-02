package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFireball extends AbilityActive
{

    public ActiveAbilityFireball(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, Event event)
    {
        return true;
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_AIR)) {
                EntityPlayer player = interact.entityPlayer;

                if (!player.worldObj.isRemote) {
                    Vec3 look = PlayerHelper.getLookVector(player);
                    Vec3 eye = PlayerHelper.getEyeVector(player);
                    Random rand = new Random();

                    float f = 2 * 0.5F;
                    EntityFireball fireball = new EntitySmallFireball(player.worldObj, player, look.xCoord, look.yCoord, look.zCoord);
                    fireball.posY = eye.yCoord;
                    fireball.rotationPitch = player.rotationPitch;
                    fireball.rotationYaw = player.rotationYaw;
                    player.worldObj.spawnEntityInWorld(fireball);
                }

                return true;
            }
        }

        return false;
    }
}
