package com.ollieread.technomagi.common.block.teleporter.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.api.tile.ITileLink;
import com.ollieread.technomagi.util.TeleportHelper;

public class TileTeleporterJumper extends TileTeleporter implements ITileLink, IEnergyHandler
{

    protected EnergyStorage energy;
    protected int baseConsume = 50;
    protected ChunkCoordinates link;

    public TileTeleporterJumper()
    {
        createEnergyStorage();
    }

    protected void createEnergyStorage()
    {
        this.energy = new EnergyStorage(3200, 50, 0);
    }

    @Override
    public void startCooldown(boolean incoming)
    {
        setCooldown(25);
    }

    @Override
    public boolean canUse(EntityPlayer player, boolean incoming)
    {
        if (!incoming) {
            return super.canUse(player, incoming) && isLinked();
        }

        return false;
    }

    @Override
    public void use(EntityPlayer player, boolean incoming)
    {
        TileEntity linked = this.getTile();

        if (!incoming) {
            if (linked instanceof TileTeleporterJumper) {
                if (((TileTeleporterJumper) linked).canUse(player, !incoming)) {
                    TeleportHelper.teleportEntityToTeleporter(player, linked.xCoord, linked.yCoord, linked.zCoord, true);
                    energy.modifyEnergyStored(this.baseConsume);
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (link != null) {
            compound.setIntArray("Link", new int[] { link.posX, link.posY, link.posZ });
        }

        NBTTagCompound energyCompound = new NBTTagCompound();
        this.energy.writeToNBT(energyCompound);
        compound.setTag("Energy", energyCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Link")) {
            int[] linkCoords = compound.getIntArray("Link");
            link = new ChunkCoordinates(linkCoords[0], linkCoords[1], linkCoords[2]);
        }

        createEnergyStorage();
        this.energy.readFromNBT(compound.getCompoundTag("Energy"));
    }

    @Override
    public boolean isLinked()
    {
        return link != null;
    }

    @Override
    public boolean canLink(int x, int y, int z)
    {
        TileEntity tile = worldObj.getTileEntity(x, y, z);

        if (tile != null && tile instanceof TileTeleporterJumper) {
            ITileLink tileLink = (ITileLink) tile;

            if (!tileLink.isLinked()) {
                int xd = xCoord - x;
                int yd = yCoord - y;
                int zd = zCoord - z;

                return MathHelper.sqrt_double(xd * xd + yd * yd + zd * zd) <= getRange();
            }
        }
        return true;
    }

    @Override
    public int getRange()
    {
        return 64;
    }

    @Override
    public void setLink(int x, int y, int z)
    {
        this.link = new ChunkCoordinates(x, y, z);
    }

    @Override
    public ITileLink getLink()
    {
        if (this.link != null) {
            TileEntity tile = worldObj.getTileEntity(link.posX, link.posY, link.posZ);

            return tile instanceof ITileLink ? (ITileLink) tile : null;
        }

        return null;
    }

    @Override
    public TileEntity getTile()
    {
        return this;
    }

    @Override
    public void removeLink()
    {
        this.link = null;
    }

    @Override
    public boolean isProxy()
    {
        return false;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from)
    {
        return !from.equals(ForgeDirection.UP);
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return energy.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return energy.extractEnergy(maxExtract, simulate);
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return energy.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return energy.getMaxEnergyStored();
    }

}
