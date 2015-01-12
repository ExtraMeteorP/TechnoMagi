package com.ollieread.technomagi.ability.active;

import java.util.Random;

import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityPayload;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.ability.IAbilityActiveHasModes;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Config;

public class ActiveAbilityProjectile extends AbilityActive implements IAbilityActiveHasModes
{

    protected int cost;
    protected ResourceLocation[] modeIcons;

    public ActiveAbilityProjectile(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.projectileCost;

        modeIcons = new ResourceLocation[8];
        modeIcons[0] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + ".png");
        modeIcons[1] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Energy.png");
        modeIcons[2] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Fire.png");
        modeIcons[3] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Follow.png");
        modeIcons[4] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Ice.png");
        modeIcons[5] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Multiple.png");
        modeIcons[6] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Poison.png");
        modeIcons[7] = new ResourceLocation(Reference.MODID.toLowerCase(), "textures/abilities/" + name + "Soeed.png");
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
        int j = 72000;

        boolean flag = charon.player.capabilities.isCreativeMode;
        Random rand = new Random();

        if (flag) {
            if (charon.player.inventory.hasItem(Items.arrow) && charon.nanites.decreaseNanites(cost)) {
                float f = (float) j / 20.0F;
                f = (f * f + f * 2.0F) / 3.0F;

                if ((double) f < 0.1D) {
                    return false;
                }

                if (f > 1.0F) {
                    f = 1.0F;
                }

                EntityArrow entityarrow = new EntityArrow(charon.player.worldObj, charon.player, f * 2.0F);

                if (f == 1.0F) {
                    entityarrow.setIsCritical(true);
                }

                if (flag) {
                    entityarrow.canBePickedUp = 2;
                } else {
                    charon.player.inventory.consumeInventoryItem(Items.arrow);
                }

                charon.player.worldObj.spawnEntityInWorld(entityarrow);

                return true;
            }
        }

        return false;
    }

    @Override
    public int switchMode(ExtendedPlayerKnowledge playerKnowledge, int mode)
    {
        return 0;
    }

    @Override
    public int getMaxModes()
    {
        return 8;
    }

    @Override
    public ResourceLocation getModeIcon(int mode)
    {
        return modeIcons[mode];
    }

    @Override
    public ResourceLocation getModeIcon(int mode, int staffMode)
    {
        return getModeIcon(mode);
    }

    @Override
    public String getModeLocalisedName(int mode)
    {
        return null;
    }

    @Override
    public String getModeLocalisedName(int mode, int staffMode)
    {
        return null;
    }

    @Override
    public String[] getModeKnowledge(int mode)
    {
        return null;
    }

    @Override
    public String[] getModeKnowledge(int mode, int staffMode)
    {
        return null;
    }

}
