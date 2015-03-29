package com.ollieread.technomagi.compat;

import com.ollieread.technomagi.compat.thaumcraft.CompatTC;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.SidedProxy;

public class Compat
{

    @SidedProxy(clientSide = "com.ollieread.technomagi.compat.CompatClientProxy", serverSide = "com.ollieread.technomagi.compat.CompatCommonProxy")
    public static CompatCommonProxy proxy;

    public static boolean thaumcraft = false;

    public static void loaded()
    {
        if (Loader.isModLoaded("Thaumcraft")) {
            thaumcraft = true;
        }
    }

    public static void pre()
    {
        if (thaumcraft) {
            CompatTC.pre();
        }
    }

    public static void init()
    {
        if (thaumcraft) {
            CompatTC.init();
        }
    }

    public static void post()
    {
        if (thaumcraft) {
            CompatTC.post();
        }
    }

}
