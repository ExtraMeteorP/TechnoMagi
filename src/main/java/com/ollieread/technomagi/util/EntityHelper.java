package com.ollieread.technomagi.util;

import net.minecraft.entity.EntityList;

public class EntityHelper
{

    public static int startEntityId = 0;

    public static int getUniqueEntityId()
    {
        do {
            startEntityId++;
        } while (EntityList.getStringFromID(startEntityId) != null);

        return startEntityId;
    }

}
