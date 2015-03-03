package com.ollieread.technomagi.common.block.teleporter.tile;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.api.teleporter.TeleporterNetwork;
import com.ollieread.technomagi.api.teleporter.TeleporterNetworkManager;

public abstract class TileTeleporterNetworked extends TileTeleporter
{

    protected String network = "";

    public void setNetwork(String network)
    {
        this.network = network;
    }

    public String getNetwork()
    {
        return this.network;
    }

    public void resetNetwork()
    {
        this.network = "";
    }

    public void addToNetwork(String network)
    {
        TeleporterNetworkManager manager = TeleporterNetworkManager.get(worldObj);

        if (manager != null) {
            TeleporterNetwork teleporterNetwork = manager.getNetwork(network);

            if (teleporterNetwork != null) {
                teleporterNetwork.addTeleporter(this);
                setNetwork(network);
            }
        }
    }

    public void removeFromNetwork()
    {
        if (network != null && !network.isEmpty()) {
            TeleporterNetworkManager manager = TeleporterNetworkManager.get(worldObj);

            if (manager != null) {
                TeleporterNetwork teleporterNetwork = manager.getNetwork(network);

                if (teleporterNetwork != null) {
                    teleporterNetwork.removeTeleporter(this);
                    resetNetwork();
                }
            }
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (network != null && !network.isEmpty()) {
            compound.setString("Network", network);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Network")) {
            network = compound.getString("Network");
        }
    }

}
