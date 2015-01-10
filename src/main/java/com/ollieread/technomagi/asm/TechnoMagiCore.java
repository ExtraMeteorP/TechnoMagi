package com.ollieread.technomagi.asm;

import com.google.common.eventbus.EventBus;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;

public class TechnoMagiCore extends DummyModContainer
{

    public TechnoMagiCore()
    {
        super(new ModMetadata());

        ModMetadata meta = getMetadata();
        meta.modId = "TechnoMagiCore";
        meta.name = "TechnoMagiCore";
        meta.version = "@VERSION@";
    }

    @Override
    public boolean registerBus(EventBus bus, LoadController controller)
    {
        bus.register(this);
        return true;
    }
}
