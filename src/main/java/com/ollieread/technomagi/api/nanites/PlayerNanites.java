package com.ollieread.technomagi.api.nanites;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.research.IResearch;

public class PlayerNanites extends EntityNanites
{

    protected PlayerTechnomagi technomagi;

    public PlayerNanites(PlayerTechnomagi technomagi)
    {
        super(true);
        this.technomagi = technomagi;
    }

    @Override
    public boolean canResearch(IResearch research)
    {
        if (technomagi.knowledge().canDiscover(TechnomagiApi.getKnowledge(research.getKnowledge())) && !researchComplete.contains(research.getName())) {
            return true;
        }

        return false;
    }

}
