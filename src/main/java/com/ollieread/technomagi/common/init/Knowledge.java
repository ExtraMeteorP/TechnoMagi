package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.knowledge.KnowledgeCategoryBiology;
import com.ollieread.technomagi.knowledge.KnowledgeCategoryTechnology;

public class Knowledge
{

    public static void init()
    {
        TechnoMagi.info("Initiating & registering knowledge");

        new KnowledgeCategoryBiology("biology");
        new KnowledgeCategoryTechnology("technology");
    }
}
