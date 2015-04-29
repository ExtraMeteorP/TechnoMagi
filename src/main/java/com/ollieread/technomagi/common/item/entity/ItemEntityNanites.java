package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.EntityTechnomagi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.nanites.PlayerNanites;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.PlayerHelper;

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
        if (!player.worldObj.isRemote) {
            if (PlayerHelper.hasSpecialised(player)) {
                PlayerNanites nanites = PlayerHelper.getNanites(player);

                if (isEmpty(stack)) {
                    if (nanites.decreaseNanites(10)) {
                        stack.stackSize--;
                        ItemStack newStack = new ItemStack(this, 1, 0);
                        setEntity(newStack, EntityPlayer.class);
                        PlayerHelper.giveInventoryItem(player, newStack);
                    }
                } else {
                    if (getEntity(stack).equals("player") && nanites.increaseNanites(10)) {
                        stack.stackSize--;
                    }
                }
            }
        }

        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if (!player.worldObj.isRemote) {
            if (!isEmpty(stack)) {
                if (!(entity instanceof EntityPlayer)) {
                    if (getEntity(stack).equals(EntityHelper.getEntityName(entity.getClass()))) {

                        EntityTechnomagi technomagiEntity = EntityHelper.getTechnomagi(entity);

                        if (technomagiEntity != null && technomagiEntity.nanites() != null) {
                            if (technomagiEntity.nanites().increaseNanites(10)) {
                                if (!technomagiEntity.nanites().getActive()) {
                                    technomagiEntity.nanites().setActive(true);
                                }

                                return true;
                            }
                        }
                    }
                } else if (getEntity(stack).equals("player")) {
                    if (PlayerHelper.hasSpecialised((EntityPlayer) entity)) {
                        PlayerNanites nanites = PlayerHelper.getNanites((EntityPlayer) entity);

                        if (nanites != null && nanites.increaseNanites(10)) {
                            return true;
                        }
                    }
                }
            } else if (!(entity instanceof EntityPlayer)) {
                EntityTechnomagi technomagiEntity = EntityHelper.getTechnomagi(entity);

                if (technomagiEntity != null && technomagiEntity.nanites() != null) {
                    if (technomagiEntity.nanites().decreaseNanites(10)) {
                        stack.stackSize--;
                        ItemStack newStack = new ItemStack(this, 1, 0);
                        setEntity(newStack, entity);
                        PlayerHelper.giveInventoryItem(player, newStack);

                        return true;
                    }
                }
            }
        }

        return false;
    }

}
