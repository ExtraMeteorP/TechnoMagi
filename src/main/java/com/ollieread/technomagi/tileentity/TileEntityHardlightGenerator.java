package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityBasic;
import com.ollieread.technomagi.tileentity.component.ComponentDisguisable;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;

public class TileEntityHardlightGenerator extends TileEntityBasic implements ITileEntityHasOwner, ITileEntityDisguisable
{

    protected ComponentOwner owner = new ComponentOwner();
    protected ComponentDisguisable disguise = new ComponentDisguisable();
    protected boolean on = false;
    protected boolean proximity = false;
    protected int checked = 0;
    protected boolean complete = true;
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

        disguise.readFromNBT(compound.getCompoundTag("Disguise"));
        owner.readFromNBT(compound.getCompoundTag("Owner"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);
        compound.setBoolean("Complete", complete);
        compound.setInteger("Count", count);
        compound.setInteger("Ticks", ticks);

        NBTTagCompound disguiseCompound = new NBTTagCompound();
        NBTTagCompound ownerCompound = new NBTTagCompound();

        disguise.writeToNBT(disguiseCompound);
        owner.writeToNBT(ownerCompound);

        compound.setTag("Disguise", disguiseCompound);
        compound.setTag("Owner", ownerCompound);
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
                            } else {
                                complete = true;
                                count = 0;
                            }
                        } else {
                            if (worldObj.getBlock(x, y, z) == Blocks.blockHardlight) {
                                worldObj.setBlockToAir(x, y, z);
                                count++;
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

    public void toggleStatus()
    {
        if (!worldObj.isRemote) {
            if (on) {
                on = false;
            } else {
                on = true;
            }

            complete = false;
            count = 1;

            sync();
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

    /* OWNER */

    @Override
    public boolean isOwner(String name)
    {
        return owner.isOwner(name);
    }

    @Override
    public void setOwner(String name)
    {
        owner.setOwner(name);
    }

    @Override
    public EntityPlayer getOwner(World world)
    {
        return owner.getOwner(world);
    }

}
