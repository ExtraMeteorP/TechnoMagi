package com.ollieread.technomagi.common.item.entity;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.technomagi.api.TechnomagiApi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntityNanites extends ItemEntityBase
{

    @SideOnly(Side.CLIENT)
    public IIcon[] itemOverlays;

    public ItemEntityNanites(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcon = register.registerIcon(getTexturePath("nanites"));

        this.itemOverlays = new IIcon[2];
        this.itemOverlays[0] = register.registerIcon(getTexturePath("nanites/research"));
        this.itemOverlays[1] = register.registerIcon(getTexturePath("nanites/targeted"));
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }*/

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        return itemIcon;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return pass == 0 ? getIconIndex(stack) : (stack.getItemDamage() > 0 ? this.itemOverlays[0] : null);
    }

    @Override
    public boolean isEntityValid(ItemStack stack, Class entity)
    {
        return TechnomagiApi.entity().getNaniteEntities().contains(entity);
    }

}
