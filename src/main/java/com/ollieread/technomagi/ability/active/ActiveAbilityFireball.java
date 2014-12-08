package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
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
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("exo", 1);
        this.enhancements.put("force", 3);
        this.cost = Config.fireballCost;

        this.knowledge = new String[] { "exothermic", "projectile" };
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
    public Map<String, Integer> getEnhancements()
    {
        return enhancements;
    }

    @Override
    public Map<String, Integer> getEnhancements(int mode)
    {
        return getEnhancements();
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityCast cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH) && cast.target.equals(AbilityUseTarget.AIR);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
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
