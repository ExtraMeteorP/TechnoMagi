package com.ollieread.technomagi.common.init;

import net.minecraft.entity.EntityList;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.entity.passive.EntityRobotCow;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.common.registry.EntityRegistry;

public class Entities
{

    public static void init()
    {
        int id = EntityHelper.getUniqueEntityId();
        EntityRegistry.registerModEntity(EntityRobotCow.class, "robotCow", id, TechnoMagi.instance, 80, 1, true);
        EntityList.addMapping(EntityRobotCow.class, "robotCow", id, 113213, 3523523);
    }

}
