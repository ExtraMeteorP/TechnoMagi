package com.ollieread.technomagi.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;
import com.ollieread.technomagi.api.crafting.IProcessorComponent;
import com.ollieread.technomagi.util.ItemNBTHelper;

public class ItemProcessorComponent extends ItemSubtypes implements IProcessorComponent
{

    public ItemProcessorComponent(String name)
    {
        super(name, new String[] { "iron_grinding", "diamond_grinding", "iron_saw", "diamond_saw" });

        this.setMaxStackSize(1);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
        stack.stackTagCompound = new NBTTagCompound();
    }

    @Override
    public int getMaxDamage(ItemStack stack)
    {
        int damage = stack.getItemDamage();

        if (damage == 0 || damage == 2) {
            return 100;
        } else if (damage == 1 || damage == 3) {
            return 500;
        }

        return 0;
    }

    @Override
    public ProcessorType getType(ItemStack stack)
    {
        int damage = stack.getItemDamage();

        if (damage == 0 || damage == 1) {
            return ProcessorType.GRIND;
        } else if (damage == 2 || damage == 3) {
            return ProcessorType.SAW;
        }

        return null;
    }

    @Override
    public int getDuplicationChance(ItemStack stack)
    {
        return (stack.getItemDamage() % 2) == 0 ? 4 : 1;
    }

    @Override
    public int getMaxDuration(ItemStack stack)
    {
        return (stack.getItemDamage() % 2) == 0 ? 500 : 200;
    }

    @Override
    public boolean isBroken(ItemStack stack)
    {
        return getCurrentDamage(stack) >= getMaxDamage(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return getCurrentDamage(stack) > 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        return getCurrentDamage(stack) / getMaxDamage(stack);
    }

    @Override
    public int getCurrentDamage(ItemStack stack)
    {
        NBTTagCompound compound = ItemNBTHelper.getNBT(stack);

        if (compound.hasKey("Damage")) {
            return compound.getInteger("Damage");
        }

        return 0;
    }

    @Override
    public void setCurrentDamage(ItemStack stack, int damage)
    {
        NBTTagCompound compound = ItemNBTHelper.getNBT(stack);

        compound.setInteger("Damage", damage);
    }

    public int damage(ItemStack stack, int damage)
    {
        int duration = getCurrentDamage(stack) + damage;

        if (duration < getMaxDamage(stack)) {
            setCurrentDamage(stack, duration);
            return duration;
        }

        return 0;
    }

}
