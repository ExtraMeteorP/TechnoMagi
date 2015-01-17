/**
 * 
 */
package com.ollieread.technomagi.knowledge.technology;

import net.minecraft.util.ResourceLocation;

import com.ollieread.ennds.research.Knowledge;

/**
 * @author ollie
 *
 */
public class KnowledgeNeutronics extends Knowledge
{

    /**
     * @param name
     * @param Modid
     * @param knowledge
     * @param category
     */
    public KnowledgeNeutronics(String name, String Modid, String[] knowledge, String category)
    {
        super(name, Modid, knowledge, category);
        // TODO Auto-generated constructor stub
    }

    /**
     * @param name
     * @param icon
     * @param knowledge
     * @param category
     */
    public KnowledgeNeutronics(String name, ResourceLocation icon, String[] knowledge, String category)
    {
        super(name, icon, knowledge, category);
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.ollieread.ennds.research.IKnowledge#getIntrigue()
     */
    @Override
    public String[] getIntrigue()
    {
        // TODO Auto-generated method stub
        return null;
    }

}
