package com.ollieread.technomagi.item;

import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.ennds.item.IStaffEnhancement;

import cpw.mods.fml.common.eventhandler.Event;

public class ItemUnit extends ItemTMSubtypes implements IStaffEnhancement
{

    public ItemUnit(String name)
    {
        super(name, new String[] { "lifeUnitBasic", "lifeUnitAdvanced", "lifeUnitElite", "teleportUnitBasic", "teleportUnitAdvanced", "teleportUnitElite", "subspaceUnitBasic", "subspaceUnitAdvanced", "subspaceUnitElite", "naniteUnitBasic", "naniteUnitAdvanced", "naniteUnitElite", "powerUnitBasic", "powerUnitAdvanced", "powerUnitElite", "exoUnitBasic", "exoUnitAdvanced", "exoUnitElite", "endoUnitBasic", "endoUnitAdvanced", "endoUnitElite", "forceUnitBasic", "forceUnitAdvanced", "forceUnitElite", "voidUnitBasic", "voidUnitAdvanced", "voidUnitElite" });
    }

    @Override
    public String getName(ItemStack enhancement)
    {
        if (enhancement != null) {
            switch (enhancement.getItemDamage()) {
                case 0:
                    return "lifeI";
                case 1:
                    return "lifeII";
                case 2:
                    return "lifeIII";
                case 3:
                    return "teleportI";
                case 4:
                    return "teleportII";
                case 5:
                    return "teleportIII";
                case 6:
                    return "subspaceI";
                case 7:
                    return "subspaceII";
                case 8:
                    return "subspaceIII";
                case 9:
                    return "naniteI";
                case 10:
                    return "naniteII";
                case 11:
                    return "naniteIII";
                case 12:
                    return "powerI";
                case 13:
                    return "powerII";
                case 14:
                    return "powerIII";
                case 15:
                    return "exoI";
                case 16:
                    return "exoII";
                case 17:
                    return "exoIII";
                case 18:
                    return "endoI";
                case 19:
                    return "endoII";
                case 20:
                    return "endoIII";
                case 21:
                    return "forceI";
                case 22:
                    return "forceII";
                case 23:
                    return "forceIII";
                case 24:
                    return "voidI";
                case 25:
                    return "voidII";
                case 26:
                    return "voidIII";
            }
        }

        return null;
    }

    @Override
    public boolean canEnhance(ExtendedPlayerKnowledge charon, Event event, ItemStack staff, ItemStack enhancement)
    {
        if (staff != null && staff.getItem() != null && staff.getItem() instanceof IStaff && staff.getItemDamage() == 1) {
            IStaff istaff = (IStaff) staff.getItem();

            return istaff.hasEnchancement(staff, ((IStaffEnhancement) enhancement.getItem()).getName(enhancement));
        }

        return false;
    }

}
