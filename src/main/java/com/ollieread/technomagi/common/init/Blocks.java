package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.block.BlockArchive;
import com.ollieread.technomagi.block.BlockConstruct;
import com.ollieread.technomagi.block.BlockKnowledgeReceptacle;
import com.ollieread.technomagi.block.BlockNaniteReplicator;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class Blocks {
	
	public static Block blockConstruct;
	public static Block blockArchive;
	public static Block blockKnowledgeReceptacle;
	public static Block blockNaniteReplicator;

	public static void init()
	{
		blockConstruct = new BlockConstruct("blockConstruct");
		blockArchive = new BlockArchive("blockArchive");
		blockKnowledgeReceptacle = new BlockKnowledgeReceptacle("blockKnowledgeReceptacle");
		blockNaniteReplicator = new BlockNaniteReplicator("blockNaniteReplicator");
		
		GameRegistry.registerBlock(blockConstruct, "blockConstruct");
		GameRegistry.registerBlock(blockArchive, "blockArchive");
		GameRegistry.registerBlock(blockKnowledgeReceptacle, "blockKnowledgeReceptacle");
		GameRegistry.registerBlock(blockNaniteReplicator, "blockNaniteReplicator");
	}
	
}
