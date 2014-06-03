package com.ollieread.technomagi;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.ollieread.technomagi.api.Specialisation;
import com.ollieread.technomagi.client.gui.GuiTMOverlay;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.KeyBindings;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Abilities;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.init.Knowledge;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.creativetab.CreativeTabTM;
import com.ollieread.technomagi.event.handler.KeyInputHandler;
import com.ollieread.technomagi.event.handler.MouseEventHandler;
import com.ollieread.technomagi.event.handler.PlayerEventHandler;
import com.ollieread.technomagi.event.handler.TMEventHandler;
import com.ollieread.technomagi.event.handler.TickEventHandler;
import com.ollieread.technomagi.network.PacketHandler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = Reference.MODID, version= Reference.VERSION)
public class TechnoMagi {
	
	@SidedProxy(clientSide = "com.ollieread.technomagi.client.ClientProxy", serverSide = "com.ollieread.technomagi.common.CommonProxy")
    public static CommonProxy proxy;
	
	@Instance("TechnoMagi")
	public static TechnoMagi instance;
	
	public static CreativeTabs tabTM = new CreativeTabTM();
	
	public static final Logger logger = LogManager.getLogger(Reference.MODID);
	
	@EventHandler
	public void pre(FMLPreInitializationEvent event)
	{ 		
		PacketHandler.init();
		Specialisations.init();
		Knowledge.init();
		Abilities.init();
		
		MinecraftForge.EVENT_BUS.register(new PlayerEventHandler());
		MinecraftForge.EVENT_BUS.register(new TMEventHandler());
		MinecraftForge.EVENT_BUS.register(new MouseEventHandler());
		FMLCommonHandler.instance().bus().register(new TickEventHandler());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{		
		FMLCommonHandler.instance().bus().register(new KeyInputHandler());
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
		
		KeyBindings.init();
		Blocks.init();
		Items.init();
		
		MinecraftForge.EVENT_BUS.register(new GuiTMOverlay(Minecraft.getMinecraft()));		
	}
	
	@EventHandler
	public void post(FMLPostInitializationEvent event)
	{
		Information.load("specialisations");
	}

}
