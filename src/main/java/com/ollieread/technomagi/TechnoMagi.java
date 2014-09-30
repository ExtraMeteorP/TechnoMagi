package com.ollieread.technomagi;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Information;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Abilities;
import com.ollieread.technomagi.common.init.Blocks;
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

    public static Configuration config;

    @EventHandler
    public void pre(FMLPreInitializationEvent event)
    {
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();

        proxy.registerEventHandlers();

        PacketHandler.init();

        Specialisations.init();
        Research.init();
        Knowledge.init();
        Abilities.init();
        Entities.init();

        Items.init();
        Blocks.init();
        Recipes.init();

        config.save();

        Potion[] potionTypes = null;

        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);

            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionType = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionType, 0, potionTypes.length);
                    f.set(null, newPotionType);
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author");
                System.err.println(e);
            }
        }

        Potions.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.init();

        NetworkRegistry.INSTANCE.registerGuiHandler(instance, proxy);
        GameRegistry.registerWorldGenerator(new OreGen(Blocks.blockEtheriumOre, 10, 70, 3, 3), 4);
        GameRegistry.registerWorldGenerator(new OreGen(Blocks.blockVoidstone, 0, 10, 3, 3), 4);
    }

    @EventHandler
    public void post(FMLPostInitializationEvent event)
    {
        Information.load("info");
        Information.load("specialisations");
        Information.load("knowledge");
        Information.load("recipes");
        Information.load("abilities");
    }

}
