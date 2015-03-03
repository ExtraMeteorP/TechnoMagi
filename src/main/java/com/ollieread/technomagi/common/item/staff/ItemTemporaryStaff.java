package com.ollieread.technomagi.common.item.staff;

import com.ollieread.technomagi.common.item.ItemSubtypes;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTemporaryStaff extends ItemSubtypes
{

    public ItemTemporaryStaff(String name, String[] names)
    {
        super(name, names);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

}
