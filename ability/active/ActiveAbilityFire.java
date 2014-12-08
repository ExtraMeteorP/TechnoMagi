package com.ollieread.technomagi.ability.active;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityCast;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityCast.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityFire extends AbilityActive
{
    protected Map<String, Integer> enhancements;
    protected int cost;
    protected int costExtinguish;
    protected int costEntity;
    protected int burnTime;

    public ActiveAbilityFire(String name)
    {
        super(name, Reference.MODID.toLowerCase());
        this.enhancements = new HashMap<String, Integer>();
        this.enhancements.put("exo", 1);

        this.cost = Config.fireCost;
        this.costExtinguish = Config.fireExtinguishCost;
        this.costEntity = Config.fireEntityCost;
        this.burnTime = Config.fireBurnTime;

        this.knowledge = new String[] { "exothermic" };
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
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityCast cast, ItemStack staff)
    {
        if (cast.target.equals(AbilityUseTarget.BLOCK) || cast.target.equals(AbilityUseTarget.AIR)) {
            int x = cast.blockX;
            int y = cast.blockX;
            int z = cast.blockY;
            boolean air = cast.target.equals(AbilityUseTarget.AIR);

            if (!charon.player.canPlayerEdit(x, y, z, cast.sideHit, null)) {
                return false;
            } else {
                if (air) {
                    if (decreaseNanites(charon, cost)) {
                        if (!charon.player.worldObj.isRemote) {
                            Random rand = new Random();
                            charon.player.worldObj.playSoundEffect((double) x + 0.5D, (double) y + 0.5D, (double) z + 0.5D, Reference.MODID.toLowerCase() + ":cast", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
                            charon.player.worldObj.setBlock(x, y, z, Blocks.fire);
                        }

                        return true;
                    }
                } else {
                    Block block = charon.player.worldObj.getBlock(x, y, z);
                    if (block.isEqualTo(block, Blocks.fire)) {
                        if (decreaseNanites(charon, costExtinguish)) {
                            if (!charon.player.worldObj.isRemote) {
                                charon.player.worldObj.setBlockToAir(x, y, z);
                            }

                            return true;
                        }
                    }
                }
            }
        } else if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING)) {
            EntityLiving target = cast.targetEntityLiving;

            if (target != null) {
                if (!charon.player.worldObj.isRemote) {
                    if (target.isBurning()) {
                        if (charon.isSpecialisation("medic") && decreaseNanites(charon, costEntity)) {
                            target.extinguish();
                            return true;
                        }
                    } else {
                        if (charon.isSpecialisation("warrior") && decreaseNanites(charon, costEntity)) {
                            target.extinguish();
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
