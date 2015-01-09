package com.ollieread.technomagi.util;

import net.minecraft.potion.Potion;

public class PotionHelper
{

    public static int nextPotionId()
    {
        for (int i = 0; i < Potion.potionTypes.length; i++) {
            if (Potion.potionTypes[i] == null) {
                return i;
            }
        }

        return -1;
    }

}
