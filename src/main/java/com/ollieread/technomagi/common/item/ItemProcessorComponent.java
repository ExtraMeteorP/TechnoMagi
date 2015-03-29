package com.ollieread.technomagi.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;
import com.ollieread.technomagi.api.crafting.IProcessorComponent;
import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemProcessorComponent extends ItemSubtypes implements IProcessorComponent
{

    public ItemProcessorComponent(String name)
    {
        super(name, new String[] { "iron_grinding", "diamond_grinding", "iron_saw", "diamond_saw", "stone_furnace_bed", "heating_element", "stone_rolling", "iron_rolling" });

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
        if (getTier(stack) == 1) {
            return 100;
        } else {
            return 500;
        }
    }

    @Override
    public ProcessorType getType(ItemStack stack)
    {
        int damage = stack.getItemDamage();

        if (damage == 0 || damage == 1) {
            return ProcessorType.GRIND;
        } else if (damage == 2 || damage == 3) {
            return ProcessorType.SAW;
        } else if (damage == 4 || damage == 5) {
            return ProcessorType.BURN;
        } else if (damage == 6 || damage == 7) {
            return ProcessorType.ROLL;
        }

        return null;
    }

    @Override
    public int getTier(ItemStack stack)
    {
        return (stack.getItemDamage() % 2) == 0 ? 1 : 2;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return getTier(stack) == 1 ? EnumRarity.common : EnumRarity.uncommon;
    }

    @Override
    public int getDuplicationChance(ItemStack stack)
    {
        return getTier(stack) == 1 ? 8 : 4;
    }

    @Override
    public int getMaxDuration(ItemStack stack)
    {
        return getTier(stack) == 1 ? 500 : 200;
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

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced)
    {
        if (!isBroken(stack)) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
                info.add("Tier: " + (getTier(stack) == 1 ? 1 : 2));
                info.add("Durability: " + (getMaxDamage(stack) - getCurrentDamage(stack)) + "/" + getMaxDamage(stack));
                info.add("Duration: " + getMaxDuration(stack));
                info.add("Duplication Chance: " + (100 / getDuplicationChance(stack)) + "%");
            } else {
                info.add("Hold " + EnumChatFormatting.DARK_AQUA + "SHIFT" + EnumChatFormatting.GRAY + " for more details.");
            }
        } else {
            info.add(EnumChatFormatting.RED + "BROKEN");
        }
    }
}
