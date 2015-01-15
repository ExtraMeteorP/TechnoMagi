package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.lib.util.helpers.EnergyHelper;

import com.ollieread.technomagi.common.init.Config;

public class TileEntityEncourager extends TileEntityMachine
{

    public TileEntityEncourager()
    {
        super(Config.encouragerPowerMax, Config.encouragerPowerRecieve, 0);

        maxProgress = Config.encouragerProgressMax;
        usage = Config.encouragerPowerUse;
    }

    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (canEncourage()) {
                encourage();
            }

            if (EnergyHelper.isAdjacentEnergyHandlerFromSide(this, ForgeDirection.DOWN.ordinal())) {
                int input = energy.getMaxReceive();
                int receive = energy.receiveEnergy(ForgeDirection.DOWN, input, true);
                int extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, true);

                if (receive > 0 && extract > 0) {

                    if (receive == extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    } else if (receive > extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), extract, false);
                    } else if (receive < extract) {
                        extract = EnergyHelper.extractEnergyFromAdjacentEnergyHandler(this, ForgeDirection.DOWN.ordinal(), receive, false);
                    }
                    receiveEnergy(ForgeDirection.DOWN, extract, false);
                }
            }
        }
    }

    public boolean canEncourage()
    {
        return getEnergyStored(null) > usage;
    }

    public void encourage()
    {
        if (energy.modifyEnergyStored(usage)) {
            progress++;

            if (progress >= maxProgress) {
                int y = yCoord;

                for (int x = xCoord - 7; x <= xCoord + 7; ++x) {
                    for (int z = zCoord - 7; z <= zCoord + 7; ++z) {
                        Block block = worldObj.getBlock(x, y, z);

                        if (block == Blocks.dirt || block == Blocks.grass) {
                            worldObj.setBlock(x, y, z, Blocks.farmland);
                            worldObj.setBlockMetadataWithNotify(x, y, z, 7, 2);
                        } else if (block == Blocks.farmland && worldObj.getBlockMetadata(x, y, z) < 7) {
                            worldObj.setBlockMetadataWithNotify(x, y, z, 7, 2);
                        }
                    }
                }

                y += 1;
                int c = 0;

                while (c < 6) {
                    int x = (xCoord - 7) + worldObj.rand.nextInt(16);
                    int z = (zCoord - 7) + worldObj.rand.nextInt(16);

                    Block block = worldObj.getBlock(x, y, z);

                    if (block instanceof IGrowable) {
                        if (worldObj.getBlock(x, y - 1, z) != Blocks.farmland) {
                            continue;
                        }

                        IGrowable growable = (IGrowable) block;

                        if (growable.func_149851_a(worldObj, x, y, z, true)) {
                            growable.func_149853_b(worldObj, worldObj.rand, x, y, z);
                        }
                    }

                    c++;
                }

                progress = 0;
            }
        }
    }

}
