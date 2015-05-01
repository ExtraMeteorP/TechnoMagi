package com.ollieread.technomagi.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.BlockResource;
import com.ollieread.technomagi.common.block.BlockSubtypes;
import com.ollieread.technomagi.common.block.electromagnetic.BlockElectromagnetic;
import com.ollieread.technomagi.common.block.energy.BlockBattery;
import com.ollieread.technomagi.common.block.energy.BlockGenerator;
import com.ollieread.technomagi.common.block.fluid.BlockAmnioticFluid;
import com.ollieread.technomagi.common.block.fluid.BlockEnrichedFluid;
import com.ollieread.technomagi.common.block.fluid.BlockTank;
import com.ollieread.technomagi.common.block.machine.BlockCultivator;
import com.ollieread.technomagi.common.block.machine.BlockFauxPocket;
import com.ollieread.technomagi.common.block.machine.BlockResourceProcessor;
import com.ollieread.technomagi.common.block.research.BlockAnalyser;
import com.ollieread.technomagi.common.block.structure.BlockHardlight;
import com.ollieread.technomagi.common.block.structure.BlockHardlightFence;
import com.ollieread.technomagi.common.block.structure.BlockHardlightPane;
import com.ollieread.technomagi.common.block.structure.BlockHardlightPlatform;
import com.ollieread.technomagi.common.block.structure.BlockHardlightSlab;
import com.ollieread.technomagi.common.block.structure.BlockHardlightStairs;
import com.ollieread.technomagi.common.block.structure.BlockShifted;
import com.ollieread.technomagi.common.block.structure.BlockStructure;
import com.ollieread.technomagi.common.block.teleporter.BlockElevator;
import com.ollieread.technomagi.common.block.world.BlockNaniteFarmland;
import com.ollieread.technomagi.common.item.block.ItemBlockBase;
import com.ollieread.technomagi.common.item.block.ItemBlockBattery;
import com.ollieread.technomagi.common.item.block.ItemBlockFluid;
import com.ollieread.technomagi.common.item.block.ItemBlockTank;

import cpw.mods.fml.common.registry.GameRegistry;

public class Blocks
{

    public static BlockSubtypes resource;
    public static BlockBaseContainer electromagnetic;
    // public static BlockContainerSubtypes conduit;
    public static BlockContainerSubtypes processor;
    public static Block naniteFarmland;
    public static BlockBaseContainer fauxPocket;
    public static BlockBaseContainer battery;
    public static BlockContainerSubtypes generator;
    public static BlockBaseContainer elevator;
    public static Block hardlight;
    public static Block hardlightSlab;
    public static Block hardlightPlatform;
    public static Block hardlightFence;
    public static Block hardlightPane;
    public static Block hardlightStairs;
    public static Block amnioticFluid;
    public static Block enrichedFluid;
    public static BlockContainerSubtypes structure;
    public static BlockContainerSubtypes tank;
    public static BlockBaseContainer shifted;
    public static BlockContainerSubtypes cultivator;
    public static BlockBaseContainer analyser;

    public static void init()
    {
        resource = new BlockResource("resource");
        electromagnetic = new BlockElectromagnetic("electromagnetic");
        // conduit = new BlockConduit("conduit");
        // conduit.setHardness(3.5F).setResistance(5.0F);
        processor = new BlockResourceProcessor("processor");
        naniteFarmland = new BlockNaniteFarmland("nanite_farmland");
        fauxPocket = new BlockFauxPocket("faux_pocket");
        battery = new BlockBattery("battery");
        generator = new BlockGenerator("generator");
        elevator = new BlockElevator("elevator");
        hardlight = new BlockHardlight("hardlight");
        hardlightSlab = new BlockHardlightSlab("hardlight_slab");
        hardlightPlatform = new BlockHardlightPlatform("hardlight_platform");
        hardlightFence = new BlockHardlightFence("hardlight_fence");
        hardlightPane = new BlockHardlightPane("hardlight_pane");
        hardlightStairs = new BlockHardlightStairs("hardlight_stairs");
        amnioticFluid = new BlockAmnioticFluid("amniotic_fluid");
        enrichedFluid = new BlockEnrichedFluid("enriched_fluid");
        structure = new BlockStructure("structure");
        tank = new BlockTank("tank");
        shifted = new BlockShifted("shifted");
        cultivator = new BlockCultivator("cultivator");
        analyser = new BlockAnalyser("analyser");

        // Register Blocks
        GameRegistry.registerBlock(resource, ItemBlockBase.class, "resource");
        GameRegistry.registerBlock(electromagnetic, "electromagnetic");
        // GameRegistry.registerBlock(conduit, ItemBlockConduit.class,
        // "conduit");
        GameRegistry.registerBlock(processor, ItemBlockBase.class, "processor");
        GameRegistry.registerBlock(naniteFarmland, "nanite_farmland");
        GameRegistry.registerBlock(fauxPocket, "faux_pocket");
        GameRegistry.registerBlock(battery, ItemBlockBattery.class, "battery");
        GameRegistry.registerBlock(generator, ItemBlockBase.class, "generator");
        GameRegistry.registerBlock(elevator, "teleporter");
        GameRegistry.registerBlock(hardlight, "hardlight");
        GameRegistry.registerBlock(hardlightSlab, "hardlight_slab");
        GameRegistry.registerBlock(hardlightPlatform, "hardlight_platform");
        GameRegistry.registerBlock(hardlightFence, "hardlight_fence");
        GameRegistry.registerBlock(hardlightPane, "hardlight_pane");
        GameRegistry.registerBlock(hardlightStairs, "hardlight_stairs");
        GameRegistry.registerBlock(amnioticFluid, ItemBlockFluid.class, "amniotic_fluid");
        GameRegistry.registerBlock(enrichedFluid, ItemBlockFluid.class, "enriched_fluid");
        GameRegistry.registerBlock(structure, ItemBlockBase.class, "structure");
        GameRegistry.registerBlock(tank, ItemBlockTank.class, "tank");
        GameRegistry.registerBlock(shifted, "shifted");
        GameRegistry.registerBlock(cultivator, ItemBlockBase.class, "cultivator");
        GameRegistry.registerBlock(analyser, "analyser");

        // Register TileEntities
        electromagnetic.registerTiles();
        processor.registerTiles();
        fauxPocket.registerTiles();
        battery.registerTiles();
        generator.registerTiles();
        elevator.registerTiles();
        structure.registerTiles();
        tank.registerTiles();
        shifted.registerTiles();
        cultivator.registerTiles();
        analyser.registerTiles();

        // Register with the ore dictionary
        OreDictionary.registerOre("oreEtherium", new ItemStack(resource, 1, 0));
        OreDictionary.registerOre("oreCopper", new ItemStack(resource, 1, 2));
        OreDictionary.registerOre("oreAluminium", new ItemStack(resource, 1, 3));
        OreDictionary.registerOre("oreAluminum", new ItemStack(resource, 1, 3));
    }
}
