package com.ollieread.technomagi.api.research;

import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

public interface IResearch
{

    public String getName();

    public String getKnowledge();

    public int getProgress();

    public boolean isRepeating();

    public String getLocalisedName();

    public boolean canPerform(ExtendedPlayerKnowledge charon);

}
