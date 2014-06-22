package com.ollieread.technomagi.api.research;

import com.ollieread.technomagi.player.PlayerKnowledge;

public interface IResearch
{

    public String getName();

    public String getKnowledge();

    public int getProgress();

    public int getEvent();

    public boolean isRepeating();

    public boolean canPerform(PlayerKnowledge charon);

    public String getLocalisedName();

}
