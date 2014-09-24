package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgeCow extends Knowledge
{

    public static IResearchEvent breed;
    public static IResearchEvent kill;
    public static IResearchCrafting cook;
    public static IResearchEvent eat;

    public KnowledgeCow(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        breed = new ResearchEvent("breedCow", getName(), 25, "breedCow", false, 2, null);
        kill = new ResearchEvent("killCow", getName(), 25, "killedCow", false, 2, null);
        cook = new ResearchCrafting("cookCow", getName(), 25, new ItemStack(Items.cooked_beef), false, 2, null);
        eat = new ResearchEvent("eatCow", getName(), 25, "eatBeef", false, 2, null);
    }
}
