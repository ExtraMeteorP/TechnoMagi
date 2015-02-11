package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.IAbilityCast;

import cpw.mods.fml.common.eventhandler.Cancelable;

public class AbilityCastEvent extends TechnomagiEvent
{

    protected final AbilityPayload payload;
    protected final IAbilityCast ability;

    public AbilityCastEvent(EntityPlayer player, IAbilityCast ability, AbilityPayload payload)
    {
        super(player);

        this.ability = ability;
        this.payload = payload;
    }

    public AbilityPayload getPayload()
    {
        return payload;
    }

    public IAbilityCast getAbility()
    {
        return ability;
    }

    @Cancelable
    public static class Start extends AbilityCastEvent
    {

        public Start(EntityPlayer player, IAbilityCast ability, AbilityPayload payload)
        {
            super(player, ability, payload);
        }

    }

    @Cancelable
    @HasResult
    public static class Cast extends AbilityCastEvent
    {

        public Cast(EntityPlayer player, IAbilityCast ability, AbilityPayload payload)
        {
            super(player, ability, payload);
        }

    }

    public static class Stop extends AbilityCastEvent
    {

        public final boolean complete;

        public Stop(EntityPlayer player, IAbilityCast ability, AbilityPayload payload, boolean complete)
        {
            super(player, ability, payload);

            this.complete = complete;
        }

    }

}
