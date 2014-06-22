package com.ollieread.technomagi.util;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;

import com.ollieread.technomagi.world.ExplosionFall;

public class ExplosionHelper
{

    public static void newFallExplosion(World world, Entity entity, double x, double y, double z, float size)
    {
        ExplosionFall explosion = new ExplosionFall(world, entity, x, y, z, size);
        explosion.isFlaming = false;
        explosion.isSmoking = true;
        explosion.doExplosionA();
        explosion.doExplosionB(true);
    }

}
