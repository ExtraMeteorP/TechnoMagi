package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.item.ItemEtherium;
import com.ollieread.technomagi.item.ItemEtheriumIngot;
import com.ollieread.technomagi.item.ItemHectoStorage;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemRelux;
import com.ollieread.technomagi.item.ItemReluxIngot;
import com.ollieread.technomagi.item.ItemSampleExtractor;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.item.ItemTM;

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
    }

}
