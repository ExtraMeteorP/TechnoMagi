package com.ollieread.technomagi.common.block.structure.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;

import com.ollieread.technomagi.common.block.structure.BlockHardlight;
import com.ollieread.technomagi.common.init.Blocks;

public class TileStructurePlatform extends TileStructure
{

    protected List<ChunkCoordinates> coords;

    @Override
    public void enable()
    {
        if (!enabled) {
            enabled = true;

            coords = new ArrayList<ChunkCoordinates>();
            int xStart = xCoord - 4;
            int zStart = zCoord - 4;

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int x = xStart + i;
                    int z = zStart + j;

                    if (worldObj.isAirBlock(x, yCoord, z)) {
                        worldObj.setBlock(x, yCoord, z, Blocks.hardlight);
                        coords.add(new ChunkCoordinates(x, yCoord, z));
                    }
                }
            }

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void disable()
    {
        if (enabled) {
            enabled = false;
            if (coords != null && coords.size() > 0) {
                for (ChunkCoordinates coord : coords) {
                    if (worldObj.getBlock(coord.posX, coord.posY, coord.posZ) instanceof BlockHardlight) {
                        worldObj.setBlockToAir(coord.posX, coord.posY, coord.posZ);
                    }
                }

                coords = new ArrayList<ChunkCoordinates>();
            }

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("Enabled", enabled);

        if (coords != null && coords.size() > 0) {
            NBTTagList coordCompound = new NBTTagList();

            for (ChunkCoordinates coord : coords) {
                NBTTagCompound c = new NBTTagCompound();
                c.setIntArray("Coord", new int[] { coord.posX, coord.posY, coord.posZ });
                coordCompound.appendTag(c);
            }

            compound.setTag("Coords", coordCompound);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        enabled = compound.getBoolean("Enabled");

        if (compound.hasKey("Coords")) {
            NBTTagList coordCompound = compound.getTagList("Coords", compound.getId());
            coords = new ArrayList<ChunkCoordinates>();

            for (int i = 0; i < coordCompound.tagCount(); i++) {
                NBTTagCompound nbt = coordCompound.getCompoundTagAt(i);
                int[] c = nbt.getIntArray("Coord");
                coords.add(new ChunkCoordinates(c[0], c[1], c[2]));
            }
        }
    }

}
