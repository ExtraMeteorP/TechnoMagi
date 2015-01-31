package com.ollieread.technomagi.api.helpers;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.nanites.PlayerNanites;

public class EntityHelper
{

    public static PlayerTechnomagi getPlayerTechnomagi(EntityPlayer player)
    {
        return PlayerTechnomagi.get(player);
    }

    public static PlayerKnowledge getPlayerKnowledge(EntityPlayer player)
    {
        return getPlayerTechnomagi(player).knowledge();
    }

    public static PlayerNanites getPlayerNanites(EntityPlayer player)
    {
        return getPlayerTechnomagi(player).nanites();
    }

}
