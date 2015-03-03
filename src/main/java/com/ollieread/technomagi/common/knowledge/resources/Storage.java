package com.ollieread.technomagi.common.knowledge.resources;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.common.knowledge.Resources;
import com.ollieread.technomagi.util.ResourceHelper;

public class Storage extends Knowledge
{

    public Storage()
    {
        super("storage", ResourceHelper.texture("knowledge/storage.png"), Resources.category.getName());

        TechnomagiApi.addKnowledge(this);
    }

    public void mappings()
    {
    }

}