package com.ollieread.technomagi.world.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncRegions;

public class RegionManager
{

    private static int regionId = -1;
    protected static Map<Integer, RegionNetwork> networks = new HashMap<Integer, RegionNetwork>();
    protected static Map<RegionControllerType, List<IRegionController>> controllers = new HashMap<RegionControllerType, List<IRegionController>>();
    protected static Map<Integer, List<RegionControllerType>> networkControllerTypes = new HashMap<Integer, List<RegionControllerType>>();

    /**
     * Get the next available network id and increment by 1
     * 
     * @return int
     */
    private static int getNextId()
    {
        regionId++;
        return regionId;
    }

    public static boolean isInside(int x, int z)
    {
        for (Iterator<RegionNetwork> i = networks.values().iterator(); i.hasNext();) {
            RegionNetwork network = i.next();

            if (network.isInside(x, z)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCovering(int[] start, int[] end)
    {
        for (Iterator<RegionNetwork> i = networks.values().iterator(); i.hasNext();) {
            RegionNetwork network = i.next();

            if (network.start[0] >= start[0] && network.end[0] <= end[0] && network.start[1] >= start[1] && network.end[1] <= end[1]) {
                return true;
            }
        }

        return false;
    }

    /**
     * Add a network to the registry. A network is a series of 4 pillars,
     * sharing x & z accordingly to make a square.
     * 
     * @param start
     * @param end
     * @return
     */
    public static int addNetwork(int[] start, int[] end)
    {
        if (!isCovering(start, end)) {
            int id = getNextId();

            RegionNetwork network = new RegionNetwork(id, start, end);

            networks.put(id, network);
            networkControllerTypes.put(id, new ArrayList<RegionControllerType>());

            sync();

            return id;
        }

        return -1;
    }

    /**
     * 
     * @param id
     * @return
     */
    public static RegionNetwork getNetwork(int id)
    {
        if (networks.containsKey(id)) {
            return networks.get(id);
        }

        return null;
    }

    /**
     * 
     * @param id
     */
    public static void removeNetwork(int id)
    {
        if (networks.containsKey(id)) {
            networks.remove(id);
        }
    }

    /**
     * 
     * @param id
     * @param type
     * @return
     */
    public static boolean hasController(int id, RegionControllerType type)
    {
        if (networks.containsKey(id) && networkControllerTypes.get(id).contains(type)) {
            return true;
        }

        return false;
    }

    /**
     * 
     * @param x
     * @param z
     * @return
     */
    public static int getNetworkForCoords(int x, int z)
    {
        for (Iterator<RegionNetwork> i = networks.values().iterator(); i.hasNext();) {
            RegionNetwork network = i.next();

            if (network.isInside(x, z)) {
                return network.getId();
            }
        }

        return -1;
    }

    /**
     * 
     * @param type
     * @param controller
     */
    public static void addController(IRegionController controller)
    {
        RegionControllerType type = controller.getType();

        if (!controllers.containsKey(type)) {
            controllers.put(type, new ArrayList<IRegionController>());
        }

        int id = controller.getNetworkId();

        if (id > -1 && !hasController(controller.getNetworkId(), type)) {
            if (!controllers.get(type).contains(controller)) {
                controllers.get(type).add(controller);
                networkControllerTypes.get(id).add(type);
            }
        }
    }

    /**
     * 
     * @param type
     * @return
     */
    public static List<IRegionController> getControllers(RegionControllerType type)
    {
        if (controllers.containsKey(type)) {
            return controllers.get(type);
        }

        return null;
    }

    public static void sync()
    {
        if (TechnoMagi.proxy.isServer()) {
            NBTTagCompound compound = new NBTTagCompound();
            writeToNBT(compound);

            PacketHandler.INSTANCE.sendToAll(new MessageSyncRegions(compound));
        }
    }

    public static void load(NBTTagCompound compound)
    {
        if (TechnoMagi.proxy.isClient()) {
            readFromNBT(compound);
        }
    }

    public static void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList networkList = new NBTTagList();

        for (Iterator<RegionNetwork> i = networks.values().iterator(); i.hasNext();) {
            RegionNetwork entry = i.next();
            NBTTagCompound n = new NBTTagCompound();
            entry.sync(compound);
            networkList.appendTag(n);
        }

        NBTTagList controllerList = new NBTTagList();

        for (Iterator<List<IRegionController>> i = controllers.values().iterator(); i.hasNext();) {
            List<IRegionController> entry = i.next();
            for (Iterator<IRegionController> it = entry.iterator(); it.hasNext();) {
                IRegionController list = it.next();
                NBTTagCompound c = new NBTTagCompound();
                c.setInteger("Type", list.getType().ordinal());
                c.setIntArray("Coords", list.getCoords());
            }
        }

        compound.setTag("Controllers", controllerList);
    }

    public static void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList networkList = compound.getTagList("Networks", compound.getId());
        networks = new HashMap<Integer, RegionNetwork>();

        for (int i = 0; i < networkList.tagCount(); i++) {
            NBTTagCompound n = networkList.getCompoundTagAt(i);
            int id = n.getInteger("ID");
            networks.put(i, new RegionNetwork(id, n.getIntArray("Start"), n.getIntArray("End")));
        }

        NBTTagList controllerList = compound.getTagList("Controllers", compound.getId());
        controllers = new HashMap<RegionControllerType, List<IRegionController>>();
        networkControllerTypes = new HashMap<Integer, List<RegionControllerType>>();

        for (int i = 0; i < controllerList.tagCount(); i++) {
            NBTTagCompound c = controllerList.getCompoundTagAt(i);
            RegionControllerType type = RegionControllerType.values()[c.getInteger("Type")];
            int[] coords = c.getIntArray("Coords");

            if (!controllers.containsKey(type)) {
                controllers.put(type, new ArrayList<IRegionController>());
            }

            IRegionController controller = (IRegionController) TechnoMagi.proxy.getClientWorld().getTileEntity(coords[0], coords[1], coords[2]);

            controllers.get(type).add(controller);

            if (!networkControllerTypes.containsKey(controller.getNetworkId())) {
                networkControllerTypes.put(controller.getNetworkId(), new ArrayList<RegionControllerType>());
            }

            networkControllerTypes.get(controller.getNetworkId()).add(type);
        }
    }

    /**
     * The type of controller available for the regions.
     * 
     * Perception: Other players & entities cannot perceive the chunks, instead
     * see seed version
     * 
     * Interaction: Control over what players & entities can do
     * 
     * Presence: Controller over the presence of players & entities, including
     * spawn control
     * 
     * @author ollieread
     *
     */
    public static enum RegionControllerType {
        PERCEPTION, INTERACTION, PRESENCE
    }

}
