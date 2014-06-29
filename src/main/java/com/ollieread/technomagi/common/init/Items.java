package com.ollieread.technomagi.common.init;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.item.ItemMobBrain;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleExtractor;
import com.ollieread.technomagi.item.ItemSampleVile;
import com.ollieread.technomagi.item.ItemTM;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items
{

    public static ItemTM itemSampleVile;
    public static ItemTM itemSampleExtractor;
    // public static ItemTM itemArmourVisor;
    public static ItemTM itemMobBrain;
    public static ItemTM itemNaniteContainer;
    public static ItemTM itemDigitalTool;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering items");

        itemSampleVile = new ItemSampleVile("sampleVile");
        itemSampleExtractor = new ItemSampleExtractor("sampleExtractor");
        // itemArmourVisor = new ItemDigitalVisor("digitalVisor", 4, 0);
        itemMobBrain = new ItemMobBrain("mobBrain");
        itemNaniteContainer = new ItemNaniteContainer("naniteContainer");
        itemDigitalTool = new ItemDigitalTool("digitalTool");

        GameRegistry.registerItem(itemSampleVile, "sampleVile");
        GameRegistry.registerItem(itemSampleExtractor, "sampleExtractor");
        // GameRegistry.registerItem(itemArmourVisor, "armourVisor");
        GameRegistry.registerItem(itemMobBrain, "mobBrain");
        GameRegistry.registerItem(itemNaniteContainer, "naniteContainer");
        GameRegistry.registerItem(itemDigitalTool, "digitalTool");
    }

}
