package com.ollieread.technomagi.research;

import com.ollieread.ennds.common.Reference;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearchMonitoring;
import com.ollieread.ennds.research.Research;

public class ResearchMonitoring extends Research implements IResearchMonitoring
{

    protected String event;
    protected Class entity;
    protected int health;

    public ResearchMonitoring(String name, String knowledge, String event, Class entityClass, int health)
    {
        super(name, knowledge, Reference.MODID.toLowerCase());

        this.event = event;
        this.entity = entityClass;
        this.health = health;
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
