package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.EntityHelper;

public class ActiveAbilityBlink extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int distance;

    public ActiveAbilityBlink(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("teleport", 1);
        this.cost = Config.blinkCost;
        this.distance = Config.blinkDistance;
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
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityCast cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        Vec3 look = EntityHelper.getLookVector(charon.player);
        Vec3 eye = EntityHelper.getEyeVector(charon.player);

        Vec3 target = Vec3.createVectorHelper(look.xCoord, look.yCoord, look.zCoord);
        Vec3 dest = null;

        Random rand = new Random();

        target.xCoord = (look.xCoord * distance) + eye.xCoord;
        target.yCoord = (look.yCoord * distance) + eye.yCoord;
        target.zCoord = (look.zCoord * distance) + eye.zCoord;

        EnderTeleportEvent teleportEvent = new EnderTeleportEvent(charon.player, target.xCoord, target.yCoord, target.zCoord, 0);
        boolean cancelled = MinecraftForge.EVENT_BUS.post(teleportEvent);

        if (!cancelled && decreaseNanites(charon, cost)) {
            for (int i = 0; i < 32; ++i) {
                charon.player.worldObj.spawnParticle("portal", teleportEvent.targetX, teleportEvent.targetY + rand.nextDouble() * 2.0D, teleportEvent.targetZ, rand.nextGaussian(), 0.0D, rand.nextGaussian());
            }

            if (!charon.player.worldObj.isRemote) {
                charon.player.setPositionAndUpdate(teleportEvent.targetX, teleportEvent.targetY, teleportEvent.targetZ);
            }

            return true;
        }

        return false;
    }

}
