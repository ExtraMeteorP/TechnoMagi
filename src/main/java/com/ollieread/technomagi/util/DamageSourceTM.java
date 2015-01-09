package com.ollieread.technomagi.util;

import net.minecraft.util.DamageSource;

public class DamageSourceTM extends DamageSource
{

    public static DamageSource voidDamage = (new DamageSourceTM("void")).setDamageBypassesArmor();

    public DamageSourceTM(String name)
    {
        super(name);
    }

}
