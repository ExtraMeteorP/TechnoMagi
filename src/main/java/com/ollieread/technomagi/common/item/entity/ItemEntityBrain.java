package com.ollieread.technomagi.common.item.entity;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StringUtils;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;
import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntityBrain extends ItemEntityBase
{

    @SideOnly(Side.CLIENT)
    public IIcon[] itemIcons;

    public ItemEntityBrain(String name)
    {
        super(name);

        this.setHasSubtypes(true);
        this.setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcons = new IIcon[3];
        itemIcons[0] = register.registerIcon(getTexturePath("brain"));
        itemIcons[1] = register.registerIcon(getTexturePath("brain/dead"));
        itemIcons[2] = register.registerIcon(getTexturePath("brain/monster"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        if (isEntityUndead(stack)) {
            return itemIcons[1];
        }

        if (isEntityMonster(stack)) {
            return itemIcons[2];
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
        return TechnomagiApi.entity().getBrainableEntities().contains(entity);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {
        if (world.isRemote) {
            return;
        }

        int life = getLife(stack);
        life--;

        if (life <= 0) {
            stack.stackSize--;
            return;
        }

        setLife(stack, life);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return 1 - (1D / getMaxLife(stack)) * getLife(stack);
    }

    protected int getLife(ItemStack stack)
    {
        NBTTagCompound compound = ItemNBTHelper.getNBT(stack);

        if (compound.hasKey("Life")) {
            return compound.getInteger("Life");
        }

        return 0;
    }

    public void setLife(ItemStack stack, int ticks)
    {
        ItemNBTHelper.setInteger(stack, "Life", ticks);
    }

    protected int getMaxLife(ItemStack stack)
    {
        return ItemNBTHelper.getInteger(stack, "MaxLife");
    }

    public void setMaxLife(ItemStack stack, int ticks)
    {
        ItemNBTHelper.setInteger(stack, "MaxLife", ticks);
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
            int maxLife = ((IEntityBrain) descriptor).getBrainMaxLife();

            setEntity(copied, entity);
            setMaxLife(copied, maxLife);
            setLife(copied, maxLife);

            list.add(copied);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        super.addInformation(stack, player, info, advanced);

        info.add(StringUtils.ticksToElapsedTime(getLife(stack)));
    }

}
