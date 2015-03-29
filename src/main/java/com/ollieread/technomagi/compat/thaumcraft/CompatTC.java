package com.ollieread.technomagi.compat.thaumcraft;

import com.ollieread.technomagi.compat.thaumcraft.common.init.Blocks;
import com.ollieread.technomagi.compat.thaumcraft.common.init.Items;
import com.ollieread.technomagi.compat.thaumcraft.common.init.Recipes;

public class CompatTC
{

    public static void pre()
    {
        Blocks.init();
        Items.init();
        Recipes.init();
    }

    public static void init()
    {

    }

    public static void post()
    {

    }

}
