package com.ollieread.technomagi.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.item.IStaff;
import com.ollieread.ennds.item.IStaffEnhancement;
import com.ollieread.technomagi.TechnoMagi;

import cpw.mods.fml.common.eventhandler.Event;

public class ItemUnit extends ItemTMSubtypes implements IStaffEnhancement
{

    public String[] itemTypes = new String[] { "life", "light", "teleport", "subspace", "nanite", "power", "exo", "endo", "force", "void" };

    public ItemUnit(String name)
    {
        super(name, new String[] { "lifeUnitBasic", "lifeUnitAdvanced", "lifeUnitElite", "lightUnitBasic", "lightUnitAdvanced", "lightUnitElite", "teleportUnitBasic", "teleportUnitAdvanced", "teleportUnitElite", "subspaceUnitBasic", "subspaceUnitAdvanced", "subspaceUnitElite", "naniteUnitBasic", "naniteUnitAdvanced", "naniteUnitElite", "powerUnitBasic", "powerUnitAdvanced", "powerUnitElite", "exoUnitBasic", "exoUnitAdvanced", "exoUnitElite", "endoUnitBasic", "endoUnitAdvanced", "endoUnitElite", "forceUnitBasic", "forceUnitAdvanced", "forceUnitElite", "voidUnitBasic", "voidUnitAdvanced", "voidUnitElite" });

        setCreativeTab(TechnoMagi.tabTMUnit);
    }

    @Override
    public String getName(ItemStack enhancement)
    {
        if (enhancement != null) {
            return itemTypes[enhancement.getItemDamage() / 3];
        }

        return null;
    }

    @Override
    public int getLevel(ItemStack enhancement)
    {
        if (enhancement != null) {
            return (enhancement.getItemDamage() % 3) + 1;
        }

        return 0;
    }

    @Override
    public boolean canEnhance(ExtendedPlayerKnowledge charon, Event event, ItemStack staff, ItemStack enhancement)
    {
        if (staff != null && staff.getItem() != null && staff.getItem() instanceof IStaff && staff.getItemDamage() == 1) {
            IStaff istaff = (IStaff) staff.getItem();
            IStaffEnhancement istaffenhancement = (IStaffEnhancement) enhancement.getItem();

            return istaff.hasEnhancement(staff, istaffenhancement.getName(enhancement), istaffenhancement.getLevel(enhancement));
        }

        return false;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

        if (charon != null && !charon.canSpecialise()) {
            ItemStack staff = charon.getStaff(false);

            if (staff != null) {
                Item actualStaff = staff.getItem();

                if (actualStaff instanceof IStaff) {
                    IStaff realStaff = (IStaff) actualStaff;

                    if (!realStaff.hasEnhancement(staff, getName(stack), getLevel(stack))) {
                        realStaff.setEnhancement(staff, getName(stack), getLevel(stack));
                        stack.stackSize--;

                        charon.getStaff(true);
                        charon.setStaff(staff);
                    }
                }
            }
        }

        return stack;
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float par8, float par9, float par10)
    {
        stack.stackSize--;

        if (stack.stackSize == 0) {
            stack = null;
        }

        return true;
    }

}
