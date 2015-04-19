package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class TeleportationSubstitution extends Knowledge
{

    public TeleportationSubstitution()
    {
        super("teleportation_substitution", ResourceHelper.texture("knowledge/teleportation_substitution.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public static void mappings()
    {

    }

}
