package com.ollieread.technomagi.api.knowledge.research;

import java.util.List;
import java.util.Map;

public interface IResearcher
{

    public boolean canResearch(IResearch research);

    public int addResearch(IResearch research, int modifier);

    public void copyFrom(IResearcher research);

    public List<String> getCompleteResearch();

    public Map<String, Integer> getResearchRepetition();

}
