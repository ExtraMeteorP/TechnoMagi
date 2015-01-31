package com.ollieread.technomagi.common.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.technomagi.api.helpers.EntityHelper;
import com.ollieread.technomagi.common.items.ItemBase;
import com.ollieread.technomagi.common.util.ItemStackNBT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemEntityBase extends ItemBase
{

    @SideOnly(Side.CLIENT)
    public IIcon itemOverlayIcon;

    public ItemEntityBase(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    public abstract boolean isEntityValid(ItemStack stack, EntityLivingBase entity);

    public boolean canSetEntity(ItemStack stack, EntityLivingBase entity)
    {
        if (ItemStackNBT.getString(stack, "Entity") != null) {
            return false;
        }

        return isEntityValid(stack, entity);
    }

    public void setEntity(ItemStack stack, EntityLivingBase entity)
    {
        ItemStackNBT.setString(stack, "Entity", EntityHelper.getEntityName(entity.getClass()));
    }

    public String getEntity(ItemStack stack)
    {
        return ItemStackNBT.getString(stack, "Entity");
    }

}
