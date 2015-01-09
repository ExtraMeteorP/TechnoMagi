package com.ollieread.technomagi.research;

import com.ollieread.ennds.common.Reference;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchMonitoring;
import com.ollieread.ennds.research.Research;
import com.ollieread.ennds.research.ResearchRegistry;

public class ResearchMonitoring extends Research implements IResearchMonitoring
{

    protected String event;
    protected Class entity;
    protected int health;
    protected int repeatition;
    protected int chance;

    public ResearchMonitoring(String name, String knowledge, int progress, String event, Class entityClass, int health, int repeatition, int chance, String[] requirements)
    {
        this(name, knowledge, progress, Reference.MODID.toLowerCase(), event, entityClass, health, repeatition, chance, requirements);
    }

    public ResearchMonitoring(String name, String knowledge, int progress, String Modid, String event, Class entityClass, int health, int repeatition, int chance, String[] requirements)
    {
        super(name, knowledge, progress, Modid, requirements);

        this.event = event;
        this.entity = entityClass;
        this.health = health;
        this.repeatition = repeatition;
        this.chance = chance;

        ResearchRegistry.registerResearch(this);
    }

    @Override
    public boolean isRepeating()
    {
        return repeatition > 1;
    }

    @Override
    public int getMaxRepeatition()
    {
        return repeatition;
    }

    @Override
    public int getChance()
    {
        return chance;
    }

    @Override
    public String getMonitoringEvent()
    {
        return event;
    }

    @Override
    public boolean canPerform(ExtendedPlayerKnowledge charon)
    {
        return true;
    }

    @Override
    public Class getMonitoringEntity()
    {
        return entity;
    }

    @Override
    public int getModifiedHealth()
    {
        return health;
    }

}
