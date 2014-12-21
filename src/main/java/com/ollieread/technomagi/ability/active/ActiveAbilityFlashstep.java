package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.util.EntityHelper;

public class ActiveAbilityFlashstep extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int distance;

    public ActiveAbilityFlashstep(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("force", 2);
        this.cost = Config.flashstepCost;
        this.distance = Config.flashstepDistance;

        this.knowledge = new String[] { "motion" };
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
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH) && cast.target.equals(AbilityUseTarget.AIR);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        if (charon.player.isSprinting()) {
            Vec3 look = EntityHelper.getLookVector(charon.player);
            Vec3 eye = EntityHelper.getEyeVector(charon.player);

            Vec3 target = Vec3.createVectorHelper(look.xCoord, charon.player.posY, look.zCoord);
            Vec3 dest = null;
            int max = distance * distance;
            int dmg = 0;
            Random rand = new Random();
            World world = charon.player.worldObj;

            for (int i = 1; i <= max; i++) {
                target.xCoord = (look.xCoord * i) + eye.xCoord;
                target.zCoord = (look.zCoord * i) + eye.zCoord;

                if (!world.isAirBlock(MathHelper.floor_double(target.xCoord), MathHelper.floor_double(target.yCoord), MathHelper.floor_double(target.zCoord)) || eye.squareDistanceTo(target) >= max || !world.isAirBlock(MathHelper.floor_double(target.xCoord), MathHelper.floor_double(target.yCoord + 1), MathHelper.floor_double(target.zCoord))) {
                    break;
                } else {
                    dest = Vec3.createVectorHelper(target.xCoord, target.yCoord, target.zCoord);
                }
            }

            if (dest != null && eye.squareDistanceTo(dest) < max && decreaseNanites(charon, cost)) {

                if (!world.isRemote) {
                    charon.player.setPositionAndUpdate(dest.xCoord, dest.yCoord, dest.zCoord);
                }

                return true;
            }
        }

        return false;
    }

}
