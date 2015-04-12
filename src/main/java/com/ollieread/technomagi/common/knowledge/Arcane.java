package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.arcane.BasicArcane;
import com.ollieread.technomagi.common.knowledge.arcane.Brewing;
import com.ollieread.technomagi.common.knowledge.arcane.Enchantments;
import com.ollieread.technomagi.common.knowledge.arcane.Progenitors;
import com.ollieread.technomagi.util.ResourceHelper;

public class Arcane
{

    public static KnowledgeCategory category;

    public static BasicArcane basicArcane;
    public static Progenitors progenitors;
    public static Brewing brewing;
    public static Enchantments enchantment;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("arcane", ResourceHelper.texture("knowledge/arcane.png"));
        category.setLocation(-27, -15);

        basicArcane = new BasicArcane();
        basicArcane.setLocation(0, 0);

        progenitors = new Progenitors();
        progenitors.setLocation(27, 15);

        brewing = new Brewing();
        brewing.setLocation(-27, 15);

        enchantment = new Enchantments();
        enchantment.setLocation(0, -30);

        progenitors.addPrerequisite(basicArcane.getName());
        brewing.addPrerequisite(basicArcane.getName());
        enchantment.addPrerequisite(basicArcane.getName());
    }

    public static void mappings()
    {

    }

    public static void recipes()
    {

    }

}
