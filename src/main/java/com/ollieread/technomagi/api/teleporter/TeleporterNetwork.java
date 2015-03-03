package com.ollieread.technomagi.api.teleporter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterAnchor;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterBeacon;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterBouncer;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterNetworked;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterRegulator;

/**
 * This class represents a single teleporter network.
 * 
 * This class only stores that which is necessary to identify and operate the
 * basics of a teleporter networking, including the locations of teleporter
 * within the network, and their types. Individual access restriction, ownership
 * and whether or not an actual teleporter can be used, is controlled by the
 * teleporter itself.
 * 
 * Each teleporter is responsible for adding and removing itself from a network.
 * 
 * @author ollieread
 *
 */
public class TeleporterNetwork
{

    /**
     * The type of teleporter, used to identify the way it should be displayed
     * as well as few other things, as certain types cannot be jumped to, but
     * jumped through.
     * 
     * @author ollieread
     *
     */
    public static enum TeleporterType {

        BOUNCER(TileTeleporterBouncer.class),
        ANCHOR(TileTeleporterAnchor.class),
        BEACON(TileTeleporterBeacon.class);

        public final Class<? extends TileTeleporterNetworked> teleporterClass;

        private TeleporterType(Class<? extends TileTeleporterNetworked> teleporterClass)
        {
            this.teleporterClass = teleporterClass;
        }

        /**
         * Get the matching teleporter type for the class provided.
         * 
         * @param teleporterClass
         * @return
         */
        public static TeleporterType getTeleporterType(Class teleporterClass)
        {
            TeleporterType[] types = TeleporterType.values();

            for (int i = 0; i < types.length; i++) {
                if (types[i].teleporterClass.equals(teleporterClass)) {
                    return types[i];
                }
            }

            return null;
        }

    }

    /**
     * This class represents an individual teleporter, storing its dimension,
     * coordinates, type and whether or not it is enabled.
     * 
     * @author ollieread
     *
     */
    public static class Teleporter implements Comparable
    {

        public int posX;
        public int posY;
        public int posZ;
        public int dimension;
        public TeleporterType type;
        /**
         * This is here so that we don't have to load the TileEntity when
         * displaying the map.
         */
        public boolean enabled = true;

        public Teleporter(int dimension, int x, int y, int z, TeleporterType type)
        {
            this.posX = x;
            this.posY = y;
            this.posZ = z;
            this.dimension = dimension;
            this.type = type;
        }

        public Teleporter(TileTeleporterNetworked teleporter)
        {
            this.posX = teleporter.xCoord;
            this.posY = teleporter.yCoord;
            this.posZ = teleporter.zCoord;
            this.dimension = teleporter.getWorldObj().provider.dimensionId;
            this.type = TeleporterType.getTeleporterType(teleporter.getClass());
        }

        /**
         * Sometimes a none supported teleporter could be passed in, in that
         * case, the type would be null. This is used as a sanity check when
         * adding a teleporter to the network.
         * 
         * @return
         */
        public boolean isValid()
        {
            return type != null;
        }

        public float getDistance(int x, int z)
        {
            float xd = this.posX - x;
            float zd = this.posZ - z;

            return MathHelper.sqrt_double(xd * xd + zd * zd);
        }

        public float getDistanceSquared(int x, int y, int z)
        {
            float f = posX - x;
            float f1 = posY - y;
            float f2 = posZ - z;

            return f * f + f1 * f1 + f2 * f2;
        }

        public TileTeleporterNetworked getTeleporter(World world)
        {
            TileEntity tile = world.getTileEntity(posX, posY, posZ);

            if (tile != null && tile instanceof TileTeleporterNetworked) {
                return (TileTeleporterNetworked) tile;
            } else {
                /*
                 * This is where we'd automatically invalidate this teleporter,
                 * but there are extenuating circumstances that could lead to
                 * this tile not loading.
                 */
                // this.type = null;
            }

            return null;
        }

