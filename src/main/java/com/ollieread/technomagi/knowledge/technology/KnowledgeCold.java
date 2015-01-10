package com.ollieread.technomagi.knowledge.technology;

import net.minecraft.entity.projectile.EntitySnowball;

import com.ollieread.ennds.research.IResearchEvent;
import com.ollieread.ennds.research.IResearchMining;
import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.KnowledgeReference;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Research;
import com.ollieread.technomagi.research.ResearchEvent;
import com.ollieread.technomagi.research.ResearchMining;
import com.ollieread.technomagi.util.EventHelper;
import com.ollieread.technomagi.util.ItemHelper;

public class KnowledgeCold extends Knowledge
{

    public static IResearchMining snow;
    public static IResearchMining ice;
    public static IResearchEvent snowball;
    public static IResearchEvent snowballDamage;
    public static IResearchEvent obsidian;
    public static IResearchEvent cobblestone;

    public KnowledgeCold(String name, String[] knowledge)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, KnowledgeReference.CATEGORY_TECHNOLOGY);

        snow = new ResearchMining("snow", getName(), 10, ItemHelper.block("snow_layer"), 1, 4, new String[] {});
        ice = new ResearchMining("ice", getName(), 10, ItemHelper.block("ice"), 1, 4, new String[] {});
        snowball = new ResearchEvent("snowball", getName(), 4, EventHelper.itemUse(ItemHelper.item("snowball")), 5, 4, new String[] { snow.getName() });
        snowball = new ResearchEvent("snowballDamage", getName(), 4, EventHelper.damageProjectile(EntitySnowball.class), 5, 4, new String[] { snow.getName() });
        obsidian = new ResearchEvent("obsidian", getName(), 4, Research.OBSIDIAN, 5, 4, new String[] { snow.getName() });
        cobblestone = new ResearchEvent("cobblestone", getName(), 4, Research.COBBLESTONE, 5, 4, new String[] { snow.getName() });
    }
}
