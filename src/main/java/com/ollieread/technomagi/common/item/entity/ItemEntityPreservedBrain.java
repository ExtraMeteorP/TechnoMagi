package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntityPreservedBrain extends ItemEntityBrain
{
    @SideOnly(Side.CLIENT)
    public IIcon itemOverlayIcon;

    public ItemEntityPreservedBrain(String name)
    {
        super(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        itemOverlayIcon = register.registerIcon(getTexturePath("brain/preserved"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return pass == 0 ? getIconIndex(stack) : itemOverlayIcon;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack emptyStack = new ItemStack(item, 1, 0);
        List<Class> entities = TechnomagiApi.entity().getBrainableEntities();

        for (Class entity : entities) {
            IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entity);
            ItemStack copied = emptyStack.copy();
            int maxLife = ((IEntityBrain) descriptor).getBrainMaxLife() * ((IEntityBrain) descriptor).getBrainPreservedMultiplier();

            setEntity(copied, entity);
            setMaxLife(copied, maxLife);
            setLife(copied, maxLife);

            list.add(copied);
        }
    }

}
