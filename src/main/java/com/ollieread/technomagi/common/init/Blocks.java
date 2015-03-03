package com.ollieread.technomagi.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.BlockResource;
import com.ollieread.technomagi.common.block.BlockSubtypes;
import com.ollieread.technomagi.common.block.battery.BlockBattery;
import com.ollieread.technomagi.common.block.battery.tile.TileBattery;
import com.ollieread.technomagi.common.block.conduit.BlockConduit;
import com.ollieread.technomagi.common.block.conduit.tile.TileConduitFluid;
import com.ollieread.technomagi.common.block.conduit.tile.TileConduitPower;
import com.ollieread.technomagi.common.block.electromagnetic.BlockElectromagnetic;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;
import com.ollieread.technomagi.common.block.extrapolator.BlockExtrapolator;
import com.ollieread.technomagi.common.block.extrapolator.tile.TileExtrapolator;
import com.ollieread.technomagi.common.block.processor.BlockResourceProcessor;
import com.ollieread.technomagi.common.block.processor.tile.TileResourceProcessorBasic;
import com.ollieread.technomagi.common.block.scanner.BlockScanner;
import com.ollieread.technomagi.common.block.scanner.tile.TileScanner;
import com.ollieread.technomagi.common.block.world.BlockNaniteFarmland;
import com.ollieread.technomagi.common.item.block.ItemBlockBase;
import com.ollieread.technomagi.common.item.block.ItemBlockBattery;
import com.ollieread.technomagi.common.item.block.ItemBlockConduit;

import cpw.mods.fml.common.registry.GameRegistry;

public class Blocks
{

    public static BlockSubtypes resource;
    public static Block electromagnetic;
    public static BlockContainerSubtypes scanner;
    public static BlockContainerSubtypes conduit;
    public static BlockContainerSubtypes processor;
    public static Block naniteFarmland;
    public static Block extrapolator;
    public static Block battery;

    public static void init()
    {
        resource = new BlockResource("resource");
        resource.setHardness(5.0F).setResistance(10.0F);
        electromagnetic = new BlockElectromagnetic("electromagnetic");
        scanner = new BlockScanner("scanner");
        scanner.setHardness(3.5F).setResistance(5.0F);
        conduit = new BlockConduit("conduit");
        conduit.setHardness(3.5F).setResistance(5.0F);
        processor = new BlockResourceProcessor("processor");
        processor.setHardness(3.5F);
        naniteFarmland = new BlockNaniteFarmland("nanite_farmland");
        extrapolator = new BlockExtrapolator("extrapolator");
        battery = new BlockBattery("battery");

        // Register Blocks
        GameRegistry.registerBlock(resource, ItemBlockBase.class, "resource");
        GameRegistry.registerBlock(electromagnetic, "electromagnetic");
        GameRegistry.registerBlock(scanner, ItemBlockBase.class, "scanners");
        GameRegistry.registerBlock(conduit, ItemBlockConduit.class, "conduit");
        GameRegistry.registerBlock(processor, ItemBlockBase.class, "processor");
        GameRegistry.registerBlock(naniteFarmland, "nanite_farmland");
        GameRegistry.registerBlock(extrapolator, "extrapolator");
        GameRegistry.registerBlock(battery, ItemBlockBattery.class, "battery");

        // Register TileEntities
        GameRegistry.registerTileEntity(TileScanner.class, "scanner_tile");
        GameRegistry.registerTileEntity(TileElectromagnetic.class, "electromagnetic");
        GameRegistry.registerTileEntity(TileConduitFluid.class, "conduit_fluid");
        GameRegistry.registerTileEntity(TileConduitPower.class, "conduit_power");
        GameRegistry.registerTileEntity(TileResourceProcessorBasic.class, "processor_basic");
        GameRegistry.registerTileEntity(TileExtrapolator.class, "extrapolator");
        GameRegistry.registerTileEntity(TileBattery.class, "battery");

        // Register with the ore dictionary
        OreDictionary.registerOre("oreEtherium", new ItemStack(resource, 1, 0));
        OreDictionary.registerOre("oreCopper", new ItemStack(resource, 1, 2));
        OreDictionary.registerOre("oreAluminium", new ItemStack(resource, 1, 3));
        OreDictionary.registerOre("oreAluminum", new ItemStack(resource, 1, 3));
    }
}
