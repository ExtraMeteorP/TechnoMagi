package com.ollieread.technomagi;

import net.minecraft.launchwrapper.Launch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ollieread.technomagi.common.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Technomagi.MODID, version = Technomagi.VERSION, dependencies = "")
public class Technomagi
{

    public static final String MODID = "Technomagi";
    public static final String VERSION = "@VERSION@";
    public static final String CHANNEL = "TM";

    @SidedProxy(clientSide = "com.ollieread.technomagi.client.ClientProxy", serverSide = "com.ollieread.technomagi.common.CommonProxy")
    public static CommonProxy proxy;

    @Instance(Technomagi.MODID)
    public static Technomagi instance;

    public static final Logger logger = LogManager.getLogger(Technomagi.MODID);
    public static boolean debug = false;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
            debug = true;
        }
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {

    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {

    }

}
