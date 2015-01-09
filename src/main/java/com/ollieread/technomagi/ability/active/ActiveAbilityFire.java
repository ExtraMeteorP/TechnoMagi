package com.ollieread.technomagi.ability.active;

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

    protected int cost;
    protected int costExtinguish;
    protected int costEntity;
    protected int burnTime;

    public ActiveAbilityFire(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.fireCost;
        costExtinguish = Config.fireExtinguishCost;
        costEntity = Config.fireEntityCost;
        burnTime = Config.fireBurnTime;
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
        if (cast.target.equals(AbilityUseTarget.BLOCK)) {
            int x = cast.blockX;
            int y = cast.blockY;
            int z = cast.blockZ;

            if (cast.sideHit == 0) {
                --y;
            }

            if (cast.sideHit == 1) {
                ++y;
            }

            if (cast.sideHit == 2) {
                --z;
            }

            if (cast.sideHit == 3) {
                ++z;
            }

            if (cast.sideHit == 4) {
                --x;
            }

            if (cast.sideHit == 5) {
                ++x;
            }

            boolean air = cast.target.equals(AbilityUseTarget.AIR);

            if (!charon.player.canPlayerEdit(x, y, z, cast.sideHit, null)) {
                return false;
            } else {
                if (!air) {
                    Block block = charon.player.worldObj.getBlock(x, y, z);
                    if (block.isEqualTo(block, Blocks.fire)) {
                        if (decreaseNanites(charon, costExtinguish)) {
                            if (!charon.player.worldObj.isRemote) {
                                charon.player.worldObj.setBlockToAir(x, y, z);
                            }

                            return true;
                        }
                    } else {
                        if (decreaseNanites(charon, cost)) {
                            if (!charon.player.worldObj.isRemote) {
                                charon.player.worldObj.setBlock(x, y, z, Blocks.fire);
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
