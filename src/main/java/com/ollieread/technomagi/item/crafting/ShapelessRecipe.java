package com.ollieread.technomagi.item.crafting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;

public class ShapelessRecipe implements IRecipeTM
{
    private final ItemStack output;
    public final List items;
    private String knowledge;

    public ShapelessRecipe(ItemStack output, List items, String knowledge)
    {
        this.output = output;
        this.items = items;
        this.knowledge = knowledge;
    }

    public ItemStack getRecipeOutput()
    {
        return this.output;
    }

    public boolean matches(IInventory crafting, World world)
    {
        ArrayList arraylist = new ArrayList(this.items);

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                ItemStack itemstack = crafting.getStackInSlot(j + i * 3);

                if (itemstack != null) {
                    boolean flag = false;
                    Iterator iterator = arraylist.iterator();

                    while (iterator.hasNext()) {
                        ItemStack itemstack1 = (ItemStack) iterator.next();

                        if (itemstack.getItem() == itemstack1.getItem() && (itemstack1.getItemDamage() == 32767 || itemstack.getItemDamage() == itemstack1.getItemDamage())) {
                            flag = true;
                            arraylist.remove(itemstack1);
                            break;
                        }
                    }

                    if (!flag) {
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }

    public ItemStack getCraftingResult(IInventory p_77572_1_)
    {
        return this.output.copy();
    }

    @Override
    public int getRecipeSize()
    {
        return this.items.size();
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