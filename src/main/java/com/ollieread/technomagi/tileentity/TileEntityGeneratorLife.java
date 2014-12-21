package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityGeneratorLife extends TileEntityGenerator
{

    public TileEntityGeneratorLife()
    {
        super(3200, 10, 0, 5);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            boolean flag = (energy.getMaxEnergyStored(null) > energy.getEnergyStored(null));
            boolean should = true;

            if (energyTicks > 0) {
                working++;

                if (energyTicks == working) {
                    should = true;
                    working = 0;
                }
            }

            if (flag) {

                for (int i = 0; i < 4; ++i) {
                    int x = xCoord + worldObj.rand.nextInt(3) - 1;
                    int y = yCoord + worldObj.rand.nextInt(5) - 3;
                    int z = zCoord + worldObj.rand.nextInt(3) - 1;
                    Block block = worldObj.getBlock(x, y + 1, z);

                    if (block instanceof BlockGrass) {
                        energyGeneration = 4;
                        worldObj.setBlock(x, y, z, Blocks.dirt);
                        break;
                    } else if (block instanceof BlockLeaves) {
                        energyGeneration = 6;
                        worldObj.setBlockToAir(x, y, z);
                        break;
                    } else if (block instanceof BlockTallGrass) {
                        energyGeneration = 8;
                        worldObj.setBlockToAir(x, y, z);
                        break;
                    } else if (block instanceof BlockFlower) {
                        energyGeneration = 4;
                        worldObj.setBlockToAir(x, y, z);
                        break;
                    }
                }

                if (energyGeneration > 0) {
                    energy.increaseEnergyStored(energyGeneration);

                    sync();
                }

                energyGeneration = 0;
            }
        }

    }

    @Override
    public boolean canGenerate()
    {
        return true;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return from.equals(ForgeDirection.DOWN);
    }

}
