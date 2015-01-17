package com.ollieread.technomagi.item.crafting;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

public class ShapedRecipe implements IRecipeTM
{

    public final int width;
    public final int height;
    public final ItemStack[] items;
    private ItemStack output;
    private String knowledge;

    public ShapedRecipe(int width, int height, ItemStack[] items, ItemStack output, String knowledge)
    {
        this.width = width;
        this.height = height;
        this.items = items;
        this.output = output;
        this.knowledge = knowledge;
    }

    public boolean matches(IInventory crafting, World world)
    {
        for (int i = 0; i <= 3 - this.width; ++i) {
            for (int j = 0; j <= 3 - this.height; ++j) {
                if (this.checkMatch(crafting, i, j, true)) {
                    return true;
                }

                if (this.checkMatch(crafting, i, j, false)) {
                    return true;
                }
            }
        }

        return false;
    }

    public ItemStack getCraftingResult(IInventory crafting)
    {
        return output;
    }

    public int getRecipeSize()
    {
        return this.width * this.height;
    }

    public ItemStack getRecipeOutput()
    {
        return output;
    }

    private boolean checkMatch(IInventory crafting, int i, int j, boolean b)
    {
        for (int k = 0; k < 3; ++k) {
            for (int l = 0; l < 3; ++l) {
                int i1 = k - i;
                int j1 = l - j;
                ItemStack itemstack = null;
                ItemStack itemstack1 = null;

                if (i1 >= 0 && j1 >= 0 && i1 < this.width && j1 < this.height) {
                    if (b) {
                        itemstack = this.items[this.width - i1 - 1 + j1 * this.width];
                        itemstack1 = crafting.getStackInSlot(this.width - i1 - 1 + j1 * this.width);
                    } else {
                        itemstack = this.items[i1 + j1 * this.width];
                        itemstack1 = crafting.getStackInSlot(i1 + j1 * this.width);
                    }
                }

                if (itemstack1 != null || itemstack != null) {
                    if (itemstack1 == null && itemstack != null || itemstack1 != null && itemstack == null) {
                        return false;
                    }

                    if (itemstack.getItem() != itemstack1.getItem()) {
                        return false;
                    }

                    if (itemstack.getItemDamage() != 32767 && itemstack.getItemDamage() != itemstack1.getItemDamage()) {
                        return false;
                    }
                }
            }
        }

        return true;
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

    @Override
    public String getKnowledge()
    {
        return knowledge;
    }

    @Override
    public ItemStack getOutput()
    {
        return output;
    }
}
