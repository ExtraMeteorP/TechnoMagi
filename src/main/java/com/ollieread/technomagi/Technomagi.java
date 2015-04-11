package com.ollieread.technomagi;

import net.minecraft.launchwrapper.Launch;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.init.Abilities;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.common.init.Dimensions;
import com.ollieread.technomagi.common.init.Entities;
import com.ollieread.technomagi.common.init.Fluids;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.common.init.Potions;
import com.ollieread.technomagi.common.init.Progression;
import com.ollieread.technomagi.common.init.Recipes;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.common.network.PacketHandler;
import com.ollieread.technomagi.common.world.gen.TechnomagiElectromagneticGen;
import com.ollieread.technomagi.common.world.gen.TechnomagiOreGen;
import com.ollieread.technomagi.compat.Compat;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Technomagi.MODID, version = Technomagi.VERSION, dependencies = "required-after:CoFHAPI")
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
        Config.init(event.getSuggestedConfigurationFile());

        if ((Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
            debug = true;
        }

        proxy.registerEventHandlers();

        PacketHandler.init();

        Entities.init();

        Fluids.init();
        Items.init();
        Blocks.init();

        Potions.init();
        Dimensions.init();

        Specialisations.init();
        Progression.init();
        Abilities.init();

        Recipes.init();

        proxy.registerRenderers();

        Compat.loaded();
        Compat.pre();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();
        // Register the proxy as the GUI handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        // Generate Etherium ore
        GameRegistry.registerWorldGenerator(new TechnomagiOreGen(Blocks.resource, 0, 12, 6, 10, 250), 4);
        GameRegistry.registerWorldGenerator(new TechnomagiOreGen(Blocks.resource, 1, 3, 3, 0, 10), 4);
        GameRegistry.registerWorldGenerator(new TechnomagiOreGen(Blocks.resource, 2, 8, 5, 0, 70), 4);
        GameRegistry.registerWorldGenerator(new TechnomagiOreGen(Blocks.resource, 3, 5, 4, 0, 50), 4);

        GameRegistry.registerWorldGenerator(new TechnomagiElectromagneticGen(Blocks.electromagnetic, 1, 128), 4);
        // Config.loadImmuneBlocks();

        Compat.init();
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        Compat.post();
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
