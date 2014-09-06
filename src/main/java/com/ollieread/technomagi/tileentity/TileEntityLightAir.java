package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.BlockLightAir;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.proxy.MasterBlock;

public class TileEntityLightAir extends TileEntityTM implements IHasMaster
{

    public MasterBlock master = new MasterBlock(false);

    public int ticks = 0;

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Ticks", ticks);

        master.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        ticks = compound.getInteger("Ticks");

        master.readFromNBT(compound);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            validateSelf();
        }
    }

    public boolean validateSelf()
    {
        if (master.setup) {
            if (master.masterX == 0 && master.masterY == 0 && master.masterZ == 0) {
                master.setSetup(false);

                return false;
            } else {
                Block block = worldObj.getBlock(master.masterX, master.masterY, master.masterZ);
                if (!block.equals(Blocks.blockAreaLight)) {
                    master.setSetup(false);

                    return false;
                } else {
                    TileEntityAreaLight tile = (TileEntityAreaLight) worldObj.getTileEntity(master.masterX, master.masterY, master.masterZ);

                    if (tile == null) {
                        master.setSetup(false);
                    } else if (!tile.isOn()) {
                        master.setSetup(false);
                    }
                }
            }
        } else {
            if (ticks < 20) {
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
                            light.setMaster(master.masterX, master.masterY, master.masterZ);
                        }
                    }
                }
            }
        }
    }

    public static boolean isValidPosition(World world, TileEntityLightAir tile, int x, int y, int z)
    {
        if (tile.isSetup() && !world.canBlockSeeTheSky(x, y, z) && world.getBlockLightValue(x, y, z) < 14) {
            Block block = world.getBlock(x, y, z);

            if (block.equals(Blocks.blockLightAir)) {
                if (((BlockLightAir) block).validateSelf(world, x, y, z)) {
                    return false;
                }
            } else if (!block.equals(net.minecraft.init.Blocks.air)) {
                return false;
            }

            double d1 = tile.master.masterX - x;
            double d2 = tile.master.masterY - y;
            double d3 = tile.master.masterZ - z;

            return (d1 * d1 + d2 * d2 + d3 * d3) <= (21 * 21);
        }

        return false;
    }

    public boolean isSetup()
    {
        return master.setup;
    }

    /* Everything below is just a proxy for the interfaces */

    /* MASTER */

    @Override
    public void setMaster(int x, int y, int z)
    {
        master.setMaster(x, y, z);
    }

    @Override
    public void setSetup(boolean setup)
    {
        master.setSetup(setup);
    }
}
