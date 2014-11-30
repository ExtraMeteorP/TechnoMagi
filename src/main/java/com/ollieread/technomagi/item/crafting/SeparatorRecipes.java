package com.ollieread.technomagi.item.crafting;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SeparatorRecipes
{

    private static final SeparatorRecipes instance = new SeparatorRecipes();
    private Map separatorList = new HashMap();
    private Map extraList = new HashMap();
    private Map chanceList = new HashMap();
    private Random rand = new Random();

    public static SeparatorRecipes getInstance()
    {
        return instance;
    }

    private SeparatorRecipes()
    {

    }

    public void addRecipe(Block block, ItemStack result, ItemStack extra, int chance)
    {
        this.addRecipe(Item.getItemFromBlock(block), result, extra, chance);
    }

    public void addRecipe(Item item, ItemStack result, ItemStack extra, int chance)
    {
        this.addRecipe(new ItemStack(item, 1, 32767), result, extra, chance);
    }

    public void addRecipe(ItemStack stack, ItemStack result, ItemStack extra, int chance)
    {
        separatorList.put(stack, result);

        if (extra != null) {
            extraList.put(result, extra);
            chanceList.put(extra, chance);
        }
    }

    public ItemStack findMatchingRecipe(ItemStack stack)
    {
        Iterator iterator = this.separatorList.entrySet().iterator();
        Entry entry;

        do {
            if (!iterator.hasNext()) {
                return null;
            }

            entry = (Entry) iterator.next();
        } while (!this.matches(stack, (ItemStack) entry.getKey()));

        return (ItemStack) entry.getValue();
    }

    public ItemStack getExtra(ItemStack stack)
    {
        if (stack != null) {
            ItemStack extra = (ItemStack) extraList.get(stack);

            if (extra != null) {
                int chance = (Integer) chanceList.get(extra);

                if (rand.nextInt(chance) == 0) {
                    return extra;
                }
            }
        }

        return null;
    }

    private boolean matches(ItemStack stack, ItemStack result)
    {
        return result.getItem() == stack.getItem() && (result.getItemDamage() == 32767 || result.getItemDamage() == stack.getItemDamage());
    }

    public Map getSeparatorList()
    {
        return this.separatorList;
    }

}
