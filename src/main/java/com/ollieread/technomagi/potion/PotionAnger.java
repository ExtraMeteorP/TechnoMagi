package com.ollieread.technomagi.potion;

import net.minecraft.entity.EntityLivingBase;

public class PotionAnger extends PotionTM
{

    public PotionAnger()
    {
        super("anger", false, 8171462);

        setIconIndex(0, 1);
    }

    @Override
    public void performEffect(EntityLivingBase entity, int effect)
    {

    }

}
