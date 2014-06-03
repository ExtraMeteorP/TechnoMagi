package com.ollieread.technomagi.api.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

import com.ollieread.technomagi.block.BlockModule;

public interface IConstructModule {

	public Block getModuleBlock(ItemStack stack);
	
}
