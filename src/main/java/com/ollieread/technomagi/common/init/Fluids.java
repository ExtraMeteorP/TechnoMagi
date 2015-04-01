package com.ollieread.technomagi.common.init;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class Fluids
{

    public static Fluid amniotic;
    public static Fluid enriched;

    public static void init()
    {
        amniotic = new Fluid("amniotic_fluid");
        enriched = new Fluid("enriched_fluid").setLuminosity(10);

        FluidRegistry.registerFluid(amniotic);
        FluidRegistry.registerFluid(enriched);
    }
}
