package com.ollieread.technomagi.common.knowledge.organics;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Organics;
import com.ollieread.technomagi.util.ResourceHelper;

public class TechnorganicMaterials extends Knowledge
{

    public TechnorganicMaterials()
    {
        super("technorganic_materials", ResourceHelper.texture("knowledge/technorganic_materials.png"), Organics.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}