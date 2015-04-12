package com.ollieread.technomagi.api.crafting;

import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import com.google.common.base.Throwables;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.ReflectionHelper;

public class ShapelessKnowledgeDependantRecipe extends ShapelessRecipes
{

    private String knowledge;

    public ShapelessKnowledgeDependantRecipe(ItemStack output, List items, String knowledge)
    {
        super(output, items);
        this.knowledge = knowledge;
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world)
    {
        EntityPlayer player = findPlayer(inv);
        return player != null && PlayerHelper.hasKnowledge(player, knowledge) && super.matches(inv, world);
    }

    private static final Field eventHandlerField = ReflectionHelper.findField(InventoryCrafting.class, "eventHandler", "field_70465_c");
    private static final Field containerPlayerPlayerField = ReflectionHelper.findField(ContainerPlayer.class, "thePlayer", "field_82862_h");
    private static final Field slotCraftingPlayerField = ReflectionHelper.findField(SlotCrafting.class, "thePlayer", "field_75238_b");

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
