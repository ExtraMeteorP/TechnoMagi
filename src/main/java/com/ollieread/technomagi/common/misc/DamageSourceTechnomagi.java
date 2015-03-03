package com.ollieread.technomagi.common.misc;

import net.minecraft.util.DamageSource;

public class DamageSourceTechnomagi extends DamageSource
{

    public static DamageSource voidDamage = (new DamageSourceTechnomagi("void")).setDamageBypassesArmor();
    public static DamageSource frostDamage = (new DamageSourceTechnomagi("frost")).setDamageBypassesArmor();
    public static DamageSource lifeforceDamage = (new DamageSourceTechnomagi("lifeforce")).setDamageBypassesArmor();
    public static DamageSource naniteDamage = (new DamageSourceTechnomagi("nanite")).setDamageBypassesArmor().setDamageIsAbsolute();
    public static DamageSource dimensionalDamage = (new DamageSourceTechnomagi("dimensional")).setDamageBypassesArmor().setDamageIsAbsolute();
    public static DamageSource electromagneticDamage = (new DamageSourceTechnomagi("electromagnetic")).setDamageBypassesArmor().setDamageIsAbsolute();

    public DamageSourceTechnomagi(String name)
    {
        super(name);
    }

}
