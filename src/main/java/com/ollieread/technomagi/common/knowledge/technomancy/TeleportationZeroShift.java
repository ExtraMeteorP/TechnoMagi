package com.ollieread.technomagi.common.knowledge.technomancy;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Technomancy;
import com.ollieread.technomagi.util.ResourceHelper;

public class TeleportationZeroShift extends Knowledge
{

    public TeleportationZeroShift()
    {
        super("teleportation_zero_shift", ResourceHelper.texture("knowledge/teleportation_zero_shift.png"), Technomancy.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

}
