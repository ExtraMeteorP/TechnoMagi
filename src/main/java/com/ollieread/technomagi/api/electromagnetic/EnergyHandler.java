package com.ollieread.technomagi.api.electromagnetic;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ollieread.technomagi.util.BlockRepresentation;
import com.ollieread.technomagi.util.ItemStackRepresentation;

public class EnergyHandler
{

    /**
     * The types of energy, along with their conversion to RF values.
     *
     * @author ollieread
     *
     */
    public static enum EnergyType {
        LIFE(1.5F), LIGHT(0.25F), VOID(3F), HEAT(2F);

        public final float conversion;

        private EnergyType(float conversion)
        {
            this.conversion = conversion;
        }
    }

    protected EnumMap<EnergyType, Map<Object, Integer>> energyValues = new EnumMap<EnergyType, Map<Object, Integer>>(EnergyType.class);

    public EnergyHandler()
    {
        for (EnergyType type : EnergyType.values()) {
            energyValues.put(type, new HashMap<Object, Integer>());
        }
    }

    public void addEnergy(Object representation, EnergyType type, int value)
    {
        if (!(representation instanceof ItemStackRepresentation) && !(representation instanceof BlockRepresentation)) {
            return;
        }

        energyValues.get(type).put(representation, value);
    }

    public Map<Object, Integer> getEnergyValues(EnergyType type)
    {
        return energyValues.get(type);
    }

    public int getEnergyValue(Object representation, EnergyType type)
    {
        if (representation instanceof ItemStackRepresentation || representation instanceof BlockRepresentation) {
            if (energyValues.get(type).containsKey(representation)) {
                return energyValues.get(type).get(representation);
            }
        }

        return 0;
    }

    public List<Integer> getEnergyValues(Object representation)
    {
        List<Integer> values = new ArrayList<Integer>();

        if (representation instanceof ItemStackRepresentation || representation instanceof BlockRepresentation) {
            for (EnergyType type : EnergyType.values()) {
                if (energyValues.get(type).containsKey(representation)) {
                    values.add(energyValues.get(type).get(representation));
                }
            }
        }

        return values;
    }

}
