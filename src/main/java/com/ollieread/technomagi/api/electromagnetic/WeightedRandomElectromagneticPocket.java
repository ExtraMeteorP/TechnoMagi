package com.ollieread.technomagi.api.electromagnetic;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;

public class WeightedRandomElectromagneticPocket
{

    protected Map<Integer, EnumMap<EnergyType, Integer>> biomePockets = new HashMap<Integer, EnumMap<EnergyType, Integer>>();

    public WeightedRandomElectromagneticPocket()
    {
        EnumMap<EnergyType, Integer> desert = new EnumMap<EnergyType, Integer>(EnergyType.class);
    }
}
