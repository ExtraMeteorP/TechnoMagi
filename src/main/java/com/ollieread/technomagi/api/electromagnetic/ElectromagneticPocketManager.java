package com.ollieread.technomagi.api.electromagnetic;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;

import com.ollieread.technomagi.api.electromagnetic.EnergyHandler.EnergyType;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;

public class ElectromagneticPocketManager extends WorldSavedData
{

    private static final String IDENT = "Technomagi_ElectromagneticPockets";

    protected int dimension;
    protected List<ElectromagneticPocket> pockets = new ArrayList<ElectromagneticPocket>();

    public ElectromagneticPocketManager()
    {
        super(IDENT);
    }

    public void addPocket(TileElectromagnetic tile)
    {
        int dimension = tile.getWorldObj().provider.dimensionId;

        ElectromagneticPocket pocket = new ElectromagneticPocket(dimension, tile.xCoord, tile.yCoord, tile.zCoord, tile.getSize(), tile.getType(), tile.isNegative());

        if (!pockets.contains(pocket)) {
            pockets.add(pocket);
        }
    }

    public void removePocket(TileElectromagnetic tile)
    {
        removePocket(tile.getWorldObj().provider.dimensionId, new ElectromagneticPocket(tile.getWorldObj().provider.dimensionId, tile.xCoord, tile.yCoord, tile.zCoord, tile.getSize(), tile.getType(), tile.isNegative()));
    }

    public void removePocket(int dimension, ElectromagneticPocket pocket)
    {
        if (pockets.contains(pocket)) {
            pockets.remove(pocket);
        }
    }

    public List<ElectromagneticPocket> getPockets()
    {
        return pockets;
    }

    public ElectromagneticPocket getNearestPocket(int x, int y, int z)
    {
        ChunkCoordinates coords = new ChunkCoordinates(x, y, z);

        if (pockets != null && pockets.size() > 0) {
            ElectromagneticPocket nearest = null;

            for (ElectromagneticPocket pocket : pockets) {
                if (nearest == null) {
                    nearest = pocket;
                } else {
                    if (nearest.coordinates.getDistanceSquaredToChunkCoordinates(coords) > pocket.coordinates.getDistanceSquaredToChunkCoordinates(coords)) {
                        nearest = pocket;
                    }
                }
            }

            return nearest;
        }

        return null;
    }

    public ElectromagneticPocket getNearestPocketOfType(int x, int y, int z, EnergyHandler.EnergyType type)
    {
        ChunkCoordinates coords = new ChunkCoordinates(x, y, z);
        if (pockets != null && pockets.size() > 0) {
            ElectromagneticPocket nearest = null;

            for (ElectromagneticPocket pocket : pockets) {
                if (pocket.type.equals(type)) {
                    if (nearest == null) {
                        nearest = pocket;
                    } else {
                        if (nearest.coordinates.getDistanceSquaredToChunkCoordinates(coords) > pocket.coordinates.getDistanceSquaredToChunkCoordinates(coords)) {
                            nearest = pocket;
                        }
                    }
                }
            }

            return nearest;
        }

        return null;
    }

    public ElectromagneticPocket getPocketForCoords(World world, int x, int y, int z)
    {
        int dimension = world.provider.dimensionId;
        ChunkCoordinates coords = new ChunkCoordinates(x, y, z);
        if (pockets != null && pockets.size() > 0) {
            ElectromagneticPocket nearest = null;

            for (ElectromagneticPocket pocket : pockets) {
                TileEntity tile = world.getTileEntity(pocket.coordinates.posX, pocket.coordinates.posY, pocket.coordinates.posZ);

                if (tile != null && tile instanceof TileElectromagnetic) {
                    if (((TileElectromagnetic) tile).withinRange(x, y, z)) {
                        if (nearest == null) {
                            nearest = pocket;
                        } else {
                            if (nearest.coordinates.getDistanceSquaredToChunkCoordinates(coords) > pocket.coordinates.getDistanceSquaredToChunkCoordinates(coords)) {
                                nearest = pocket;
                            }
                        }
                    }
                }
            }

            return nearest;
        }

        return null;
    }

    public ElectromagneticPocket getPocketForCoordsOfType(World world, int x, int y, int z, EnergyHandler.EnergyType type)
    {
        int dimension = world.provider.dimensionId;
        ChunkCoordinates coords = new ChunkCoordinates(x, y, z);

        if (pockets != null && pockets.size() > 0) {
            ElectromagneticPocket nearest = null;

            for (ElectromagneticPocket pocket : pockets) {
                if (pocket.type.equals(type)) {
                    TileEntity tile = world.getTileEntity(pocket.coordinates.posX, pocket.coordinates.posY, pocket.coordinates.posZ);

                    if (tile != null && tile instanceof TileElectromagnetic) {
                        if (((TileElectromagnetic) tile).withinRange(x, y, z)) {
                            if (nearest == null) {
                                nearest = pocket;
                            } else {
                                if (nearest.coordinates.getDistanceSquaredToChunkCoordinates(coords) > pocket.coordinates.getDistanceSquaredToChunkCoordinates(coords)) {
                                    nearest = pocket;
                                }
                            }
                        }
                    }
                }
            }

            return nearest;
        }

        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        pockets = new ArrayList<ElectromagneticPocket>();

        NBTTagList pocketList = compound.getTagList("Dimensions", compound.getId());

        for (int i = 0; i < pocketList.tagCount(); i++) {
            ElectromagneticPocket pocket = new ElectromagneticPocket();
            pocket.readFromNBT(pocketList.getCompoundTagAt(i));
            pockets.add(pocket);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList pocketList = new NBTTagList();

        for (ElectromagneticPocket name : pockets) {
            pocketList.appendTag(name.writeToNBT(new NBTTagCompound()));
        }

        compound.setTag("Pockets", pocketList);
    }

    public static ElectromagneticPocketManager get(World world)
    {
        ElectromagneticPocketManager data = (ElectromagneticPocketManager) world.perWorldStorage.loadData(ElectromagneticPocketManager.class, IDENT);

        if (data == null) {
            data = new ElectromagneticPocketManager();
            world.perWorldStorage.setData(IDENT, data);
        }

        return data;
    }

}
