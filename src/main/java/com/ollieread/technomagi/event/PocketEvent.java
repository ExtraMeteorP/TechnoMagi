package com.ollieread.technomagi.event;

import com.ollieread.technomagi.tileentity.ITileEntityPocket;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class PocketEvent extends Event
{

    protected final ITileEntityPocket pocket;

    public PocketEvent(ITileEntityPocket pocket)
    {
        this.pocket = pocket;
    }

    public ITileEntityPocket getPocket()
    {
        return pocket;
    }

}
