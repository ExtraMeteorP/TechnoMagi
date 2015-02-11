package com.ollieread.technomagi.api.energy;


/**
 * 
 * @author ollieread
 *
 */
public class EnergyHandler
{

    /**
     * These are the types of energy, along with their conversion to RF values.
     * 
     * @author ollieread
     *
     */
    public static enum EnergyType {
        LIFE(1.5F), LIGHT(0.25F), VOID(3F), HEAT(2F);

        public float conversion;

        private EnergyType(float conversion)
        {
            this.conversion = conversion;
        }
    }

}
