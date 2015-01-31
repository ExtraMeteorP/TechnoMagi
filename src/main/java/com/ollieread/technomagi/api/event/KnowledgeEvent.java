package com.ollieread.technomagi.api.event;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

import cpw.mods.fml.common.eventhandler.Cancelable;

public class KnowledgeEvent extends TechnomagiEvent
{

    public final IResearch research;
    public final Knowledge knowledge;

    public KnowledgeEvent(EntityPlayer player, IResearch research, Knowledge knowledge)
    {
        super(player);

        this.research = research;
        this.knowledge = knowledge;
    }

    public static class Chance extends KnowledgeEvent
    {

        public int chance;

        public Chance(EntityPlayer entity, IResearch research, int chance)
        {
            super(entity, research, null);
            this.chance = chance;
        }

    }

    public static class Progress extends KnowledgeEvent
    {

        public final int current;
        public final int progress;
        public int modifier = 0;
        public final boolean researchFired;

        public Progress(EntityPlayer entity, IResearch research, int current, int progress, boolean researchFired)
        {
            super(entity, research, null);

            this.current = current;
            this.progress = progress;
            this.researchFired = researchFired;
        }

        @Cancelable
        public static class Pre extends Progress
        {

            public Pre(EntityPlayer entity, IResearch research, int current, int progress)
            {
                super(entity, research, current, progress, false);
            }

        }

        public static class Post extends Progress
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

    public static class Unlocked extends TechnomagiEvent
    {

        public final Knowledge knowledge;

        public Unlocked(EntityPlayer entity, Knowledge knowledge)
        {
            super(entity);
            this.knowledge = knowledge;
        }

    }

}
