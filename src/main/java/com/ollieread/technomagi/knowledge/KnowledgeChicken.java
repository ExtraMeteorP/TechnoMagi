package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeChicken extends Knowledge
{

    public static IResearchEvent breed;
    public static IResearchEvent kill;
    public static IResearchCrafting cook;
    public static IResearchEvent eat;

    public KnowledgeChicken(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        breed = new ResearchEvent("breedChicken", getName(), 25, "breedingChicken", false, 2, null);
        kill = new ResearchEvent("killChicken", getName(), 25, "killedChicken", false, 2, null);
        cook = new ResearchCrafting("cookChicken", getName(), 25, new ItemStack(Items.cooked_chicken), false, 2, null);
        eat = new ResearchEvent("eatChicken", getName(), 25, "eatChicken", false, 2, null);
    }
}
