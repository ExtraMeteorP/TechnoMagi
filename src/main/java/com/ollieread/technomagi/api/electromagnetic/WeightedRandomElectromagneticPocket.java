package com.ollieread.technomagi.api.electromagnetic;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.ollieread.technomagi.api.electromagnetic.EnergyHandler.EnergyType;

public class WeightedRandomElectromagneticPocket
{

    protected Map<Integer, EnumMap<EnergyHandler.EnergyType, Integer>> biomePockets = new HashMap<Integer, EnumMap<EnergyHandler.EnergyType, Integer>>();

    public WeightedRandomElectromagneticPocket()
    {
        EnumMap<EnergyHandler.EnergyType, Integer> desert = new EnumMap<EnergyHandler.EnergyType, Integer>(EnergyHandler.EnergyType.class);
    }
}
