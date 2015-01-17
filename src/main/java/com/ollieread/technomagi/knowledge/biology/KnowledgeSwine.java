/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import com.ollieread.ennds.research.Knowledge;

/**
 * @author ollie
 *
 */
public class KnowledgeSwine extends Knowledge
{

    /**
     * @param name
     * @param Modid
     * @param knowledge
     * @param category
     */
    public KnowledgeSwine(String name, String Modid, String[] knowledge, String category)
    {
        super(name, Modid, knowledge, category);
    }

    @Override
    public String[] getIntrigue()
    {
        return null;
    }

}
