package com.ollieread.technomagi.common;

import java.util.HashMap;
import java.util.Map;

import com.ollieread.technomagi.client.gui.GuiSpecialisation;
import com.ollieread.technomagi.player.PlayerKnowledge;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler {
	
	public static int GUI_TECHNOMAGI = 1;
	public static int GUI_SPECIALISATION = 2;
	
	private static final Map<String, NBTTagCompound> extendedEntityData = new HashMap<String, NBTTagCompound>();
	
	public static void storeEntityData(String name, NBTTagCompound compound)
	{
		extendedEntityData.put(name, compound);
	}
	
	public static NBTTagCompound getEntityData(String name)
	{
		return extendedEntityData.remove(name);
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == GUI_TECHNOMAGI) {
			PlayerKnowledge charon = PlayerKnowledge.get(player);
			
			if(charon.canSpecialise()) {
				return new GuiSpecialisation();
			} else {
				
			}
		}
		
		return null;
	}

}
