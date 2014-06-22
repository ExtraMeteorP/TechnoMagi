package com.ollieread.technomagi.potion;

import net.minecraft.potion.Potion;

public class PotionTM extends Potion
{

    public PotionTM(int id, String name, boolean isBad, int colour)
    {
        super(id, isBad, colour);

        setPotionName(name);
        setIconIndex(0, 0);
    }

}
