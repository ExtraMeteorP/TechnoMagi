package com.ollieread.technomagi.api.knowledge.research;

/**
 * This interface is used to register a piece of research with the system.
 * Unless you're doing anything particularly complicated, {@link Research}
 * should be used.
 * 
 * How and when the research is called is entirely up to the implementation,
 * this simply provides the basic information required for adding the research
 * data to the required knowledge topic.
 * 
 * @author ollieread
 *
 */
public interface IResearch
{

    /**
     * The name to identify this research.
     * 
     * @return
     */
    public String getName();

    /**
     * The chance of this research succeeding.
     * 
     * @return
     */
    public int getChance();

    /**
     * The knowledge this research is for.
     * 
     * @return
     */
    public String getKnowledge();

    /**
     * The progress to apply to the knowledge topic.
     * 
     * @return
     */
    public int getProgress();

    /**
     * Whether or not this research is repeating.
     * 
     * @return
     */
    public boolean isRepeating();

    /**
     * The amount of times the research can be repeated.
     * 
     * @return
     */
    public int getRepetition();

    /**
     * The unlocalised name, for translation.
     * 
     * @return
     */
    public String getUnlocalisedName();

}
