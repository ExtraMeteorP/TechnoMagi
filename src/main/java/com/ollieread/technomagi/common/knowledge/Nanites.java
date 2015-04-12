package com.ollieread.technomagi.common.knowledge;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.KnowledgeCategory;
import com.ollieread.technomagi.common.knowledge.nanites.BasicNanites;
import com.ollieread.technomagi.common.knowledge.nanites.Mimcry;
import com.ollieread.technomagi.common.knowledge.nanites.Robotization;
import com.ollieread.technomagi.common.knowledge.nanites.TargetedNanites;
import com.ollieread.technomagi.util.ResourceHelper;

public class Nanites
{

    public static KnowledgeCategory category;

    public static BasicNanites basicNanites;
    public static Mimcry mimcry;
    public static Robotization robotization;
    public static TargetedNanites targetedNanites;

    public static void knowledge()
    {
        category = TechnomagiApi.addKnowledgeCategory("nanites", ResourceHelper.texture("knowledge/nanites.png"));
        category.setLocation(0, -30);

        basicNanites = new BasicNanites();
        basicNanites.setLocation(0, 0);

        mimcry = new Mimcry();
        mimcry.setLocation(0, -30);

        robotization = new Robotization();
        robotization.setLocation(-27, 15);

        targetedNanites = new TargetedNanites();
        targetedNanites.setLocation(27, 15);

        mimcry.addPrerequisite(basicNanites.getName());
        robotization.addPrerequisite(basicNanites.getName());
        targetedNanites.addPrerequisite(basicNanites.getName());
    }

    public static void mappings()
    {

    }

    public static void recipes()
    {

    }

}
