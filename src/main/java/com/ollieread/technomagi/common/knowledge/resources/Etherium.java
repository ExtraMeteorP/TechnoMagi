package com.ollieread.technomagi.common.knowledge.resources;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.Research;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.ResourceHelper;

public class Etherium extends Knowledge
{

    public static Research mineEtherium;
    public static Research pickupEtherium;
    public static Research etheriumExposed;

    public Etherium()
    {
        super("etherium", ResourceHelper.texture("knowledge/etherium.png"), Resources.category.getName());

        TechnomagiApi.addKnowledge(this);

        mineEtherium = TechnomagiApi.addResearch("mine_etherium", getName()).setProgress(4).setChance(11).setRepetition(10);
        pickupEtherium = TechnomagiApi.addResearch("pickup_etherium", getName()).setProgress(4).setChance(3).setRepetition(10);
        etheriumExposed = TechnomagiApi.addResearch("etherium_exposed", getName()).setProgress(10).setChance(8).setRepetition(2);
    }

    public void mappings()
    {
        TechnomagiApi.knowledge().mapMiningResearch(Blocks.resource, 0, mineEtherium.getName());
        TechnomagiApi.knowledge().mapPickupResearch(ItemStackHelper.itemSubtype(Items.crystal, "etherium", 1), mineEtherium.getName());
    }

}