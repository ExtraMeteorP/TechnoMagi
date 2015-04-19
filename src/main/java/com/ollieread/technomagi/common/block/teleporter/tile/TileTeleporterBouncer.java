package com.ollieread.technomagi.common.block.teleporter.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;

import com.ollieread.technomagi.api.tile.ITileLink;

public class TileTeleporterBouncer extends TileTeleporterNetworked implements ITileLink, IEnergyHandler
{

    protected EnergyStorage energy;
    protected int baseConsume = 25;
    protected ChunkCoordinates link;

    public TileTeleporterBouncer()
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
        if (incoming) {
            return super.canUse(player, incoming);
        }

        return false;
    }

    @Override
    public void use(EntityPlayer player, boolean incoming)
    {
        energy.modifyEnergyStored(-25);
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
        return true;
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
