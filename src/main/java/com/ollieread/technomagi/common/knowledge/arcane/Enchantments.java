package com.ollieread.technomagi.common.knowledge.arcane;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Arcane;
import com.ollieread.technomagi.util.ResourceHelper;

public class Enchantments extends Knowledge
{

    public Enchantments()
    {
        super("enchantment", ResourceHelper.texture("knowledge/enchantment.png"), Arcane.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}