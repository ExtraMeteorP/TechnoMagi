package com.ollieread.technomagi.ability.active;

import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityPayload;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.EntityHelper;

public class ActiveAbilityFireball extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;

    public ActiveAbilityFireball(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.fireballCost;
    }

    @Override
    public int getMaxFocus()
    {
        return 0;
    }

    @Override
    public boolean canIntervalFocus()
    {
        return false;
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityPayload cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH) && cast.target.equals(AbilityUseTarget.AIR);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        EntityPlayer player = charon.player;

        if (decreaseNanites(charon, cost)) {
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
            }

            return true;
        }

        return false;
    }

    @Override
    public int getCooldown()
    {
        return 200;
    }
}
