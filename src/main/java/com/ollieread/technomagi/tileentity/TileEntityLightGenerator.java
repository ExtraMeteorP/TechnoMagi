package com.ollieread.technomagi.tileentity;

public class TileEntityLightGenerator extends TileEntityTM
{

    protected boolean enabled = false;

    public void toggle()
    {
        if (enabled) {
            disable();
        } else {
            enable();
        }
    }

    private void enable()
    {

    }

    private void disable()
    {

    }

}
