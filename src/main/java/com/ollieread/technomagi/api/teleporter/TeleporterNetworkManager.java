package com.ollieread.technomagi.api.teleporter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.WorldServer;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.teleporter.TeleporterNetwork.Teleporter;
import com.ollieread.technomagi.common.block.teleporter.tile.TileTeleporterRegulator;

/**
 * 
 * @author ollieread
 *
 */
public class TeleporterNetworkManager extends WorldSavedData
{

    private static final String IDENT = "Technomagi_TeleporterNetworks";

    protected Map<String, TeleporterNetwork> networks = new ConcurrentHashMap<String, TeleporterNetwork>();

    public TeleporterNetworkManager()
    {
        super(IDENT);
    }

    public boolean hasNetwork(String name)
    {
        return networks.containsKey(name);
    }

    public void addNetwork(String name, TileTeleporterRegulator regulator)
    {
        TeleporterNetwork network = new TeleporterNetwork(regulator);

        networks.put(name, network);
        markDirty();
    }

    public TeleporterNetwork getNetwork(String name)
    {
        if (hasNetwork(name)) {
            return networks.get(name);
        }

        return null;
    }

    public void removeNetwork(String name)
    {
        if (hasNetwork(name)) {
            TeleporterNetwork network = getNetwork(name);
            network.removeNetwork();
            networks.remove(name);
            markDirty();
        }
    }

    public TileTeleporterRegulator getNetworkRegulator(String name)
    {
        if (Technomagi.proxy.isClient()) {
            return null;
        }

        TeleporterNetwork network = getNetwork(name);

        if (network != null) {
            WorldServer world = MinecraftServer.getServer().worldServerForDimension(network.getDimension());

            if (world != null) {
                return network.getRegulator(world);
            }
        }

        return null;
    }

    public List<Teleporter> getNetworkTeleporters(String name, int dimension)
    {
        TeleporterNetwork network = getNetwork(name);

        if (network != null) {
            return network.getTeleporters(dimension);
        }

        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        networks = new ConcurrentHashMap<String, TeleporterNetwork>();

        NBTTagList networkList = compound.getTagList("Networks", compound.getId());

        for (int i = 0; i < networkList.tagCount(); i++) {
            NBTTagCompound networkCompound = networkList.getCompoundTagAt(i);
            TeleporterNetwork network = new TeleporterNetwork();
            network.readFromNBT(networkCompound);
            networks.put(network.name, network);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList networkList = new NBTTagList();

        for (String name : networks.keySet()) {
            networkList.appendTag(networks.get(name).writeToNBT(new NBTTagCompound()));
        }

        compound.setTag("Networks", networkList);
    }

    public static TeleporterNetworkManager get(World world)
    {
        TeleporterNetworkManager data = (TeleporterNetworkManager) world.loadItemData(TeleporterNetworkManager.class, IDENT);

        if (data == null) {
            data = new TeleporterNetworkManager();
            world.setItemData(IDENT, data);
        }

        return data;
    }

}