        public World getWorld()
        {
            if (Technomagi.proxy.isClient()) {
                return null;
            }

            return MinecraftServer.getServer().worldServerForDimension(dimension);
        }

        public void enable()
        {
            enabled = true;
        }

        public void disable()
        {
            enabled = false;
        }

        public boolean isEnabled()
        {
            return enabled;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (!(obj instanceof Teleporter)) {
                if (obj instanceof ChunkCoordinates) {
                    ChunkCoordinates coords = (ChunkCoordinates) obj;
                    return posX == coords.posX && posY == coords.posY && posZ == coords.posZ;
                }

                return false;
            }

            Teleporter teleporter = (Teleporter) obj;

            return posX == teleporter.posX && posY == teleporter.posY && posZ == teleporter.posZ && dimension == teleporter.dimension && type.equals(teleporter.type);
        }

        @Override
        public int compareTo(Object obj)
        {
            return compareTo(obj);
        }

        public int compareTo(Teleporter teleporter)
        {
            return posZ == teleporter.posZ ? posX - teleporter.posX : posZ - teleporter.posZ;
        }

        public NBTTagCompound writeToNBT(NBTTagCompound compound)
        {
            compound.setInteger("PosX", posX);
            compound.setInteger("PosY", posY);
            compound.setInteger("PosZ", posZ);
            compound.setInteger("Dimension", dimension);
            compound.setInteger("Type", type.ordinal());
            compound.setBoolean("Enabled", enabled);

            return compound;
        }

        public static Teleporter createFromNBT(NBTTagCompound compound)
        {
            Teleporter teleporter = new Teleporter(compound.getInteger("Dimension"), compound.getInteger("PosX"), compound.getInteger("PosY"), compound.getInteger("PosZ"), TeleporterType.values()[compound.getInteger("Type")]);

            if (compound.getBoolean("Enabled")) {
                teleporter.enable();
            }

            return teleporter;
        }
    }

    /**
     * The coordinates for the regulator, which is multiblock structure
     * responsible for controlling the teleporter network.
     */
    protected ChunkCoordinates regulator;
    /**
     * This is the dimension that the regulator is in.
     */
    protected int dimension;
    /**
     * The name of the network, used to assign.
     */
    protected String name;
    protected boolean enabled = false;
    /**
     * All the teleporters in the network, separated by dimension.
     */
    protected Map<Integer, List<Teleporter>> teleporters = new ConcurrentHashMap<Integer, List<Teleporter>>();

    public TeleporterNetwork()
    {
    }

    public TeleporterNetwork(TileTeleporterRegulator regulator)
    {
        this(regulator.getNetwork(), regulator.getWorldObj().provider.dimensionId, regulator.xCoord, regulator.yCoord, regulator.zCoord);
    }

    public TeleporterNetwork(String name, int dimension, int x, int y, int z)
    {
        this.name = name;
        this.dimension = dimension;
        this.regulator = new ChunkCoordinates(x, y, z);
    }

    public void enable()
    {
        enabled = true;
    }

