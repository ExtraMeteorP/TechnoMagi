package com.ollieread.technomagi.common.init;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import com.ollieread.technomagi.item.ItemTMNBT;
import com.ollieread.technomagi.util.RecipeHelper;

import cpw.mods.fml.common.registry.GameRegistry;

public class Recipes
{

    public static List<IRecipe> recipes;

    public static void init()
    {
        recipes = new ArrayList<IRecipe>();

        ItemStack basicStaff = ItemTMNBT.resetNBT(new ItemStack(Items.itemTechnomageStaff, 1, 0));
        ItemStack stackSampleVile = ItemTMNBT.resetNBT(new ItemStack(Items.itemSampleVile, 1, 0));
        ItemStack stackSampleVile8 = ItemTMNBT.resetNBT(new ItemStack(Items.itemSampleVile, 8, 0));
        ItemStack stackSampleExtractor = ItemTMNBT.resetNBT(new ItemStack(Items.itemSampleExtractor, 1));
        ItemStack stackNaniteContainer = ItemTMNBT.resetNBT(new ItemStack(Items.itemNaniteContainer, 1, 0));
        ItemStack stackNaniteContainer2 = ItemTMNBT.resetNBT(new ItemStack(Items.itemNaniteContainer, 2, 0));
        ItemStack stackNaniteContainer4 = ItemTMNBT.resetNBT(new ItemStack(Items.itemNaniteContainer, 4, 0));

        ItemStack ironIngot = new ItemStack(net.minecraft.init.Items.iron_ingot, 1);
        ItemStack goldIngot = new ItemStack(net.minecraft.init.Items.gold_ingot, 1);
        ItemStack diamond = new ItemStack(net.minecraft.init.Items.diamond, 1);

        // construct
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Blocks.blockConstruct), "xzx", "zyz", "xzx", 'x', ironIngot, 'y', stackNaniteContainer, 'z', new ItemStack(Items.itemComponent, 1, 1)));
        recipes.add(GameRegistry.addShapedRecipe(basicStaff, "x", "y", "x", 'x', new ItemStack(Items.itemComponent, 1, 2), 'y', new ItemStack(net.minecraft.init.Blocks.chest, 1)));
        recipes.add(GameRegistry.addShapedRecipe(stackSampleVile8, "x x", "x x", "xxx", 'x', new ItemStack(net.minecraft.init.Blocks.glass, 1)));
        recipes.add(GameRegistry.addShapedRecipe(stackSampleExtractor, " xy", "zxx", "  x", 'x', ironIngot, 'y', stackSampleVile, 'z', new ItemStack(Items.itemComponent, 1, 2)));
        recipes.add(GameRegistry.addShapedRecipe(stackNaniteContainer4, " x ", "yzy", " x ", 'x', ironIngot, 'y', new ItemStack(Items.itemComponent, 1, 5), 'z', new ItemStack(net.minecraft.init.Blocks.glass, 1)));
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Items.itemComponent, 2, 1), "xx", "xx", 'x', ironIngot));
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Items.itemComponent, 1, 2), "x", "x", 'x', ironIngot));
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Items.itemComponent, 2, 4), "xx", "xx", 'x', goldIngot));
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Items.itemComponent, 1, 5), "x", "x", 'x', goldIngot));
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Items.itemComponent, 2, 7), "xx", "xx", 'x', diamond));
        recipes.add(GameRegistry.addShapedRecipe(new ItemStack(Items.itemComponent, 1, 8), "x", "x", 'x', diamond));

        RecipeHelper.addSeparatorRecipe(new ItemStack(net.minecraft.init.Blocks.iron_ore), new ItemStack(Items.itemComponent, 2, 0), new ItemStack(net.minecraft.init.Blocks.cobblestone), 3);
        RecipeHelper.addSeparatorRecipe(new ItemStack(net.minecraft.init.Blocks.gold_ore), new ItemStack(Items.itemComponent, 2, 3), new ItemStack(net.minecraft.init.Blocks.cobblestone), 3);
        RecipeHelper.addSeparatorRecipe(new ItemStack(net.minecraft.init.Blocks.diamond_ore), new ItemStack(Items.itemComponent, 1, 6), new ItemStack(net.minecraft.init.Blocks.cobblestone), 3);

        RecipeHelper.addConstructRecipe(Blocks.blockArchive, new ItemStack[] { stackNaniteContainer2, new ItemStack(Items.itemComponent, 2, 5), new ItemStack(net.minecraft.init.Items.redstone, 4), new ItemStack(net.minecraft.init.Items.book, 1) }, null);
        RecipeHelper.addConstructRecipe(Blocks.blockCrafting, new ItemStack[] { stackNaniteContainer2, new ItemStack(Items.itemComponent, 2, 5), new ItemStack(net.minecraft.init.Blocks.crafting_table, 1), new ItemStack(net.minecraft.init.Items.redstone, 4) }, null);
        RecipeHelper.addConstructRecipe(Blocks.blockNaniteReplicator, new ItemStack[] { stackNaniteContainer4, new ItemStack(Items.itemComponent, 2, 5), stackSampleVile8, new ItemStack(net.minecraft.init.Items.redstone, 4) }, "nanites");

        RecipeHelper.addFurnaceRecipe(new ItemStack(Items.itemComponent, 1, 0), ironIngot, 0.7F);
        RecipeHelper.addFurnaceRecipe(new ItemStack(Items.itemComponent, 1, 3), goldIngot, 1F);

        // teleporter
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Blocks.blockTeleporter, 1, 0), " w ", " y ", " z ", 'y', new ItemStack(Items.itemUnit, 1, 3), 'z', new ItemStack(Blocks.blockConstruct, 1), 'w', new ItemStack(net.minecraft.init.Blocks.piston, 1));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Blocks.blockTeleporter, 1, 1), " w ", " y ", " z ", 'y', new ItemStack(Items.itemUnit, 1, 3), 'z', new ItemStack(Blocks.blockConstruct, 1), 'w', new ItemStack(net.minecraft.init.Blocks.sticky_piston, 1));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Blocks.blockTeleporter, 1, 2), " w ", " y ", " z ", 'y', new ItemStack(Items.itemUnit, 1, 4), 'z', new ItemStack(Blocks.blockConstruct, 1), 'w', new ItemStack(net.minecraft.init.Items.ender_pearl, 1));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Blocks.blockTeleporter, 1, 3), " w ", " y ", " z ", 'y', new ItemStack(Items.itemUnit, 1, 5), 'z', new ItemStack(Blocks.blockConstruct, 1), 'w', new ItemStack(net.minecraft.init.Items.emerald, 1));
        // life unit
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 0), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.egg, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 1), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 0));
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 2), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 1));
        // teleport unit
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Items.itemUnit, 1, 3), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.ender_pearl, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Items.itemUnit, 1, 4), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 3));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeTeleportation.getName(), new ItemStack(Items.itemUnit, 1, 5), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 4));
        // subspace unit
        // RecipeHelper.addShapedRecipe(Knowledge.knowledgeSubspace.getName(),
        // new ItemStack(Items.itemUnit, 1, 6), "xzx", " y ", "x x", 'x',
        // ironIngot, 'y', new ItemStack(net.minecraft.init.Items.ender_pearl,
        // 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        // RecipeHelper.addShapedRecipe(Knowledge.knowledgeSubspace.getName(),
        // new ItemStack(Items.itemUnit, 1, 7), "x x", " y ", "x x", 'x',
        // goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 6));
        // RecipeHelper.addShapedRecipe(Knowledge.knowledgeSubspace.getName(),
        // new ItemStack(Items.itemUnit, 1, 8), "x x", " y ", "x x", 'x',
        // diamond, 'y', new ItemStack(Items.itemUnit, 1, 7));
        // nanite unit
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 9), "xzx", " y ", "x x", 'x', ironIngot, 'y', stackNaniteContainer, 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 10), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 9));
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 11), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 10));
        // power unit
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 12), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.redstone, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 13), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 12));
        RecipeHelper.addShapedRecipe(null, new ItemStack(Items.itemUnit, 1, 14), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 13));
        // exo unit
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeExothermic.getName(), new ItemStack(Items.itemUnit, 1, 15), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.blaze_powder, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeExothermic.getName(), new ItemStack(Items.itemUnit, 1, 16), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 15));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeExothermic.getName(), new ItemStack(Items.itemUnit, 1, 17), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 16));
        // endo unit
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeEndothermic.getName(), new ItemStack(Items.itemUnit, 1, 18), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.snowball, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeEndothermic.getName(), new ItemStack(Items.itemUnit, 1, 19), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 18));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeEndothermic.getName(), new ItemStack(Items.itemUnit, 1, 20), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 19));
        // force unit
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeForce.getName(), new ItemStack(Items.itemUnit, 1, 21), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Blocks.gravel, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeForce.getName(), new ItemStack(Items.itemUnit, 1, 22), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 21));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeForce.getName(), new ItemStack(Items.itemUnit, 1, 23), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 22));
        // void unit
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeVoid.getName(), new ItemStack(Items.itemUnit, 1, 24), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.skull, 1, 1), 'z', new ItemStack(Items.itemComponent, 1, 5));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeVoid.getName(), new ItemStack(Items.itemUnit, 1, 25), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(Items.itemUnit, 1, 24));
        RecipeHelper.addShapedRecipe(Knowledge.knowledgeVoid.getName(), new ItemStack(Items.itemUnit, 1, 26), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(Items.itemUnit, 1, 25));
        // Exothermic
        RecipeHelper.addExothermicRecipe(new ItemStack(net.minecraft.init.Blocks.log), new ItemStack(net.minecraft.init.Items.coal, 1, 1), 500);
        // Endothermic
        RecipeHelper.addEndothermicRecipe(new ItemStack(net.minecraft.init.Blocks.cobblestone), new ItemStack(net.minecraft.init.Blocks.stone, 1), 500);
    }

}
