package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityFireball extends AbilityActive
{

    public ActiveAbilityFireball(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.hasKnowledge("pyrologyII");
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;

            if (interact.action.equals(Action.RIGHT_CLICK_AIR)) {
                EntityPlayer player = interact.entityPlayer;

                if (decreaseNanites(charon, 15)) {
                    if (!player.worldObj.isRemote) {
                        Vec3 look = EntityHelper.getLookVector(player);
                        Vec3 eye = EntityHelper.getEyeVector(player);
                        Random rand = new Random();

                        float f = 2 * 0.5F;
                        EntityFireball fireball = new EntitySmallFireball(player.worldObj, player, look.xCoord, look.yCoord, look.zCoord);
                        fireball.posY = eye.yCoord;
                        fireball.rotationPitch = player.rotationPitch;
                        fireball.rotationYaw = player.rotationYaw;

                        double d3 = (double) MathHelper.sqrt_double(look.xCoord * look.xCoord + look.yCoord * look.yCoord + look.zCoord * look.zCoord);
                        fireball.accelerationX = look.xCoord / d3 * 0.1D;
                        fireball.accelerationY = look.yCoord / d3 * 0.1D;
                        fireball.accelerationZ = look.zCoord / d3 * 0.1D;

                        player.worldObj.spawnEntityInWorld(fireball);
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

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "pyrologyII" };
    }
}
