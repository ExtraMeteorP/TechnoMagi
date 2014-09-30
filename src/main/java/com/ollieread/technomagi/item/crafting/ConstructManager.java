package com.ollieread.technomagi.item.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

public class ConstructManager
{

    private static final ConstructManager instance = new ConstructManager();
    private Map<Block, List<ItemStack>> recipes = new LinkedHashMap<Block, List<ItemStack>>();
    private Map<Block, String> recipeKnowledge = new LinkedHashMap<Block, String>();

    public static final ConstructManager getInstance()
    {
        return instance;
    }

    public void addRecipe(Block block, ItemStack[] stacks, String knowledge)
    {
        if (block != null && stacks != null && !recipes.containsKey(block)) {
            List list = new ArrayList();

            for (int i = 0; i < stacks.length; i++) {
                list.add(stacks[i]);
            }

            recipes.put(block, list);

            if (knowledge != null) {
                recipeKnowledge.put(block, knowledge);
            }
        }
    }

    public Block findMatchingRecipe(ItemStack[] stacks, EntityPlayer player)
    {
        if (stacks != null && stacks.length > 0) {
            ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(player);

            if (playerKnowledge != null && !playerKnowledge.canSpecialise()) {
                for (Iterator<Block> i = recipes.keySet().iterator(); i.hasNext();) {
                    Block block = i.next();
                    List<ItemStack> recipe = recipes.get(block);
                    String knowledge = recipeKnowledge.get(block);

                    if (knowledge == null || playerKnowledge.hasKnowledge(knowledge)) {
                        if (recipe.size() > 0) {
                            if (recipe.size() == stacks.length) {
                                int m = recipe.size();

                                for (int x = 0; x < stacks.length; x++) {
                                    if (stacks[x] != null) {
                                        for (Iterator<ItemStack> it = recipe.iterator(); it.hasNext();) {
                                            ItemStack stack = it.next();

                                            if (stacks[x].isItemEqual(stack) && stacks[x].stackSize == stack.stackSize) {
                                                m--;

                                                break;
                                            }
                                        }
                                    }
                                }

                                if (m == 0) {
                                    return block;
                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    public Map<Block, List<ItemStack>> getRecipeList()
    {
        return this.recipes;
    }
}
