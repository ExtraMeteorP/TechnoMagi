package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class TeleportationPhaseJumping extends Knowledge
{

    public TeleportationPhaseJumping()
    {
        super("teleportation_phase_jumping", ResourceHelper.texture("knowledge/teleportation_phase_jumping.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public static void mappings()
    {

    }

}
