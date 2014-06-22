package com.ollieread.technomagi.common.init;

import net.minecraft.item.Item;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.item.ItemConstructModule;
import com.ollieread.technomagi.item.ItemDigitalVisor;
import com.ollieread.technomagi.item.ItemMobBrain;
import com.ollieread.technomagi.item.ItemNaniteContainer;
import com.ollieread.technomagi.item.ItemSampleExtractor;
import com.ollieread.technomagi.item.ItemSampleVile;

import cpw.mods.fml.common.registry.GameRegistry;

public class Items
{

    public static Item itemConstructModule;
    public static Item itemSampleVile;
    public static Item itemSampleExtractor;
    public static Item itemArmourVisor;
    public static Item itemMobBrain;
    public static Item itemNaniteContainer;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering items");

        itemConstructModule = new ItemConstructModule("constructModule");
        itemSampleVile = new ItemSampleVile("sampleVile");
        itemSampleExtractor = new ItemSampleExtractor("sampleExtractor");
        itemArmourVisor = new ItemDigitalVisor("digitalVisor", 4, 0);
        itemMobBrain = new ItemMobBrain("mobBrain");
        itemNaniteContainer = new ItemNaniteContainer("naniteContainer");

        GameRegistry.registerItem(itemConstructModule, "constructModule");
        GameRegistry.registerItem(itemSampleVile, "sampleVile");
        GameRegistry.registerItem(itemSampleExtractor, "sampleExtractor");
        GameRegistry.registerItem(itemArmourVisor, "armourVisor");
        GameRegistry.registerItem(itemMobBrain, "mobBrain");
        GameRegistry.registerItem(itemNaniteContainer, "naniteContainer");
    }

}
