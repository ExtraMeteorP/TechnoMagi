package com.ollieread.technomagi.common.init;

import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOldLog;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.crafting.CraftingHandler;
import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;
import com.ollieread.technomagi.api.crafting.OreDictProcessorRecipe;
import com.ollieread.technomagi.api.crafting.ProcessorRecipe;
import com.ollieread.technomagi.util.ItemStackHelper;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{

    public static void init()
    {
        Technomagi.info("Initiating & registering recipes");

        Map<ItemStack, ItemStack> smeltingList = FurnaceRecipes.smelting().getSmeltingList();

        for (Entry<ItemStack, ItemStack> entry : smeltingList.entrySet()) {
            if (Block.getBlockFromItem(entry.getKey().getItem()) != null) {
                CraftingHandler.reactive.add(entry.getKey(), entry.getValue(), 200);
            }
        }

        CraftingHandler.vanilla.add(new ItemStack(Items.personalInterface, 1), "xyx", "zez", "z z", 'x', ItemStackHelper.item("iron_ingot"), 'y', ItemStackHelper.item("gold_ingot"), 'z', ItemStackHelper.item("redstone"), 'e', ItemStackHelper.itemSubtype(Items.crystal, "etherium", 1));

        // Resources
        // Rods
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "iron_rod", 1), "x", "x", 'x', ItemStackHelper.item("iron_ingot"));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "gold_rod", 1), "x", "x", 'x', ItemStackHelper.item("gold_ingot"));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "diamond_rod", 1), "x", "x", 'x', ItemStackHelper.item("diamond"));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "copper_rod", 1), "x", "x", 'x', ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "aluminium_rod", 1), "x", "x", 'x', ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1));
        // Sheets
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "iron_sheet", 2), "xx", "xx", 'x', ItemStackHelper.item("iron_ingot"));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "gold_sheet", 2), "xx", "xx", 'x', ItemStackHelper.item("gold_ingot"));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "diamond_sheet", 2), "xx", "xx", 'x', ItemStackHelper.item("diamond"));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "copper_sheet", 2), "xx", "xx", 'x', ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1));
        CraftingHandler.vanilla.add(ItemStackHelper.itemSubtype(Items.resource, "aluminium_sheet", 2), "xx", "xx", 'x', ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1));
        // Ingots
        CraftingHandler.furnace.add(ItemStackHelper.blockSubtype(Blocks.resource, "copper_ore", 1), ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1), false, 1F);
        CraftingHandler.furnace.add(ItemStackHelper.blockSubtype(Blocks.resource, "aluminium_ore", 1), ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1), false, 1F);
        CraftingHandler.furnace.add(ItemStackHelper.itemSubtype(Items.resource, "copper_dust", 1), ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1), false, 1F);
        CraftingHandler.furnace.add(ItemStackHelper.itemSubtype(Items.resource, "aluminium_dust", 1), ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1), false, 1F);

        // Staffs
        // Basic
        CraftingHandler.vanilla.add(new ItemStack(Items.staffBasic), " x", "x ", 'x', ItemStackHelper.itemSubtype(Items.resource, "iron_rod", 1));

        if (!Config.multiplayerMode) {
            CraftingHandler.vanilla.add(new ItemStack(Items.staffShadow), "x", 'x', new ItemStack(Items.staffScholar));
            CraftingHandler.vanilla.add(new ItemStack(Items.staffCleric), "x", 'x', new ItemStack(Items.staffShadow));
            CraftingHandler.vanilla.add(new ItemStack(Items.staffEngineer), "x", 'x', new ItemStack(Items.staffCleric));
            CraftingHandler.vanilla.add(new ItemStack(Items.staffGuardian), "x", 'x', new ItemStack(Items.staffEngineer));
            CraftingHandler.vanilla.add(new ItemStack(Items.staffScholar), "x", 'x', new ItemStack(Items.staffGuardian));
        }

        processor();
        electromagnetic();
        energy();

        GameRegistry.registerFuelHandler(new FuelHandler());
    }

    private static void processor()
    {
        // Processor Recipes
        ProcessorRecipe ironOreRecipe = new ProcessorRecipe(ItemStackHelper.block("iron_ore"));
        ProcessorRecipe goldOreRecipe = new ProcessorRecipe(ItemStackHelper.block("gold_ore"));
        ProcessorRecipe diamondOreRecipe = new ProcessorRecipe(ItemStackHelper.block("diamond_ore"));
        ProcessorRecipe copperOreRecipe = new OreDictProcessorRecipe(ItemStackHelper.blockSubtype(Blocks.resource, "copper_ore", 1));
        ProcessorRecipe aluminiumOreRecipe = new OreDictProcessorRecipe(ItemStackHelper.blockSubtype(Blocks.resource, "aluminium_ore", 1));

        ironOreRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "iron_dust", 2), 2, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        goldOreRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "gold_dust", 2), 1, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        diamondOreRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "diamond_dust", 2), 5, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8, 2);
        copperOreRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "copper_dust", 2), 2, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        aluminiumOreRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "aluminium_dust", 2), 1, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        ironOreRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.item("iron_ingot", 1), 2, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        goldOreRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.item("gold_ingot", 1), 1, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        copperOreRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.itemSubtype(Items.resource, "copped_ingot", 1), 2, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);
        aluminiumOreRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1), 1, ItemStackHelper.itemSubtype(Items.resource, "stone_dust", 1), 8);

        ProcessorRecipe ironIngotRecipe = new ProcessorRecipe(ItemStackHelper.item("iron_ingot"));
        ProcessorRecipe goldIngotRecipe = new ProcessorRecipe(ItemStackHelper.item("gold_ingot"));
        ProcessorRecipe diamondRecipe = new ProcessorRecipe(ItemStackHelper.item("diamond"));
        ProcessorRecipe copperIngotRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1));
        ProcessorRecipe aluminiumIngotRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1));

        ironIngotRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "iron_dust", 1), 2, null, 0);
        goldIngotRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "gold_dust", 1), 1, null, 0);
        diamondRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "diamond_dust", 1), 5, null, 0, 2);
        copperIngotRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "copper_dust", 1), 2, null, 0);
        aluminiumIngotRecipe.addOutput(ProcessorType.GRIND, ItemStackHelper.itemSubtype(Items.resource, "aluminium_dust", 1), 1, null, 0);
        ironIngotRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "iron_sheet", 2), 2, null, 0);
        goldIngotRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "gold_sheet", 2), 1, null, 0);
        diamondRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "diamond_sheet", 2), 5, null, 0, 2);
        copperIngotRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "copper_sheet", 2), 2, null, 0);
        aluminiumIngotRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "aluminium_sheet", 2), 1, null, 0);

        ProcessorRecipe ironSheetRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "iron_sheet", 1));
        ProcessorRecipe goldSheetRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "gold_sheet", 1));
        ProcessorRecipe diamondSheetRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "diamond_sheet", 1));
        ProcessorRecipe copperSheetRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "copper_sheet", 1));
        ProcessorRecipe aluminiumSheetRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "aluminium_sheet", 1));

        ironSheetRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "iron_rod", 2), 2, null, 0);
        goldSheetRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "gold_rod", 2), 1, null, 0);
        diamondSheetRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "diamond_rod", 2), 5, null, 0, 2);
        copperSheetRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "copper_rod", 2), 2, null, 0);
        aluminiumSheetRecipe.addOutput(ProcessorType.ROLL, ItemStackHelper.itemSubtype(Items.resource, "aluminium_rod", 2), 1, null, 0);

        ProcessorRecipe ironDustRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "iron_dust", 1));
        ProcessorRecipe goldDustRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "iron_dust", 1));
        ProcessorRecipe copperDustRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "iron_dust", 1));
        ProcessorRecipe aluminiumDustRecipe = new OreDictProcessorRecipe(ItemStackHelper.itemSubtype(Items.resource, "iron_dust", 1));

        ironDustRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.item("iron_ingot", 1), 2, null, 0);
        goldDustRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.item("gold_infor", 1), 2, null, 0);
        copperDustRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.itemSubtype(Items.resource, "copper_ingot", 1), 2, null, 0);
        aluminiumDustRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.itemSubtype(Items.resource, "aluminium_ingot", 1), 2, null, 0);

        CraftingHandler.processor.add(ironOreRecipe);
        CraftingHandler.processor.add(goldOreRecipe);
        CraftingHandler.processor.add(diamondOreRecipe);
        CraftingHandler.processor.add(copperOreRecipe);
        CraftingHandler.processor.add(aluminiumOreRecipe);
        CraftingHandler.processor.add(ironIngotRecipe);
        CraftingHandler.processor.add(goldIngotRecipe);
        CraftingHandler.processor.add(diamondRecipe);
        CraftingHandler.processor.add(copperIngotRecipe);
        CraftingHandler.processor.add(aluminiumIngotRecipe);
        CraftingHandler.processor.add(ironSheetRecipe);
        CraftingHandler.processor.add(goldSheetRecipe);
        CraftingHandler.processor.add(copperSheetRecipe);
        CraftingHandler.processor.add(aluminiumSheetRecipe);
        CraftingHandler.processor.add(ironDustRecipe);
        CraftingHandler.processor.add(goldDustRecipe);
        CraftingHandler.processor.add(copperDustRecipe);
        CraftingHandler.processor.add(aluminiumDustRecipe);

        // Log and wood recipes

        String[] logNames = BlockOldLog.field_150168_M;

        for (int i = 0; i < logNames.length; i++) {
            ProcessorRecipe logRecipe = new ProcessorRecipe(ItemStackHelper.block("log", 1, i));
            logRecipe.addOutput(ProcessorType.SAW, ItemStackHelper.block("planks", 6, i), 3, ItemStackHelper.itemSubtype(Items.resource, "wood_dust", 1), 8);
            logRecipe.addOutput(ProcessorType.BURN, ItemStackHelper.item("coal", 2, 1), 1, null, 0);
            ProcessorRecipe woodRecipe = new ProcessorRecipe(ItemStackHelper.block("planks", 1, i));
            woodRecipe.addOutput(ProcessorType.SAW, ItemStackHelper.block("wooden_slab", 3, i), 2, ItemStackHelper.itemSubtype(Items.resource, "wood_dust", 1), 8);
            ProcessorRecipe slabRecipe = new ProcessorRecipe(ItemStackHelper.block("wooden_slab", 1, i));
            slabRecipe.addOutput(ProcessorType.SAW, ItemStackHelper.item("stick", 4), 1, ItemStackHelper.itemSubtype(Items.resource, "wood_dust", 1), 8);

            CraftingHandler.processor.add(logRecipe);
            CraftingHandler.processor.add(woodRecipe);
            CraftingHandler.processor.add(slabRecipe);
        }
    }

    private static void electromagnetic()
    {

    }

    private static void energy()
    {

    }

    public static class FuelHandler implements IFuelHandler
    {

        @Override
        public int getBurnTime(ItemStack fuel)
        {
            if (fuel.isItemEqual(ItemStackHelper.itemSubtype(Items.resource, "wood_dust", 1))) {
                return 100;
            } else if (fuel.isItemEqual(ItemStackHelper.itemSubtype(Items.crystalCharged, "calidite", 1))) {
                return 1000;
            }

            return 0;
        }

    }

}
