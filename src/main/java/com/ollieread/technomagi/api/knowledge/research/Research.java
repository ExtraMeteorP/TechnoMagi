package com.ollieread.technomagi.api.knowledge.research;

import com.ollieread.technomagi.api.TechnomagiApi;

public class Research implements IResearch
{

    protected String name;
    protected String knowledge;
    protected String parent;
    protected int chance = 1;
    protected int progress;
    protected int repeatition = 1;

    public Research(String name)
    {
        this.name = name;
    }

    @Override
    public String getName()
    {
        return knowledge + "." + name;
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

    public Research setRepetition(int repeatition)
    {
        this.repeatition = repeatition;
        return this;
    }

    @Override
    public int getRepetition()
    {
        return repeatition;
    }

    @Override
    public String getUnlocalisedName()
    {
        return TechnomagiApi.PREFIX_RESEARCH + name;
    }

    public Research setParent(String parent)
    {
        this.parent = parent;
        return this;
    }

    @Override
    public String getParent()
    {
        return parent;
    }

}
