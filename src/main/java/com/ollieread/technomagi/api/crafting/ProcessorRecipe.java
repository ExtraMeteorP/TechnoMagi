package com.ollieread.technomagi.api.crafting;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.api.crafting.ProcessorRecipes.ProcessorType;

public class ProcessorRecipe implements IProcessorRecipe
{

    protected ItemStack input;
    protected Map<ProcessorType, ItemStack> output = new EnumMap<ProcessorType, ItemStack>(ProcessorType.class);
    protected Map<ProcessorType, ItemStack> byProduct = new EnumMap<ProcessorType, ItemStack>(ProcessorType.class);
    protected Map<ProcessorType, Integer> byProductChance = new LinkedHashMap<ProcessorType, Integer>();
    protected Map<ProcessorType, Integer> damage = new LinkedHashMap<ProcessorType, Integer>();

    public ProcessorRecipe(ItemStack input)
    {
        this.input = input;
    }

    public void addOutput(ProcessorType type, ItemStack output, int damage, ItemStack byProduct, int byProductChance)
    {
        this.output.put(type, output);
        this.damage.put(type, damage);

        if (byProduct != null) {
            this.byProduct.put(type, byProduct);
            this.byProductChance.put(type, byProductChance);
        }
    }

    @Override
    public ItemStack getInput(ProcessorType type)
    {
        return input;
    }

    @Override
    public ItemStack getOutput(ProcessorType type)
    {
        if (output.containsKey(type)) {
            return output.get(type);
        }

        return null;
    }

    @Override
    public ItemStack getByProduct(ProcessorType type)
    {
        if (byProduct.containsKey(type)) {
            return byProduct.get(type);
        }

        return null;
    }

    @Override
    public int getDamage(ProcessorType type)
    {
        if (damage.containsKey(type)) {
            return damage.get(type);
        }

        return 0;
    }

    @Override
    public int getByProductChance(ProcessorType type)
    {
        if (byProductChance.containsKey(type)) {
            return byProductChance.get(type);
        }

        return 0;
    }

}
