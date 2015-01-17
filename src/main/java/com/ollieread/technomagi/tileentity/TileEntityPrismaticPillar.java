package com.ollieread.technomagi.tileentity;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityBasic;
import com.ollieread.technomagi.tileentity.component.ComponentLinked;
import com.ollieread.technomagi.tileentity.component.ComponentOwner;
import com.ollieread.technomagi.world.region.RegionManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityPrismaticPillar extends TileEntityBasic implements ITileEntityHasFiller, ITileEntityHasOwner
{

    protected ComponentOwner owner = null;
    public ComponentLinked linkedX = null;
    public ComponentLinked linkedZ = null;
    public int order = -1;
    public int network = -1;

    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r = new Random();

    public TileEntityPrismaticPillar()
    {
        owner = new ComponentOwner();
        linkedX = new ComponentLinked<TileEntityPrismaticPillar>();
        linkedZ = new ComponentLinked<TileEntityPrismaticPillar>();
    }

    public boolean canLink(int x, int y, int z)
    {
        return y == yCoord && ((x == xCoord && !linkedZ.setup) || (z == zCoord && !linkedX.setup));
    }

    public void setNetwork(int id)
    {
        network = id;
    }

    public boolean isLinked()
    {
        return linkedX.setup && linkedZ.setup;
    }

    public void network()
    {
        if (network == -1 && isLinked() && order == 0) {
            TileEntityPrismaticPillar pillar1 = (TileEntityPrismaticPillar) linkedZ.getLinked(worldObj);
            TileEntityPrismaticPillar pillar2 = null;
            TileEntityPrismaticPillar pillar3 = null;
            TileEntityPrismaticPillar pillar4 = null;

            if (pillar1 != null && pillar1.isLinked()) {
                pillar2 = (TileEntityPrismaticPillar) pillar1.linkedX.getLinked(worldObj);

                if (pillar2 != null && pillar2.isLinked()) {
                    pillar3 = (TileEntityPrismaticPillar) pillar2.linkedZ.getLinked(worldObj);

                    if (pillar3 != null && pillar3.isLinked()) {
                        pillar4 = (TileEntityPrismaticPillar) pillar3.linkedZ.getLinked(worldObj);

                        if (pillar4 != null && pillar4.isLinked()) {
                            Chunk start = worldObj.getChunkFromBlockCoords(pillar2.xCoord, pillar2.xCoord);
                            Chunk end = worldObj.getChunkFromBlockCoords(pillar4.xCoord, pillar4.xCoord);
                            RegionManager manager = RegionManager.getInstance(worldObj.provider.dimensionId);
                            int id = manager.addNetwork(new int[] { start.xPosition, start.zPosition }, new int[] { end.xPosition + 15, end.zPosition + 15 });

                            if (id > -1) {
                                setNetwork(id);
                                pillar2.setNetwork(id);
                                pillar3.setNetwork(id);
                                pillar4.setNetwork(id);
                            }
                        }
                    }
                }
            }
        }
    }

    public double getDistance()
    {
        if (order > -1) {
            if (order == 0) {
                return linkedZ.linkedZ - zCoord;
            } else if (order == 1) {
                return xCoord - linkedX.linkedX;
            } else if (order == 2) {
                return linkedX.linkedX - xCoord;
            } else if (order == 3) {
                return zCoord - linkedZ.linkedZ;
            }
        }

        return 0D;
    }

    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }

    public boolean canLink()
    {
        return !linkedX.setup || !linkedZ.setup;
    }

    public void link(TileEntityPrismaticPillar tile)
    {
        link(tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public void link(int x, int y, int z)
    {
        if (x == xCoord) {
            linkedZ.setLinked(x, y, z);
        } else if (z == zCoord) {
            linkedX.setLinked(x, y, z);
        }

        sync();
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        order = compound.getInteger("Order");
        network = compound.getInteger("Network");

        owner.readFromNBT(compound.getCompoundTag("Owner"));
        linkedX.readFromNBT(compound.getCompoundTag("LinkedX"));
        linkedZ.readFromNBT(compound.getCompoundTag("LinkedZ"));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Order", order);
        compound.setInteger("Network", network);

        NBTTagCompound ownerCompound = new NBTTagCompound();
        NBTTagCompound linkedXCompound = new NBTTagCompound();
        NBTTagCompound linkedZCompound = new NBTTagCompound();

        owner.writeToNBT(ownerCompound);
        linkedX.writeToNBT(linkedXCompound);
        linkedZ.writeToNBT(linkedZCompound);

        compound.setTag("Owner", ownerCompound);
        compound.setTag("LinkedX", linkedXCompound);
        compound.setTag("LinkedZ", linkedZCompound);
    }

    @Override
    public void create()
    {
        worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockEmptyFiller);

        if (!worldObj.isRemote) {
            ((TileEntityFiller) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord)).setParent(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void destroy()
    {
        worldObj.setBlockToAir(xCoord, yCoord + 1, zCoord);
        invalidate();
        worldObj.setBlockToAir(xCoord, yCoord, zCoord);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        this.field_145924_q += 0.02F;
        this.field_145930_m -= 0.1F;

        while (this.field_145928_o >= (float) Math.PI) {
            this.field_145928_o -= ((float) Math.PI * 2F);
        }

        while (this.field_145928_o < -(float) Math.PI) {
            this.field_145928_o += ((float) Math.PI * 2F);
        }

        while (this.field_145924_q >= (float) Math.PI) {
            this.field_145924_q -= ((float) Math.PI * 2F);
        }

        while (this.field_145924_q < -(float) Math.PI) {
            this.field_145924_q += ((float) Math.PI * 2F);
        }

        float f2;

        for (f2 = this.field_145924_q - this.field_145928_o; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        this.field_145928_o += f2 * 0.4F;

        if (this.field_145930_m < 0.0F) {
            this.field_145930_m = 0.0F;
        }

        if (this.field_145930_m > 1.0F) {
            this.field_145930_m = 1.0F;
        }

        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
        float f3 = 0.2F;

        if (f < -f3) {
            f = -f3;
        }

        if (f > f3) {
            f = f3;
        }

        this.field_145929_l += (f - this.field_145929_l) * 0.9F;
        this.field_145933_i += this.field_145929_l;

        if (!worldObj.isRemote) {
            if (linkedX.isLinked() && linkedZ.isLinked() && order == -1) {
                if (linkedZ.linkedZ > zCoord) {
                    if (linkedX.linkedX > xCoord) {
                        order = 2;
                    } else {
                        order = 0;
                    }
                } else {
                    if (linkedX.linkedX > xCoord) {
                        order = 3;
                    } else {
                        order = 1;
                    }
                }

                sync();
            }

            network();
        }
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
