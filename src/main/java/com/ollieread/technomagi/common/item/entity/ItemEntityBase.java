package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.common.item.ItemBase;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;
import com.ollieread.technomagi.util.EntityHelper;
import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class ItemEntityBase extends ItemBase
{

    public ItemEntityBase(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
        this.setCreativeTab(TechnomagiTabs.entity);
    }

    public boolean isEntityValid(ItemStack stack, EntityLivingBase entity)
    {
        return isEntityValid(stack, entity.getClass());
    }

    public abstract boolean isEntityValid(ItemStack stack, Class entity);

    public boolean canSetEntity(ItemStack stack, EntityLivingBase entity)
    {
        if (ItemNBTHelper.getString(stack, "Entity") != null) {
            return false;
        }

        return isEntityValid(stack, entity);
    }

    public void setEntity(ItemStack stack, EntityLivingBase entity)
    {
        setEntity(stack, entity.getClass());

        if (entity instanceof EntityPlayer) {
            setPlayer(stack, (EntityPlayer) entity);
        }
    }

    public void setEntity(ItemStack stack, Class entity)
    {
        if (entity.equals(EntityPlayer.class)) {
            ItemNBTHelper.setString(stack, "Entity", "player");
        } else {
            String name = EntityHelper.getEntityName(entity);

            if (name != null) {
                ItemNBTHelper.setString(stack, "Entity", name);
            }
        }
    }

    public void setPlayer(ItemStack stack, EntityPlayer player)
    {
        ItemNBTHelper.setString(stack, "Player", player.getCommandSenderName());
    }

    public String getPlayer(ItemStack stack)
    {
        return ItemNBTHelper.getString(stack, "Player");
    }

    public String getEntity(ItemStack stack)
    {
        return ItemNBTHelper.getString(stack, "Entity");
    }

    public boolean hasEntity(ItemStack stack)
    {
        return getEntity(stack) != null && !getEntity(stack).isEmpty();
    }

    public boolean isEntityUndead(ItemStack stack)
    {
        String entityName = getEntity(stack);

        if (entityName != null && !entityName.isEmpty()) {
            IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entityName);

            if (descriptor == null) {
                Technomagi.debug("Entity: " + entityName);
            }

            return descriptor.isUndead();
        }

        return false;
    }

    public boolean isEntityMonster(ItemStack stack)
    {
        String entityName = getEntity(stack);

        if (entityName != null && !entityName.isEmpty()) {
            IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entityName);

            return descriptor.isMonster();
        }

        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        String entityName = getEntity(stack);

        if (entityName != null && !entityName.isEmpty()) {
            IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entityName);

            if (descriptor != null && isEntityValid(stack, descriptor.getEntityClass())) {
                String line = "";

                if (descriptor.isUndead()) {
                    line += EnumChatFormatting.DARK_GREEN;
                } else if (descriptor.isMonster()) {
                    line += EnumChatFormatting.DARK_RED;
                }

                line += StatCollector.translateToLocal("entity." + entityName + ".name");

                info.add(line);
            }
        }
    }

}
