package com.ollieread.technomagi.common.recipes;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.ollieread.technomagi.api.crafting.CraftingHandler;
import com.ollieread.technomagi.api.time.ITimeShiftTransformer;
import com.ollieread.technomagi.api.time.TimeHandler.TimeShift;
import com.ollieread.technomagi.util.ItemStackHelper;

public class FurnaceTimeShiftTransformer implements ITimeShiftTransformer
{

    @Override
    public boolean canTransform(ItemStack stack, TimeShift type)
    {
        if (!type.equals(TimeShift.FIXED)) {
            if (!CraftingHandler.furnace.isVanilla(stack)) {
                return true;
            } else {
                if (type.equals(TimeShift.BACKWARD)) {
                    return ItemStackHelper.containsValue(FurnaceRecipes.smelting().getSmeltingList(), stack, true);
                } else if (type.equals(TimeShift.FORWARD)) {
                    return ItemStackHelper.containsKey(FurnaceRecipes.smelting().getSmeltingList(), stack, true);
                }
            }
        }

        return false;
    }

    @Override
    public ItemStack transform(ItemStack stack, TimeShift type)
    {
        ItemStack result = null;

        if (!CraftingHandler.furnace.isVanilla(stack)) {
            if (type.equals(TimeShift.BACKWARD)) {
                result = CraftingHandler.furnace.findInput(stack);
            } else if (type.equals(TimeShift.FORWARD)) {
                result = CraftingHandler.furnace.findOutput(stack);
            }
        } else {
            if (type.equals(TimeShift.BACKWARD)) {
                Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.smelting().getSmeltingList();

                for (Entry<ItemStack, ItemStack> input : smeltingList.entrySet()) {
                    if (stack.isItemEqual(input.getValue()) && ItemStack.areItemStackTagsEqual(stack, input.getValue())) {
                        result = input.getKey();
                        break;
                    }
                }
            } else if (type.equals(TimeShift.FORWARD)) {
                result = FurnaceRecipes.smelting().getSmeltingResult(stack);
            }
        }

        return result;
    }
}
