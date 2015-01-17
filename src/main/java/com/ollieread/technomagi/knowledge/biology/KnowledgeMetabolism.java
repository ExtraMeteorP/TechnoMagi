/**
 * 
 */
package com.ollieread.technomagi.knowledge.biology;

import com.ollieread.ennds.research.Knowledge;

/**
 * @author ollie
 *
 */
public class KnowledgeMetabolism extends Knowledge
{

    /**
     * @param name
     * @param Modid
     * @param knowledge
     * @param category
     */
    public KnowledgeMetabolism(String name, String Modid, String[] knowledge, String category)
    {
        super(name, Modid, knowledge, category);
    }

    @Override
    public String[] getIntrigue()
    {
        return null;
    }

}
