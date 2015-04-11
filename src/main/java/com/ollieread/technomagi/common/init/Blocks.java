package com.ollieread.technomagi.common.init;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.BlockResource;
import com.ollieread.technomagi.common.block.BlockSubtypes;
import com.ollieread.technomagi.common.block.conduit.BlockConduit;
import com.ollieread.technomagi.common.block.conduit.tile.TileConduitFluid;
import com.ollieread.technomagi.common.block.conduit.tile.TileConduitPower;
import com.ollieread.technomagi.common.block.electromagnetic.BlockElectromagnetic;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;
import com.ollieread.technomagi.common.block.energy.BlockBattery;
import com.ollieread.technomagi.common.block.energy.BlockGenerator;
import com.ollieread.technomagi.common.block.energy.tile.TileBattery;
import com.ollieread.technomagi.common.block.energy.tile.TileGeneratorBasic;
import com.ollieread.technomagi.common.block.energy.tile.TileGeneratorEnhanced;
import com.ollieread.technomagi.common.block.fluid.BlockAmnioticFluid;
import com.ollieread.technomagi.common.block.fluid.BlockEnrichedFluid;
import com.ollieread.technomagi.common.block.fluid.BlockTank;
import com.ollieread.technomagi.common.block.fluid.tile.TileTank;
import com.ollieread.technomagi.common.block.machine.BlockFauxPocket;
import com.ollieread.technomagi.common.block.machine.BlockResourceProcessor;
import com.ollieread.technomagi.common.block.machine.tile.TileFauxPocket;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorBasic;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorElectric;
import com.ollieread.technomagi.common.block.machine.tile.TileResourceProcessorNanite;
import com.ollieread.technomagi.common.block.structure.BlockHardlight;
import com.ollieread.technomagi.common.block.structure.BlockHardlightFence;
import com.ollieread.technomagi.common.block.structure.BlockHardlightPane;
import com.ollieread.technomagi.common.block.structure.BlockHardlightPlatform;
import com.ollieread.technomagi.common.block.structure.BlockHardlightSlab;
import com.ollieread.technomagi.common.block.structure.BlockStructure;
import com.ollieread.technomagi.common.block.structure.tile.TileStructureBridge;
import com.ollieread.technomagi.common.block.structure.tile.TileStructurePlatform;
import com.ollieread.technomagi.common.block.teleporter.BlockElevator;
import com.ollieread.technomagi.common.block.teleporter.tile.TileElevator;
import com.ollieread.technomagi.common.block.world.BlockNaniteFarmland;
import com.ollieread.technomagi.common.item.block.ItemBlockBase;
import com.ollieread.technomagi.common.item.block.ItemBlockBattery;
import com.ollieread.technomagi.common.item.block.ItemBlockConduit;
import com.ollieread.technomagi.common.item.block.ItemBlockFluid;
import com.ollieread.technomagi.common.item.block.ItemBlockTank;

import cpw.mods.fml.common.registry.GameRegistry;

public class Blocks
{

    public static BlockSubtypes resource;
    public static Block electromagnetic;
    public static BlockContainerSubtypes conduit;
    public static BlockContainerSubtypes processor;
    public static Block naniteFarmland;
    public static Block fauxPocket;
    public static Block battery;
    public static BlockContainerSubtypes generator;
    public static Block elevator;
    public static Block hardlight;
    public static Block hardlightSlab;
    public static Block hardlightPlatform;
    public static Block hardlightFence;
    public static Block hardlightPane;
    public static Block amnioticFluid;
    public static Block enrichedFluid;
    public static BlockContainerSubtypes structure;
    public static BlockContainerSubtypes tank;

    public static void init()
    {
        resource = new BlockResource("resource");
        resource.setHardness(5.0F).setResistance(10.0F);
        electromagnetic = new BlockElectromagnetic("electromagnetic");
        conduit = new BlockConduit("conduit");
        conduit.setHardness(3.5F).setResistance(5.0F);
        processor = new BlockResourceProcessor("processor");
        processor.setHardness(3.5F);
        naniteFarmland = new BlockNaniteFarmland("nanite_farmland");
        fauxPocket = new BlockFauxPocket("faux_pocket");
        battery = new BlockBattery("battery").setLightLevel(8F).setHardness(3.5F);
        generator = new BlockGenerator("generator");
        elevator = new BlockElevator("elevator").setHardness(3.5F);
        hardlight = new BlockHardlight("hardlight");
        hardlightSlab = new BlockHardlightSlab("hardlight_slab");
        hardlightPlatform = new BlockHardlightPlatform("hardlight_platform");
        hardlightFence = new BlockHardlightFence("hardlight_fence");
        hardlightPane = new BlockHardlightPane("hardlight_pane");
        amnioticFluid = new BlockAmnioticFluid("amniotic_fluid");
        enrichedFluid = new BlockEnrichedFluid("enriched_fluid");
        structure = new BlockStructure("structure");
        structure.setHardness(3.5F);
        tank = new BlockTank("tank");
        tank.setHardness(3.5F);

        // Register Blocks
        GameRegistry.registerBlock(resource, ItemBlockBase.class, "resource");
        GameRegistry.registerBlock(electromagnetic, "electromagnetic");
        GameRegistry.registerBlock(conduit, ItemBlockConduit.class, "conduit");
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
        GameRegistry.registerBlock(amnioticFluid, ItemBlockFluid.class, "amniotic_fluid");
        GameRegistry.registerBlock(enrichedFluid, ItemBlockFluid.class, "enriched_fluid");
        GameRegistry.registerBlock(structure, ItemBlockBase.class, "structure");
        GameRegistry.registerBlock(tank, ItemBlockTank.class, "tank");

        // Register TileEntities
        GameRegistry.registerTileEntity(TileElectromagnetic.class, "electromagnetic");
        GameRegistry.registerTileEntity(TileConduitFluid.class, "conduit_fluid");
        GameRegistry.registerTileEntity(TileConduitPower.class, "conduit_power");
        GameRegistry.registerTileEntity(TileResourceProcessorBasic.class, "processor_basic");
        GameRegistry.registerTileEntity(TileResourceProcessorElectric.class, "processor_electric");
        GameRegistry.registerTileEntity(TileResourceProcessorNanite.class, "processor_nanite");
        GameRegistry.registerTileEntity(TileFauxPocket.class, "faux_pocket");
        GameRegistry.registerTileEntity(TileBattery.class, "battery");
        GameRegistry.registerTileEntity(TileGeneratorBasic.class, "basic_generator");
        GameRegistry.registerTileEntity(TileGeneratorEnhanced.class, "enhanced_generator");
        GameRegistry.registerTileEntity(TileElevator.class, "teleporter");
        GameRegistry.registerTileEntity(TileStructurePlatform.class, "platform");
        GameRegistry.registerTileEntity(TileStructureBridge.class, "bridge");
        GameRegistry.registerTileEntity(TileTank.class, "tank");

        // Register with the ore dictionary
        OreDictionary.registerOre("oreEtherium", new ItemStack(resource, 1, 0));
        OreDictionary.registerOre("oreCopper", new ItemStack(resource, 1, 2));
        OreDictionary.registerOre("oreAluminium", new ItemStack(resource, 1, 3));
        OreDictionary.registerOre("oreAluminum", new ItemStack(resource, 1, 3));
    }
}
