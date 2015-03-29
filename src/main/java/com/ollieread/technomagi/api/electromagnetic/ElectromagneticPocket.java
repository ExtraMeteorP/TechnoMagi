package com.ollieread.technomagi.api.electromagnetic;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChunkCoordinates;

public class ElectromagneticPocket
{

    /**
     * The available sizes for eletromagnetic pockets, including the maximum
     * radius.
     *
     * @author ollieread
     *
     */
    public static enum PocketSize {
        SMALL(16), MEDIUM(32), LARGE(64);

        public final int maxRadius;

        private PocketSize(int maxRadius)
        {
            this.maxRadius = maxRadius;
        }

        public static PocketSize getPocketSize(int radius)
        {
            PocketSize pocketSize = null;

            if (radius > 16) {
                if (radius > 32) {
                    pocketSize = LARGE;
                } else {
                    pocketSize = MEDIUM;
                }
            } else {
                pocketSize = SMALL;
            }

            return pocketSize;
        }
    }

    public ChunkCoordinates coordinates;
    public int dimension;
    public boolean negative;
    public PocketSize size;
    public EnergyHandler.EnergyType type;

    public ElectromagneticPocket()
    {
    }

    public ElectromagneticPocket(int dimension, int x, int y, int z, PocketSize size, EnergyHandler.EnergyType type, boolean negative)
    {
        this.dimension = dimension;
        this.coordinates = new ChunkCoordinates(x, y, z);
        this.size = size;
        this.type = type;
        this.negative = negative;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setInteger("Dimension", dimension);
        compound.setInteger("PosX", coordinates.posX);
        compound.setInteger("PosY", coordinates.posY);
        compound.setInteger("PosZ", coordinates.posZ);
        compound.setBoolean("Negative", negative);
        compound.setInteger("Size", size.ordinal());
        compound.setInteger("Type", type.ordinal());

        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        dimension = compound.getInteger("Dimension");
        coordinates = new ChunkCoordinates(compound.getInteger("PosX"), compound.getInteger("PosY"), compound.getInteger("PosZ"));
        negative = compound.getBoolean("Negative");
        size = PocketSize.values()[compound.getInteger("Size")];
        type = EnergyHandler.EnergyType.values()[compound.getInteger("Type")];
    }

}
