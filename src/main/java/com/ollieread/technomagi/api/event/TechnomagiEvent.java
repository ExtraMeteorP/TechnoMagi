package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.helpers.EntityHelper;

import cpw.mods.fml.common.eventhandler.Event;

/**
 * This contains all of the TechnoMagi related events and is posted to
 * {@link TechnomagiApi.EVENT_BUS}.
 * 
 * @author ollieread
 *
 */
public class TechnomagiEvent extends Event
{

    public final EntityPlayer entityPlayer;
    public final PlayerTechnomagi playerTechnomagi;

    public TechnomagiEvent(EntityPlayer player)
    {
        this.entityPlayer = player;
        this.playerTechnomagi = EntityHelper.getPlayerTechnomagi(player);
    }

}
