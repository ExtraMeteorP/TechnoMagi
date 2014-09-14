package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.BlockDisplacedAir;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.proxy.MasterBlock;

public class TileEntityDisplacedAir extends TileEntityTM implements IHasMaster
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
            // validateSelf();
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
                if (!block.equals(Blocks.blockDisplacedAir)) {
                    master.setSetup(false);

                    return false;
                } else {
                    TileEntityDisplacedAir tile = (TileEntityDisplacedAir) worldObj.getTileEntity(master.masterX, master.masterY, master.masterZ);

                    if (tile == null) {
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

    public static boolean isValidPosition(World world, TileEntityLightAir tile, int x, int y, int z)
    {
        if (tile.isSetup() && !world.canBlockSeeTheSky(x, y, z) && world.getBlockLightValue(x, y, z) < 14) {
            Block block = world.getBlock(x, y, z);

            if (block.equals(Blocks.blockDisplacedAir)) {
                if (((BlockDisplacedAir) block).validateSelf(world, x, y, z)) {
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
