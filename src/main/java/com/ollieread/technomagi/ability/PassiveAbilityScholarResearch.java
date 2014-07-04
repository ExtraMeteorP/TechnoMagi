package com.ollieread.technomagi.ability;

import com.ollieread.technomagi.api.ability.AbilityPassive;
import com.ollieread.technomagi.api.event.TMEvent.ResearchProgressEvent;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

public class PassiveAbilityScholarResearch extends AbilityPassive<ResearchProgressEvent>
{

    public PassiveAbilityScholarResearch(String name)
    {
        super(name);
    }

    @Override
    public String getEvent()
    {
        return "researchProgress";
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.isSpecialisation(Specialisations.specScholar.getName());
    }

    @Override
    public void use(ResearchProgressEvent event, ExtendedPlayerKnowledge charon)
    {
        int progress = event.progress;
        int current = event.current;
        int modifier = 0;
        int divide = 1;

        if (progress < 100) {
            if (current > 50) {
                divide = 2;
            }
            if (progress < 10) {
                modifier = (int) (progress * (0.35 / divide));
            } else if (progress < 50) {
                modifier = (int) (progress * (0.25 / divide));
            } else if (progress < 100) {
                modifier = (int) (progress * (0.10 / divide));
            }
        }

        event.modifier = (int) Math.round(modifier);
    }

}
