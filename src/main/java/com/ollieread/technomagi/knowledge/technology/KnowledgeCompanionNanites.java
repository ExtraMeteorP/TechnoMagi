/**
 * 
 */
package com.ollieread.technomagi.knowledge.technology;

import com.ollieread.ennds.research.Knowledge;
import com.ollieread.technomagi.common.Reference;

/**
 * @author ollie
 *
 */
public class KnowledgeCompanionNanites extends Knowledge
{

    public KnowledgeCompanionNanites(String name, String[] knowledge, String category)
    {
        super(name, Reference.MODID.toLowerCase(), knowledge, category);
    }

    @Override
    public String[] getIntrigue()
    {
        return new String[0];
    }

}
