package com.ollieread.technomagi.ability.active;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
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

public class ActiveAbilityForce extends AbilityActive
{

    protected int cost;

    public ActiveAbilityForce(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.forceCost;
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
        if (cast.type.equals(AbilityUseType.FLASH)) {
            if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING)) {
                return charon.isSpecialisation("warrior");
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        if (!cast.target.equals(AbilityUseTarget.ENTITY_LIVING)) {
            EntityPlayer player = charon.player;

            if (decreaseNanites(charon, cost)) {
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

                        if (decreaseNanites(charon, cost) && d9 != 0.0D) {
                            d5 /= d9;
                            d6 /= d9;
                            d7 /= d9;
                            entity.motionX += d5;
                            entity.motionY += d6;
                            entity.motionZ += d7;
                            entity.attackEntityFrom(DamageSource.generic, 2.0F);
                        }
                    }
                }

                return true;
            }
        } else if (cast.target.equals(AbilityUseTarget.ENTITY_LIVING)) {
            if (charon.isSpecialisation("warrior")) {
                EntityLiving entity = (EntityLiving) cast.targetEntityLiving;

                if (decreaseNanites(charon, cost)) {
                    if (!entity.worldObj.isRemote) {
                        Vec3 look = EntityHelper.getLookVector(charon.player);
                        Vec3 eye = EntityHelper.getEyeVector(charon.player);

                        Vec3 target = Vec3.createVectorHelper(look.xCoord, look.yCoord, look.zCoord);
                        Vec3 dest = null;

                        Random rand = new Random();

                        double d3 = (double) MathHelper.sqrt_double(look.xCoord * look.xCoord + look.yCoord * look.yCoord + look.zCoord * look.zCoord);

                        entity.motionX = look.xCoord / d3 * 5.0D;
                        entity.motionY = look.yCoord / d3 * 5.0D;
                        entity.motionZ = look.zCoord / d3 * 5.0D;
                        entity.attackEntityFrom(DamageSource.generic, 2.0F);
                    }

                    return true;
                }
            }
        }

        return false;
    }

}
