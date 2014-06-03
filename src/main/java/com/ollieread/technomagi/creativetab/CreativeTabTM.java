package com.ollieread.technomagi.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class CreativeTabTM extends CreativeTabs {

	public CreativeTabTM() {
		super("tabTm");
	}

	@Override
	public Item getTabIconItem() {
		return Items.iron_ingot;
	}

}
