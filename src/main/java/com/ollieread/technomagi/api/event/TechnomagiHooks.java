package com.ollieread.technomagi.api.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.ChunkPosition;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.IAbilityCast;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.PocketSize;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeBlock;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeEntity;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeItem;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Chance;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Progress;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.specialisation.Specialisation;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;

public class TechnomagiHooks
{

    public static void specialise(EntityPlayer player, Specialisation specialisation)
    {
        TechnomagiApi.EVENT_BUS.post(new TechnomagiEvent.Specialise(player, specialisation));
    }

    public static KnowledgeEvent.Chance researchChance(EntityPlayer entity, IResearch research, int chance)
    {
        Chance event = new Chance(entity, research, chance);

        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static KnowledgeEvent.Progress.Pre preKnowledgeProgress(EntityPlayer entity, IResearch research, int current, int progress)
    {
        Progress.Pre event = new Progress.Pre(entity, research, current, progress);

        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static void postKnowledgeProgress(EntityPlayer entity, IResearch research, int current, int progress)
    {
        TechnomagiApi.EVENT_BUS.post(new Progress.Post(entity, research, current, progress));
    }

    public static void unlockedKnowledge(EntityPlayer player, Knowledge knowledge)
    {
        TechnomagiApi.EVENT_BUS.post(new KnowledgeEvent.Unlocked(player, knowledge));
    }

    public static AbilityCastEvent.Start startCasting(EntityPlayer player, IAbilityCast ability, AbilityPayload payload)
    {
        AbilityCastEvent.Start event = new AbilityCastEvent.Start(player, ability, payload);

        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static AbilityCastEvent.Cast continueCasting(EntityPlayer player, IAbilityCast ability, AbilityPayload payload)
    {
        AbilityCastEvent.Cast event = new AbilityCastEvent.Cast(player, ability, payload);

        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static void stopCasting(EntityPlayer player, IAbilityCast ability, AbilityPayload payload, boolean complete)
    {
        TechnomagiApi.EVENT_BUS.post(new AbilityCastEvent.Stop(player, ability, payload, complete));
    }

    public static ElectromagneticPocketEvent.ExposeEntity electromagneticPocketEntity(TileElectromagnetic tile, EntityLivingBase entity, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount, boolean first)
    {
        ExposeEntity event = new ElectromagneticPocketEvent.ExposeEntity(tile, entity, type, size, negative, modifier, amount, first);
        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static ElectromagneticPocketEvent.ExposeItem electromagneticPocketItem(TileElectromagnetic tile, EntityItem item, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount)
    {
        ExposeItem event = new ElectromagneticPocketEvent.ExposeItem(tile, item, type, size, negative, modifier, amount);
        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

    public static ElectromagneticPocketEvent.ExposeBlock electromagneticPocketBlock(TileElectromagnetic tile, ChunkPosition block, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount)
    {
        ExposeBlock event = new ElectromagneticPocketEvent.ExposeBlock(tile, block, type, size, negative, modifier, amount);
        TechnomagiApi.EVENT_BUS.post(event);

        return event;
    }

}
