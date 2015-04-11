package com.ollieread.technomagi.api.crafting;

import java.lang.reflect.Field;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

import com.google.common.base.Throwables;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class ShapedKnowledgeDependantRecipe extends ShapedRecipes
{

    private String[] knowledge = new String[] {};

    public ShapedKnowledgeDependantRecipe(int width, int height, ItemStack[] items, ItemStack output, String[] knowledge)
    {
        super(width, height, items, output);

        if (knowledge != null) {
            this.knowledge = knowledge;
        }
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        EntityPlayer player = findPlayer(inv);

        if (player != null && super.matches(inv, world)) {
            for (int i = 0; i < knowledge.length; i++) {
                if (!PlayerHelper.hasKnowledge(player, knowledge[i])) {
                    return false;
                }
            }
        }

        return true;
    }

    // TODO: SRG names for non-dev environment
    private static final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, "eventHandler");
    private static final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, "thePlayer");
    private static final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "thePlayer");

    private static EntityPlayer findPlayer(InventoryCrafting inv)
    {
        try {
            Container container = (Container) eventHandlerField.get(inv);
            if (container instanceof ContainerPlayer) {
                return (EntityPlayer) containerPlayerPlayerField.get(container);
            } else if (container instanceof ContainerWorkbench) {
                return (EntityPlayer) slotCraftingPlayerField.get(container.getSlot(0));
            } else {
                // don't know the player
                return null;
            }
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
