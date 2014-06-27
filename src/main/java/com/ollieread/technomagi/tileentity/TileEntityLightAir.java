package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.BlockLightAir;
import com.ollieread.technomagi.common.init.Blocks;

public class TileEntityLightAir extends TileEntityTM
{

    public int masterX = 0;
    public int masterY = 0;
    public int masterZ = 0;
    public boolean setup = false;
    public int ticks = 0;

    public void setMaster(int x, int y, int z)
    {
        masterX = x;
        masterY = y;
        masterZ = z;
        setup = true;
        ticks = 0;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("MasterX", masterX);
        compound.setInteger("MasterY", masterY);
        compound.setInteger("MasterZ", masterZ);
        compound.setBoolean("Setup", setup);
        compound.setInteger("Ticks", ticks);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        masterX = compound.getInteger("MasterX");
        masterY = compound.getInteger("MasterY");
        masterZ = compound.getInteger("MasterZ");
        setup = compound.getBoolean("Setup");
        ticks = compound.getInteger("Ticks");
    }

    @Override
    public void updateEntity()
    {
        validateSelf();
    }

    public boolean validateSelf()
    {
        if (setup) {
            if (masterX == 0 && masterY == 0 && masterZ == 0) {
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);

                return false;
            } else {
                Block block = worldObj.getBlock(masterX, masterY, masterZ);
                if (!block.equals(Blocks.blockAreaLight)) {
                    worldObj.setBlockToAir(xCoord, yCoord, zCoord);

                    return false;
                } else {
                    TileEntityAreaLight tile = (TileEntityAreaLight) worldObj.getTileEntity(masterX, masterY, masterZ);

                    if (tile == null) {
                        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
                    }
                }
            }
        } else {
            if (ticks < 200) {
                ticks++;
            } else {
                worldObj.setBlockToAir(xCoord, yCoord, zCoord);

                return false;
            }
        }

        return true;
    }

    public void spreadBlocks()
    {
        int xStart = xCoord - 10;
        int xEnd = xCoord + 10;
        int zStart = zCoord - 10;
        int zEnd = zCoord + 10;
        int yStart = yCoord - 10;
        int yEnd = yCoord + 10;

        for (int i = xStart; i < xEnd; i++) {
            for (int j = zStart; j < zEnd; j++) {
                for (int k = yStart; k < yEnd; k++) {
                    if (isValidPosition(worldObj, this, i, k, j)) {
                        worldObj.setBlock(i, k, j, Blocks.blockLightAir);
                        TileEntityLightAir light = (TileEntityLightAir) worldObj.getTileEntity(i, k, j);

                        if (light != null) {
                            light.setMaster(masterX, masterY, masterZ);
                        }
                    }
                }
            }
        }
    }

    public static boolean isValidPosition(World world, TileEntityLightAir tile, int x, int y, int z)
    {
        if (tile.setup && !world.canBlockSeeTheSky(x, y, z) && world.getBlockLightValue(x, y, z) < 14) {
            Block block = world.getBlock(x, y, z);

            if (block.equals(Blocks.blockLightAir)) {
                if (((BlockLightAir) block).validateSelf(world, x, y, z)) {
                    return false;
                }
            } else if (!block.equals(net.minecraft.init.Blocks.air)) {
                return false;
            }

            double d1 = tile.masterX - x;
            double d2 = tile.masterY - y;
            double d3 = tile.masterZ - z;

            return (d1 * d1 + d2 * d2 + d3 * d3) <= (21 * 21);
        }

        return false;
    }
}
