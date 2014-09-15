package com.ollieread.technomagi.research;

import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.research.crafting.ResearchCraftingFlintAndSteel;
import com.ollieread.technomagi.research.crafting.ResearchCraftingTorch;
import com.ollieread.technomagi.research.event.ResearchEventDamageInFire;
import com.ollieread.technomagi.research.event.ResearchEventDamageLava;
import com.ollieread.technomagi.research.event.ResearchEventDamageOnFire;
import com.ollieread.technomagi.research.event.ResearchEventUseFlintAndSteel;

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

        craftingTorch = new ResearchCraftingTorch("craftingTorch", this.getName(), 15);
        craftingFlintAndSteel = new ResearchCraftingFlintAndSteel("craftingFlintAndSteel", this.getName(), 15);
        useFlintAndSteel = new ResearchEventUseFlintAndSteel("useFlintAndSteel", this.getName(), 15);
        damageInFire = new ResearchEventDamageInFire("damageInFire", this.getName(), 15);
        damageOnFire = new ResearchEventDamageOnFire("damageOnFire", this.getName(), 20);
        damageInLava = new ResearchEventDamageLava("damageInLava", this.getName(), 20);
    }

}
