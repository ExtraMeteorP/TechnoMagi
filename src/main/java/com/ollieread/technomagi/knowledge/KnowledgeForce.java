package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeForce extends Knowledge
{

    public static IResearchEvent gravity;
    public static IResearchCrafting piston;
    public static IResearchEvent block;
    public static IResearchEvent explosion;
    public static IResearchEvent splat;
    public static IResearchEvent anvil;

    public KnowledgeForce(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        gravity = new ResearchEvent("forceGravity", getName(), 25, "damageFall", false, 2, null);
        piston = new ResearchCrafting("forceCraftPiston", getName(), 25, new ItemStack(Blocks.piston), false, 2, null);
        block = new ResearchEvent("forceFallingBlock", getName(), 25, "damageFallingBlock", false, 2, null);
        explosion = new ResearchEvent("forceExplosion", getName(), 25, "damageExplosion", false, 2, null);
        splat = new ResearchEvent("forceSplat", getName(), 50, "killedByGravity", false, 1, null);
        anvil = new ResearchEvent("forceAnvil", getName(), 25, "damageAnvil", false, 2, null);
    }

}
