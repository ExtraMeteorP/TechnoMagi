package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.specialisation.Specialisation;
import com.ollieread.technomagi.util.PlayerHelper;

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
        this.playerTechnomagi = PlayerHelper.getTechnomagi(player);
    }

    public static class Specialise extends TechnomagiEvent
    {

        public final Specialisation specialisation;

        public Specialise(EntityPlayer player, Specialisation specialisation)
        {
            super(player);

            this.specialisation = specialisation;
        }

    }

}
