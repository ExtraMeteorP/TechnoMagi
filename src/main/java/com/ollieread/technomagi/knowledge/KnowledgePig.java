package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.research.IResearchCrafting;
import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.knowledge.research.ResearchCrafting;
import com.ollieread.technomagi.knowledge.research.ResearchEvent;

public class KnowledgePig extends Knowledge
{

    public static IResearchEvent breed;
    public static IResearchEvent kill;
    public static IResearchCrafting cook;
    public static IResearchEvent eat;

    public KnowledgePig(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        breed = new ResearchEvent("breedPig", getName(), 25, "breedPig", false, 2, null);
        kill = new ResearchEvent("killPig", getName(), 25, "killedPig", false, 2, null);
        cook = new ResearchCrafting("cookPig", getName(), 25, new ItemStack(Items.cooked_porkchop), false, 2, null);
        eat = new ResearchEvent("eatPig", getName(), 25, "eatPorkchop", false, 2, null);
    }
}
