package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntityNanites extends ItemEntityBase
{

    @SideOnly(Side.CLIENT)
    public IIcon[] itemIcons;

    public static int CAPACITY = 5;

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

        this.itemIcons = new IIcon[2];
        this.itemIcons[0] = register.registerIcon(getTexturePath("nanites/research"));
        this.itemIcons[1] = register.registerIcon(getTexturePath("nanites/targeted"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        return getEntity(stack).isEmpty() ? itemIcon : itemIcons[0];
    }

    @Override
    public boolean isEntityValid(ItemStack stack, Class entity)
    {
        return TechnomagiApi.entity().getNaniteEntities().contains(entity);
    }

    public boolean isEmpty(ItemStack stack)
    {
        return getEntity(stack).isEmpty();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack emptyStack = new ItemStack(item, 1, 0);
        List<Class> entities = TechnomagiApi.entity().getNaniteEntities();
        list.add(emptyStack);

        for (Class entity : entities) {
            IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entity);
            ItemStack copied = emptyStack.copy();
            setEntity(copied, entity);

            list.add(copied);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        return stack;
    }

}
