package com.ollieread.technomagi.common.init;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.item.ItemComponent;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.item.ItemEtherium;
import com.ollieread.technomagi.item.ItemEtheriumIngot;
import com.ollieread.technomagi.item.ItemHectoStorage;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemRelux;
import com.ollieread.technomagi.item.ItemReluxIngot;
import com.ollieread.technomagi.item.ItemSampleExtractor;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.item.ItemStaff;
import com.ollieread.technomagi.item.ItemTM;
import com.ollieread.technomagi.item.ItemUnit;
import com.ollieread.technomagi.item.crafting.CraftingManager;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items
{

    public static ItemTM itemSampleVile;
    public static ItemTM itemSampleExtractor;
    public static ItemTM itemMobBrain;
    public static ItemTM itemNaniteContainer;
    public static ItemTM itemDigitalTool;
    public static ItemTM itemEtherium;
    public static ItemTM itemEtheriumIngot;
    public static ItemTM itemRelux;
    public static ItemTM itemReluxIngot;
    public static ItemTM itemHectoStorage;
    public static ItemTM itemComponent;
    public static ItemTM itemUnit;
    public static ItemTM itemTechnomageStaff;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering items");

        itemSampleVile = new ItemSampleVile("sampleVile");
        itemSampleExtractor = new ItemSampleExtractor("sampleExtractor");
        // itemMobBrain = new ItemMobBrain("mobBrain");
        itemNaniteContainer = new ItemNaniteContainer("naniteContainer");
        itemDigitalTool = new ItemDigitalTool("digitalTool");
        itemEtherium = new ItemEtherium("etherium");
        itemEtheriumIngot = new ItemEtheriumIngot("etheriumIngot");
        itemRelux = new ItemRelux("relux");
        itemReluxIngot = new ItemReluxIngot("reluxIngot");
        itemHectoStorage = new ItemHectoStorage("hectoStorage");
        itemComponent = new ItemComponent("component");
        itemUnit = new ItemUnit("unit");
        itemTechnomageStaff = new ItemStaff("technomageStaff");

        GameRegistry.registerItem(itemTechnomageStaff, "technomageStaff");
        GameRegistry.registerItem(itemSampleVile, "sampleVile");
        GameRegistry.registerItem(itemSampleExtractor, "sampleExtractor");
        // GameRegistry.registerItem(itemMobBrain, "mobBrain");
        GameRegistry.registerItem(itemNaniteContainer, "naniteContainer");
        GameRegistry.registerItem(itemDigitalTool, "digitalTool");
        GameRegistry.registerItem(itemEtherium, "etherium");
        GameRegistry.registerItem(itemEtheriumIngot, "etheriumIngot");
        GameRegistry.registerItem(itemRelux, "relux");
        GameRegistry.registerItem(itemReluxIngot, "reluxIngot");
        GameRegistry.registerItem(itemHectoStorage, "hectoStorage");
        GameRegistry.registerItem(itemComponent, "component");
        GameRegistry.registerItem(itemUnit, "unit");

        ItemStack stackSampleVile = new ItemStack(itemSampleVile, 8, 0);
        stackSampleVile.stackTagCompound = new NBTTagCompound();
        ItemStack stackSampleExtractor = new ItemStack(itemSampleExtractor, 1);
        stackSampleExtractor.stackTagCompound = new NBTTagCompound();
        ItemStack stackNaniteContainer = new ItemStack(itemNaniteContainer, 4);
        stackNaniteContainer.stackTagCompound = new NBTTagCompound();

        ItemStack ironIngot = new ItemStack(net.minecraft.init.Items.iron_ingot, 1);
        ItemStack goldIngot = new ItemStack(net.minecraft.init.Items.gold_ingot, 1);
        ItemStack diamond = new ItemStack(net.minecraft.init.Items.diamond, 1);

        // empty sample vile
        GameRegistry.addShapedRecipe(stackSampleVile, "x x", "x x", "xxx", 'x', new ItemStack(net.minecraft.init.Blocks.glass, 1));
        // sample extractor
        GameRegistry.addShapedRecipe(stackSampleExtractor, " xy", "zxx", "  x", 'x', ironIngot, 'y', new ItemStack(itemSampleVile, 1), 'z', new ItemStack(itemComponent, 1, 2));
        // nanite container
        GameRegistry.addShapedRecipe(stackNaniteContainer, " x ", "yzy", " x ", 'x', ironIngot, 'y', new ItemStack(itemComponent, 1, 5), 'z', new ItemStack(net.minecraft.init.Blocks.glass, 1));
        // iron sheet
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 2, 1), "xx", "xx", 'x', ironIngot);
        // iron rod
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 2), "x", "x", 'x', ironIngot);
        // gold sheet
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 2, 4), "xx", "xx", 'x', goldIngot);
        // gold rod
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 5), "x", "x", 'x', goldIngot);
        // diamond sheet
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 2, 7), "xx", "xx", 'x', diamond);
        // diamond rod
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 8), "x", "x", 'x', diamond);
        // life unit
        CraftingManager.getInstance().addRecipe(null, new ItemStack(itemUnit, 1, 0), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.porkchop, 1), 'z', new ItemStack(itemComponent, 1, 5));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 15), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(itemComponent, 1, 11));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 19), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(itemComponent, 1, 15));
        // nanite unit
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 12), "xzx", " y ", "x x", 'x', ironIngot, 'y', stackNaniteContainer, 'z', new ItemStack(itemComponent, 1, 5));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 16), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(itemComponent, 1, 12));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 20), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(itemComponent, 1, 16));
        // power unit
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 13), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.redstone, 1), 'z', new ItemStack(itemComponent, 1, 5));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 17), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(itemComponent, 1, 13));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 21), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(itemComponent, 1, 17));
        // teleport unit
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 14), "xzx", " y ", "x x", 'x', ironIngot, 'y', new ItemStack(net.minecraft.init.Items.ender_pearl, 1), 'z', new ItemStack(itemComponent, 1, 5));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 18), "x x", " y ", "x x", 'x', goldIngot, 'y', new ItemStack(itemComponent, 1, 14));
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 12), "x x", " y ", "x x", 'x', diamond, 'y', new ItemStack(itemComponent, 1, 18));
        // analysis unit
        GameRegistry.addShapedRecipe(new ItemStack(itemComponent, 1, 10), "zxz", "xyx", " x ", 'x', ironIngot, 'y', new ItemStack(itemComponent, 1, 12), 'z', new ItemStack(itemComponent, 1, 5));
    }
}
