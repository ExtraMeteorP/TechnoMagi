package com.ollieread.technomagi.common.init;

import net.minecraft.item.ItemSword;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.item.ItemCapture;
import com.ollieread.technomagi.item.ItemComponent;
import com.ollieread.technomagi.item.ItemDatasheet;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.item.ItemEnergySword;
import com.ollieread.technomagi.item.ItemMobBrain;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemResource;
import com.ollieread.technomagi.item.ItemSampleExtractor;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.item.ItemStaff;
import com.ollieread.technomagi.item.ItemTM;
import com.ollieread.technomagi.item.ItemTMSubtypes;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items
{

    public static ItemTMSubtypes itemResource;
    public static ItemTMSubtypes itemComponent;
    public static ItemTM itemSampleVile;
    public static ItemTM itemSampleExtractor;
    public static ItemTM itemMobBrain;
    public static ItemTM itemNaniteContainer;
    public static ItemTM itemDigitalTool;
    public static ItemTM itemDatasheet;
    public static ItemTM itemTechnomageStaff;
    public static ItemTM itemCapture;
    public static ItemSword itemEnergySword;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering items");

        itemResource = new ItemResource("resource");
        itemComponent = new ItemComponent("component");
        itemSampleVile = new ItemSampleVile("sampleVile");
        itemSampleExtractor = new ItemSampleExtractor("sampleExtractor");
        itemMobBrain = new ItemMobBrain("mobBrain");
        itemNaniteContainer = new ItemNaniteContainer("naniteContainer");
        itemDigitalTool = new ItemDigitalTool("digitalTool");
        itemDatasheet = new ItemDatasheet("datasheet");
        itemTechnomageStaff = new ItemStaff("technomageStaff");
        itemCapture = new ItemCapture("capture");
        itemEnergySword = new ItemEnergySword("energySword");

        GameRegistry.registerItem(itemResource, "resourceItem");
        GameRegistry.registerItem(itemComponent, "component");
        GameRegistry.registerItem(itemTechnomageStaff, "technomageStaff");
        GameRegistry.registerItem(itemSampleVile, "sampleVile");
        GameRegistry.registerItem(itemSampleExtractor, "sampleExtractor");
        GameRegistry.registerItem(itemMobBrain, "mobBrain");
        GameRegistry.registerItem(itemNaniteContainer, "naniteContainer");
        GameRegistry.registerItem(itemDigitalTool, "digitalTool");
        GameRegistry.registerItem(itemDatasheet, "hectoStorage");
        GameRegistry.registerItem(itemCapture, "capture");
        GameRegistry.registerItem(itemEnergySword, "energySword");
    }
}
