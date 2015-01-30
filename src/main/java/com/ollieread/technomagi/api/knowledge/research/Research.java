package com.ollieread.technomagi.api.knowledge.research;

import com.ollieread.technomagi.api.TechnoMagiApi;

public class Research implements IResearch
{

    protected static String name;
    protected static String knowledge;
    protected int chance;
    protected int progress;
    protected int repeatition = 1;

    public Research(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public Research setChance(int chance)
    {
        this.chance = chance;
        return this;
    }

    @Override
    public int getChance()
    {
        return chance;
    }

    public Research setKnowledge(String knowledge)
    {
        this.knowledge = knowledge;
        return this;
    }

    @Override
    public String getKnowledge()
    {
        return knowledge;
    }

    public Research setProgress(int progress)
    {
        this.progress = progress;
        return this;
    }

    @Override
    public int getProgress()
    {
        return progress;
    }

    @Override
    public boolean isRepeating()
    {
        return repeatition > 1;
    }

    public Research setRepeatition(int repeatition)
    {
        this.repeatition = repeatition;
        return this;
    }

    @Override
    public int getRepeatition()
    {
        return repeatition;
    }

    @Override
    public String getUnlocalisedName()
    {
        return TechnoMagiApi.PREFIX_RESEARCH + name;
    }

}
