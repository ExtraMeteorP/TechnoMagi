package com.ollieread.technomagi.common.init;

import net.minecraft.block.Block;

import org.apache.logging.log4j.Level;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.block.BlockAnalysis;
import com.ollieread.technomagi.block.BlockArchive;
import com.ollieread.technomagi.block.BlockAreaLight;
import com.ollieread.technomagi.block.BlockConstruct;
import com.ollieread.technomagi.block.BlockCrafting;
import com.ollieread.technomagi.block.BlockDisplacedAir;
import com.ollieread.technomagi.block.BlockDisplacer;
import com.ollieread.technomagi.block.BlockEmptyFiller;
import com.ollieread.technomagi.block.BlockEtheriumOre;
import com.ollieread.technomagi.block.BlockGenerator;
import com.ollieread.technomagi.block.BlockHardlight;
import com.ollieread.technomagi.block.BlockHardlightFence;
import com.ollieread.technomagi.block.BlockHardlightGenerator;
import com.ollieread.technomagi.block.BlockLightAir;
import com.ollieread.technomagi.block.BlockNaniteReplicator;
import com.ollieread.technomagi.block.BlockObservationChamber;
import com.ollieread.technomagi.block.BlockReactiveCrafting;
import com.ollieread.technomagi.block.BlockTeleporter;
import com.ollieread.technomagi.block.BlockVoidstone;
import com.ollieread.technomagi.item.ItemBlockTM;
import com.ollieread.technomagi.tileentity.TileEntityAnalysis;
import com.ollieread.technomagi.tileentity.TileEntityArchive;
import com.ollieread.technomagi.tileentity.TileEntityAreaLight;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;
import com.ollieread.technomagi.tileentity.TileEntityCrafting;
import com.ollieread.technomagi.tileentity.TileEntityDisplacedAir;
import com.ollieread.technomagi.tileentity.TileEntityDisplacer;
import com.ollieread.technomagi.tileentity.TileEntityEmptyFiller;
import com.ollieread.technomagi.tileentity.TileEntityGeneratorBasic;
import com.ollieread.technomagi.tileentity.TileEntityLightAir;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;
import com.ollieread.technomagi.tileentity.TileEntityObservationChamber;
import com.ollieread.technomagi.tileentity.TileEntityReactiveCrafting;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;

import cpw.mods.fml.common.registry.GameRegistry;

public class Blocks
{

    public static Block blockConstruct;
    public static Block blockArchive;
    public static Block blockNaniteReplicator;
    public static Block blockAreaLight;
    public static Block blockLightAir;
    public static Block blockTeleporter;
    public static Block blockObservationChamber;
    public static Block blockEmptyFiller;
    public static Block blockCrafting;
    public static Block blockAnalysis;
    public static Block blockHardlight;
    public static Block blockHardlightFence;
    public static Block blockHardlightGenerator;
    public static Block blockDisplacer;
    public static Block blockDisplacedAir;
    public static Block blockGenerator;
    public static Block blockReactiveCrafting;
    public static Block blockEtheriumOre;
    public static Block blockVoidstone;

    public static void init()
    {
        TechnoMagi.logger.log(Level.INFO, "Initiating & registering blocks");

        blockConstruct = new BlockConstruct("construct");
        blockArchive = new BlockArchive("archive");
        blockNaniteReplicator = new BlockNaniteReplicator("naniteReplicator");
        blockAreaLight = new BlockAreaLight("areaLight");
        blockLightAir = new BlockLightAir("lightAir");
        blockTeleporter = new BlockTeleporter("teleporter");
        blockObservationChamber = new BlockObservationChamber("observationChamber");
        blockEmptyFiller = new BlockEmptyFiller("emptyFiller");
        blockCrafting = new BlockCrafting("crafting");
        blockAnalysis = new BlockAnalysis("analysis");
        blockHardlight = new BlockHardlight("hardlight");
        blockHardlightFence = new BlockHardlightFence("hardlightFence");
        blockHardlightGenerator = new BlockHardlightGenerator("hardlightGenerator");
        blockDisplacer = new BlockDisplacer("displacer");
        blockDisplacedAir = new BlockDisplacedAir("displacedAir");
        blockGenerator = new BlockGenerator("generator");
        blockReactiveCrafting = new BlockReactiveCrafting("reactiveCrafting");
        blockEtheriumOre = new BlockEtheriumOre("etheriumOre");
        blockVoidstone = new BlockVoidstone("voidstone");

        GameRegistry.registerBlock(blockConstruct, "construct");
        GameRegistry.registerBlock(blockArchive, "archive");
        GameRegistry.registerBlock(blockCrafting, "crafting");
        GameRegistry.registerBlock(blockNaniteReplicator, "naniteReplicator");
        GameRegistry.registerBlock(blockAnalysis, "analysis");
        GameRegistry.registerBlock(blockAreaLight, "areaLight");
        GameRegistry.registerBlock(blockLightAir, "lightAir");
        GameRegistry.registerBlock(blockTeleporter, ItemBlockTM.class, "teleporter");
        GameRegistry.registerBlock(blockObservationChamber, "observationChamber");
        GameRegistry.registerBlock(blockEmptyFiller, "emptyFiller");
        GameRegistry.registerBlock(blockHardlight, "hardlight");
        GameRegistry.registerBlock(blockHardlightFence, "hardlightFence");
        GameRegistry.registerBlock(blockHardlightGenerator, "hardlightGenerator");
        GameRegistry.registerBlock(blockDisplacer, "displacer");
        GameRegistry.registerBlock(blockDisplacedAir, "displacedAir");
        GameRegistry.registerBlock(blockGenerator, ItemBlockTM.class, "generator");
        GameRegistry.registerBlock(blockReactiveCrafting, "reactiveCrafting");
        GameRegistry.registerBlock(blockEtheriumOre, "etheriumOre");
        GameRegistry.registerBlock(blockVoidstone, "voidstone");

        GameRegistry.registerTileEntity(TileEntityArchive.class, "tileEntityArchive");
        GameRegistry.registerTileEntity(TileEntityNaniteReplicator.class, "tileEntityNaniteReplicator");
        GameRegistry.registerTileEntity(TileEntityLightAir.class, "tileEntityLightAir");
        GameRegistry.registerTileEntity(TileEntityAreaLight.class, "tileEntityAreaLight");
        GameRegistry.registerTileEntity(TileEntityTeleporter.class, "tileEntityTeleporter");
        GameRegistry.registerTileEntity(TileEntityObservationChamber.class, "tileEntityObservationChamber");
        GameRegistry.registerTileEntity(TileEntityEmptyFiller.class, "tileEntityEmptyFiller");
        GameRegistry.registerTileEntity(TileEntityCrafting.class, "tileEntityCrafting");
        GameRegistry.registerTileEntity(TileEntityAnalysis.class, "tileEntityAnalysis");
        GameRegistry.registerTileEntity(TileEntityDisplacer.class, "tileEntityDisplacer");
        GameRegistry.registerTileEntity(TileEntityDisplacedAir.class, "tileEntityDisplacedAir");
        GameRegistry.registerTileEntity(TileEntityGeneratorBasic.class, "tileEntityGeneratorBasic");
        GameRegistry.registerTileEntity(TileEntityConstruct.class, "tileEntityConstruct");
        GameRegistry.registerTileEntity(TileEntityReactiveCrafting.class, "tileEntityReactiveCrafting");
    }
}
