package com.ollieread.technomagi.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

public class ConstructRecipe
{

    private ItemStack output = null;
    private ItemStack[] stacks = new ItemStack[0];
    private String knowledge = null;

    public ConstructRecipe(Block block, ItemStack[] stacks, String knowledge)
    {
        this(block, 0, stacks, knowledge);
    }

    public ConstructRecipe(Block block, int metadata, ItemStack[] stacks, String knowledge)
    {
        this(new ItemStack(block, 1, metadata), stacks, knowledge);
    }

    public ConstructRecipe(ItemStack block, ItemStack[] stacks, String knowledge)
    {
        this.output = block;
        this.stacks = stacks;
        this.knowledge = knowledge;
    }

    public ItemStack getRecipeOutput()
    {
        return output;
    }

    public ItemStack[] getRecipeInput()
    {
        return stacks;
    }

    public String getKnowledge()
    {
        return knowledge;
    }

    public boolean checkMatch(ItemStack[] stacks)
    {
        int match = 0;

        for (int i = 0; i < this.stacks.length; i++) {
            if (ItemStack.areItemStacksEqual(this.stacks[i], stacks[i]) && this.stacks[i].stackSize == stacks[i].stackSize) {
                match++;
            }
        }

        return match == this.stacks.length;
    }

    public boolean canCraft(EntityPlayer player)
    {
        if (knowledge != null) {
            ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

            if (charon == null || !charon.hasKnowledge(knowledge)) {
                return false;
            }
        }

        return true;
    }

}
