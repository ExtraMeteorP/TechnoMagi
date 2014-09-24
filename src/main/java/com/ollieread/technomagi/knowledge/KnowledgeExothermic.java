package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeExothermic extends Knowledge
{

    public static IResearchCrafting craftingTorch;
    public static IResearchCrafting craftingFlintAndSteel;
    public static IResearch useFlintAndSteel;
    public static IResearch damageInFire;
    public static IResearch damageOnFire;
    public static IResearch damageInLava;

    public KnowledgeExothermic(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        craftingTorch = new ResearchCrafting("craftingTorch", this.getName(), 15, new ItemStack(Blocks.torch), false, 2, null);
        craftingFlintAndSteel = new ResearchCrafting("craftingFlintAndSteel", this.getName(), 15, new ItemStack(Blocks.torch), false, 2, null);
        useFlintAndSteel = new ResearchEvent("useFlintAndSteel", this.getName(), 15, "useFlintAndSteel", false, 2, null);
        damageInFire = new ResearchEvent("damageInFire", this.getName(), 15, "damageInFire", false, 2, null);
        damageOnFire = new ResearchEvent("damageOnFire", this.getName(), 20, "damageOnFire", false, 1, null);
        damageInLava = new ResearchEvent("damageInLava", this.getName(), 20, "damageLava", false, 2, null);
    }

}
