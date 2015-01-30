package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.TechnoMagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

/**
 * This contains all of the TechnoMagi related events and is posted to
 * {@link TechnoMagiApi.EVENT_BUS}.
 * 
 * @author ollieread
 *
 */
public class TechnoMagiEvent extends Event
{

    public final EntityPlayer entityPlayer;
    public final PlayerKnowledge playerKnowledge;

    public TechnoMagiEvent(EntityPlayer player)
    {
        this.entityPlayer = player;
        this.playerKnowledge = PlayerKnowledge.get(player);
    }

    public static class ResearchChanceEvent extends TechnoMagiEvent
    {

        public final IResearch research;
        public int chance;

        public ResearchChanceEvent(EntityPlayer entity, IResearch research, int chance)
        {
            super(entity);
            this.research = research;
            this.chance = chance;
        }

    }

    public static class KnowledgeProgressEvent extends TechnoMagiEvent
    {

        public final IResearch research;
        public final int current;
        public final int progress;
        public int modifier = 0;
        public final boolean researchFired;

        public KnowledgeProgressEvent(EntityPlayer entity, IResearch research, int current, int progress, boolean researchFired)
        {
            super(entity);

            this.research = research;
            this.current = current;
            this.progress = progress;
            this.researchFired = researchFired;
        }

        @Cancelable
        public static class Pre extends KnowledgeProgressEvent
        {

            public Pre(EntityPlayer entity, IResearch research, int current, int progress)
            {
                super(entity, research, current, progress, false);
            }

        }

        public static class Post extends KnowledgeProgressEvent
        {

            public final boolean intrigue;

            public Post(EntityPlayer entity, IResearch research, int current, int progress)
            {
                super(entity, research, current, progress, true);

                if (current == 0 && progress > 0) {
                    intrigue = true;
                } else {
                    intrigue = false;
                }
            }

        }

    }

    public static class KnowledgeUnlockedEvent extends TechnoMagiEvent
    {

        public final Knowledge knowledge;

        public KnowledgeUnlockedEvent(EntityPlayer entity, Knowledge knowledge)
        {
            super(entity);
            this.knowledge = knowledge;
        }

    }

}
