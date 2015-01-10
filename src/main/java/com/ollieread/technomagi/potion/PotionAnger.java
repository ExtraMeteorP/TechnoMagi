package com.ollieread.technomagi.potion;

import net.minecraft.entity.EntityLivingBase;

public class PotionAnger extends PotionTM
{

    public PotionAnger()
    {
        super("potion.tm.anger", false, 8171462);

        setTexture("textures/abilities/anger.png");
    }

    @Override
    public void performEffect(EntityLivingBase entity, int effect)
    {

    }

}