    public void disable()
    {
        enabled = false;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public int getDimension()
    {
        return dimension;
    }

    public TileTeleporterRegulator getRegulator(World world)
    {
        TileEntity tile = world.getTileEntity(regulator.posX, regulator.posY, regulator.posZ);

        if (tile != null && tile instanceof TileTeleporterRegulator) {
            return (TileTeleporterRegulator) tile;
        }

        return null;
    }

    public void addTeleporter(TileTeleporterNetworked tile)
    {
        Teleporter teleporter = new Teleporter(tile);

        if (teleporter.isValid()) {
            if (!teleporters.containsKey(teleporter.dimension)) {
                teleporters.put(teleporter.dimension, new ArrayList<Teleporter>());
            } else {
                if (teleporters.get(teleporter.dimension).contains(teleporter)) {
                    return;
                }
            }

            teleporters.get(teleporter.dimension).add(teleporter);
            // Sort the teleporters list so that we can map simply
            Collections.sort(teleporters.get(teleporter.dimension));
        }
    }

    public Teleporter getTeleporter(int dimension, int x, int y, int z)
    {
        if (teleporters.containsKey(dimension) && teleporters.get(dimension).contains(new ChunkCoordinates(x, y, z))) {
            return teleporters.get(dimension).get(teleporters.get(dimension).indexOf(new ChunkCoordinates(x, y, z)));
        }

        return null;
    }

    public List<Teleporter> getTeleporters(int dimension)
    {
        if (teleporters.containsKey(dimension)) {
            return teleporters.get(dimension);
        }

        return null;
    }

    public void removeTeleporter(TileTeleporterNetworked tile)
    {
        Teleporter teleporter = new Teleporter(tile);

        if (teleporter.isValid()) {
            if (!teleporters.containsKey(teleporter.dimension)) {
                return;
            } else {
                if (!teleporters.get(teleporter.dimension).contains(teleporter)) {
                    return;
                }
            }

            teleporters.get(teleporter.dimension).remove(teleporter);
            Collections.sort(teleporters.get(teleporter.dimension));
        }
    }

    public void removeNetwork()
    {
        if (Technomagi.proxy.isClient()) {
            return;
        }

        for (Integer dimension : teleporters.keySet()) {
            for (Teleporter teleporter : teleporters.get(dimension)) {
                WorldServer world = MinecraftServer.getServer().worldServerForDimension(dimension);

                if (world != null) {
                    TileEntity tile = world.getTileEntity(teleporter.posX, teleporter.posY, teleporter.posZ);

                    if (tile != null && tile instanceof TileTeleporterNetworked) {
                        ((TileTeleporterNetworked) tile).resetNetwork();
                    }
                }
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        compound.setString("Name", name);
        compound.setInteger("PosX", regulator.posX);
        compound.setInteger("PosY", regulator.posY);
        compound.setInteger("PosZ", regulator.posZ);
        compound.setBoolean("Enabled", enabled);

        NBTTagList dimensionList = new NBTTagList();

        for (Integer dimension : teleporters.keySet()) {
            NBTTagCompound dimensionCompound = new NBTTagCompound();
            dimensionCompound.setInteger("Dimension", dimension);
            NBTTagList teleporterList = new NBTTagList();

            for (Teleporter teleporter : teleporters.get(dimension)) {
                teleporterList.appendTag(teleporter.writeToNBT(new NBTTagCompound()));
            }

            dimensionCompound.setTag("Teleporters", teleporterList);
            dimensionList.appendTag(dimensionCompound);
        }

        compound.setTag("Network", dimensionList);

        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        name = compound.getString("Name");
        regulator = new ChunkCoordinates(compound.getInteger("PosX"), compound.getInteger("PosY"), compound.getInteger("PosZ"));
        enabled = compound.getBoolean("Enabled");

        teleporters = new ConcurrentHashMap<Integer, List<Teleporter>>();

        NBTTagList dimensionList = compound.getTagList("Network", compound.getId());

        for (int i = 0; i < dimensionList.tagCount(); i++) {
            NBTTagCompound dimensionCompound = dimensionList.getCompoundTagAt(i);
            NBTTagList teleporterList = dimensionCompound.getTagList("Teleporters", dimensionCompound.getId());
            List<Teleporter> dimensionTeleporters = new ArrayList<Teleporter>();

            for (int x = 0; x < teleporterList.tagCount(); x++) {
                NBTTagCompound teleporterCompound = teleporterList.getCompoundTagAt(x);
                Teleporter teleporter = Teleporter.createFromNBT(teleporterCompound);

                if (teleporter.isValid()) {
                    dimensionTeleporters.add(teleporter);
                }
            }

            teleporters.put(dimensionCompound.getInteger("Dimension"), dimensionTeleporters);
        }
    }

}
