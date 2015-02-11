package com.ollieread.technomagi.api.knowledge.research;


public interface IResearcher
{
    
    public boolean canResearch(IResearch research);

    public int addResearch(IResearch research, int modifier);
    
}
