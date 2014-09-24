package com.ollieread.technomagi.knowledge;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeForce extends Knowledge
{

    public static IResearchEvent gravity;
    public static IResearchEvent anvil;
    public static IResearchEvent block;
    public static IResearchEvent explosion;
    public static IResearchEvent splat;

    public KnowledgeForce(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        gravity = new ResearchEvent("forceGravity", getName(), 25, "damageFall", false, 2, null);
        anvil = new ResearchEvent("forceAnvil", getName(), 25, "damageAnvil", false, 2, null);
        block = new ResearchEvent("forceFallingBlock", getName(), 25, "damageFallingBlock", false, 2, null);
        explosion = new ResearchEvent("forceExplosion", getName(), 25, "damageExplosion", false, 2, null);
        anvil = new ResearchEvent("forceSplat", getName(), 50, "killedByGravity", false, 1, null);
    }

}
