package com.ollieread.technomagi.tileentity;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.proxy.Disguisable;
import com.ollieread.technomagi.common.proxy.PlayerLocked;

public class TileEntityHardlightGenerator extends TileEntityTM implements IPlayerLocked, IDisguisableTile
{

    protected Disguisable disguise = new Disguisable();
    protected boolean on = false;
    protected boolean proximity = false;
    protected PlayerLocked locked = new PlayerLocked();
    protected int checked = 0;
    protected boolean complete = false;
    protected int count = 0;
    protected int ticks = 0;
    protected int maxTicks = 5;

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        on = compound.getBoolean("On");
        complete = compound.getBoolean("Complete");
        count = compound.getInteger("Count");
        ticks = compound.getInteger("Ticks");

        disguise.readFromNBT(compound);
        locked.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);
        compound.setBoolean("Complete", complete);
        compound.setInteger("Count", count);
        compound.setInteger("Ticks", ticks);

        disguise.writeToNBT(compound);
        locked.writeToNBT(compound);
    }

    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (!complete) {
                if (count <= 16) {
                    ticks++;

                    if (ticks == maxTicks) {
                        int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
                        ForgeDirection dir = ForgeDirection.getOrientation(meta);

                        int x = xCoord + (dir.offsetX * count);
                        int y = yCoord + (dir.offsetY * count);
                        int z = zCoord + (dir.offsetZ * count);

                        if (on) {
                            if (worldObj.isAirBlock(x, y, z)) {
                                worldObj.setBlock(x, y, z, Blocks.blockHardlight);
                                count++;
                            } else if (worldObj.getBlock(x, y, z) == Blocks.blockHardlightGenerator) {
                                Block partner = worldObj.getBlock(x, y, z);

                                if (ForgeDirection.getOrientation(worldObj.getBlockMetadata(x, y, z)).getOpposite().equals(dir)) {
                                    TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(x, y, z);
                                    generator.toggleStatus(true, false);
                                    complete = true;
                                    count = 0;
                                }
                            } else {
                                complete = true;
                                count = 0;
                            }
                        } else {
                            if (worldObj.getBlock(x, y, z) == Blocks.blockHardlight) {
                                worldObj.setBlockToAir(x, y, z);
                                count++;
                            } else if (worldObj.getBlock(x, y, z) == Blocks.blockHardlightGenerator) {
                                Block partner = worldObj.getBlock(x, y, z);

                                if (ForgeDirection.getOrientation(worldObj.getBlockMetadata(x, y, z)).getOpposite().equals(dir)) {
                                    TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(x, y, z);
                                    generator.toggleStatus(true, false);
                                    complete = true;
                                    count = 0;
                                }
                            } else {
                                complete = true;
                                count = 0;
                            }
                        }

                        ticks = 0;
                    }
                } else {
                    complete = true;
                    count = 0;
                }
            }
        }
    }

    public boolean isOn()
    {
        return on;
    }

    public void toggleStatus(boolean proxy, boolean neighbour)
    {
        if (!worldObj.isRemote) {
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            ForgeDirection dir = ForgeDirection.getOrientation(meta);

            if (on) {
                on = false;
            } else {
                on = true;
            }

            if (!proxy) {
                complete = false;
                count = 1;

                // toggleNeighbours();
            }
        }
    }

    public void cleanUp()
    {
        if (!worldObj.isRemote) {
            int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
            ForgeDirection dir = ForgeDirection.getOrientation(meta);

            int x;
            int y;
            int z;

            for (int i = 1; i <= 16; i++) {
                x = xCoord + (dir.offsetX * i);
                y = yCoord + (dir.offsetY * i);
                z = zCoord + (dir.offsetZ * i);

                if (worldObj.getBlock(x, y, z) == Blocks.blockHardlight) {
                    worldObj.setBlockToAir(x, y, z);
                }
            }
        }
    }

    protected void toggleNeighbours()
    {
        if (worldObj.getBlock(xCoord - 1, yCoord, zCoord) == Blocks.blockHardlightGenerator) {
            TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(xCoord - 1, yCoord, zCoord);

            if (generator.isOn() != on) {
                generator.toggleStatus(false, true);
            }
        }
        if (worldObj.getBlock(xCoord + 1, yCoord, zCoord) == Blocks.blockHardlightGenerator) {
            TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(xCoord + 1, yCoord, zCoord);

            if (generator.isOn() != on) {
                generator.toggleStatus(false, true);
            }
        }
        if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == Blocks.blockHardlightGenerator) {
            TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

            if (generator.isOn() != on) {
                generator.toggleStatus(false, true);
            }
        }
        if (worldObj.getBlock(xCoord, yCoord + 1, zCoord) == Blocks.blockHardlightGenerator) {
            TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

            if (generator.isOn() != on) {
                generator.toggleStatus(false, true);
            }
        }
        if (worldObj.getBlock(xCoord, yCoord, zCoord - 1) == Blocks.blockHardlightGenerator) {
            TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(xCoord, yCoord, zCoord - 1);

            if (generator.isOn() != on) {
                generator.toggleStatus(false, true);
            }
        }
        if (worldObj.getBlock(xCoord, yCoord, zCoord + 1) == Blocks.blockHardlightGenerator) {
            TileEntityHardlightGenerator generator = (TileEntityHardlightGenerator) worldObj.getTileEntity(xCoord, yCoord, zCoord + 1);

            if (generator.isOn() == on) {
                generator.toggleStatus(false, true);
            }
        }
    }

    /* Everything below is just a proxy for the interfaces */

    /* DISGUISABLE */

    @Override
    public boolean isDisguised()
    {
        return disguise.isDisguised();
    }

    @Override
    public boolean setDisguise(ItemStack stack)
    {
        return disguise.setDisguise(stack);
    }

    @Override
    public ItemStack getDisguise()
    {
        return disguise.getDisguise();
    }

    /* LOCKED */

    @Override
    public boolean hasPlayer()
    {
        return locked.hasPlayer();
    }

    @Override
    public void setPlayer(String name)
    {
        locked.setPlayer(name);
    }

    @Override
    public String getPlayer()
    {
        return locked.getPlayer();
    }

    @Override
    public boolean isPlayer(String name)
    {
        return locked.isPlayer(name);
    }

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

}
