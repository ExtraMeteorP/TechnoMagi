package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.abstracts.Machine;

public class TileEntityMachineEncourager extends Machine
{

    public TileEntityMachineEncourager()
    {
        super(Config.encouragerPowerMax, Config.encouragerPowerRecieve, 0);

        setMaxProgress(Config.encouragerProgressMax);
        setUsage(Config.encouragerPowerUse);
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    @Override
    public boolean isProcessing()
    {
        return getProgress() > 0;
    }

    public boolean canProcess()
    {
        return getEnergyStored(null) > getUsage();
    }

    public void process()
    {
        if (energy.modifyEnergyStored(getUsage())) {
            setProgress(getProgress() + 1);

            if (getProgress() >= getMaxProgress()) {
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

                setProgress(0);
            }
        }
    }

}
