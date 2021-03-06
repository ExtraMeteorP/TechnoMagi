package com.ollieread.technomagi;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.launchwrapper.Launch;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Abilities;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Dimensions;
import com.ollieread.technomagi.common.init.Entities;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.init.Knowledge;
import com.ollieread.technomagi.common.init.Potions;
import com.ollieread.technomagi.common.init.Recipes;
import com.ollieread.technomagi.common.init.Research;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.creativetab.CreativeTabTM;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.world.gen.OreGen;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, version = Reference.VERSION, dependencies = "required-after:EnndsCore;required-after:CoFHAPI")
public class TechnoMagi
{

    @SidedProxy(clientSide = "com.ollieread.technomagi.client.ClientProxy", serverSide = "com.ollieread.technomagi.common.CommonProxy")
    public static CommonProxy proxy;

    @Instance(Reference.MODID)
    public static TechnoMagi instance;

    public static CreativeTabs tabTM = new CreativeTabTM();
    public static final Logger logger = LogManager.getLogger(Reference.MODID);
    public static boolean debug = false;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        Config.init(event.getSuggestedConfigurationFile());

        if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment") || Config.debugMode) {
            debug = true;
        }

        proxy.registerEventHandlers();

        PacketHandler.init();

        Specialisations.init();
        Items.init();
        Blocks.init();
        Entities.init();
        Research.init();
        Knowledge.init();
        Abilities.init();
        Recipes.init();
        Potions.init();
        Dimensions.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        GameRegistry.registerWorldGenerator(new OreGen(Blocks.blockResource, 0, 10, 70, 3, 3), 4);
        GameRegistry.registerWorldGenerator(new OreGen(Blocks.blockResource, 1, 0, 10, 3, 3), 4);

        Config.loadImmuneBlocks();
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        Information.load("info");
        Information.load("specialisations");
        Information.load("knowledge");
        Information.load("recipes");
        Information.load("abilities");
        Information.load("intrigue");
    }

    public static void debug(String message)
    {
        if (debug) {
            info(message);
        }
    }

    public static void info(String message)
    {
        logger.log(Level.INFO, message);
    }

    public static void error(String message)
    {
        logger.log(Level.ERROR, message);
    }

}
