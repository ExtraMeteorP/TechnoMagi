package com.ollieread.technomagi.knowledge;

import net.minecraft.init.Blocks;
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
    public static IResearchEvent useEgg;
    public static IResearchEvent useSnowball;
    public static IResearchCrafting craftArrow;
    public static IResearchCrafting craftDispenser;

    public KnowledgeProjectile(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        damageArrow = new ResearchEvent("damageArrow", getName(), 20, "damageArrow", false, 2, new String[] { "motion" });
        fireArrow = new ResearchEvent("fireArrow", getName(), 20, "fireArrow", false, 2, new String[] { "motion" });
        craftArrow = new ResearchCrafting("craftArrow", getName(), 20, new ItemStack(Items.arrow), false, 2, new String[] { "motion" });
        useEgg = new ResearchEvent("useEgg", getName(), 20, "useEgg", false, 2, new String[] { "motion" });
        useSnowball = new ResearchEvent("useSnowball", getName(), 20, "useSnowball", false, 2, new String[] { "motion" });
        craftDispenser = new ResearchCrafting("craftDispenser", getName(), 20, new ItemStack(Blocks.dispenser), false, 2, new String[] { "motion" });
    }
}
