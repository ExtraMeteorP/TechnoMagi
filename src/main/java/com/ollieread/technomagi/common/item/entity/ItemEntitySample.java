package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntitySample extends ItemEntityBase
{

    @SideOnly(Side.CLIENT)
    public IIcon[] itemIcons;

    public ItemEntitySample(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        this.itemIcons = new IIcon[4];
        this.itemIcons[0] = register.registerIcon(getTexturePath("sample"));
        this.itemIcons[1] = register.registerIcon(getTexturePath("sample/normal"));
        this.itemIcons[2] = register.registerIcon(getTexturePath("sample/undead"));
        this.itemIcons[3] = register.registerIcon(getTexturePath("sample/monster"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        if (hasEntity(stack)) {
            if (isEntityUndead(stack)) {
                return itemIcons[2];
            } else if (isEntityMonster(stack)) {
                return itemIcons[3];
            } else {
                return itemIcons[1];
            }
        }

        return itemIcons[0];
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return getIconIndex(stack);
    }

    @Override
    public boolean isEntityValid(ItemStack stack, Class entity)
    {
        return TechnomagiApi.entity().getSampleableEntities().contains(entity);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack emptyStack = new ItemStack(item, 1, 0);
        List<Class> entities = TechnomagiApi.entity().getSampleableEntities();
        list.add(emptyStack);

        for (Class entity : entities) {
            IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entity);
            ItemStack copied = emptyStack.copy();
            setEntity(copied, entity);

            list.add(copied);
        }
    }

}
