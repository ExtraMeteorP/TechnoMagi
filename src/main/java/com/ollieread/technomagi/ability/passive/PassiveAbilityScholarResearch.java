package com.ollieread.technomagi.ability.passive;

import com.ollieread.ennds.ability.AbilityPassive;
import com.ollieread.ennds.event.EnndsEvent.ResearchProgressEvent;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Specialisations;

public class PassiveAbilityScholarResearch extends AbilityPassive<ResearchProgressEvent>
{

    public PassiveAbilityScholarResearch(String name)
    {
        super(name, Reference.MODID.toLowerCase());
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

        // event.modifier = (int) Math.round(modifier);
    }

}
