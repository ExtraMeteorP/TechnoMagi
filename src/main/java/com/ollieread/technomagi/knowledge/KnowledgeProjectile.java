package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeProjectile extends Knowledge
{

    public static IResearchEvent damageArrow;
    public static IResearchEvent fireArrow;
    public static IResearchCrafting cook;
    public static IResearchEvent eat;

    public KnowledgeProjectile(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        damageArrow = new ResearchEvent("damageArrow", getName(), 25, "damageArrow", false, 2, null);
        fireArrow = new ResearchEvent("fireArrow", getName(), 25, "fireArrow", false, 2, null);
        cook = new ResearchCrafting("cookChicken", getName(), 25, new ItemStack(Items.cooked_chicken), false, 2, null);
        eat = new ResearchEvent("eatChicken", getName(), 25, "eatChicken", false, 2, null);
    }

}
