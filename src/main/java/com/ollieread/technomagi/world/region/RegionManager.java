package com.ollieread.technomagi.world.region;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.network.PacketHandler;
import com.ollieread.technomagi.network.message.MessageSyncRegions;
import com.ollieread.technomagi.tileentity.ITileEntityRegionController;

public class RegionManager
{

    public static Map<Integer, RegionManager> dimensionManagers = new HashMap<Integer, RegionManager>();

    private int dimension;
    private int regionId = -1;
    protected Map<Integer, RegionNetwork> networks = new HashMap<Integer, RegionNetwork>();
    protected Map<RegionControllerType, List<ITileEntityRegionController>> controllers = new HashMap<RegionControllerType, List<ITileEntityRegionController>>();
    protected Map<Integer, List<RegionControllerType>> networkControllerTypes = new HashMap<Integer, List<RegionControllerType>>();

    public static RegionManager getInstance(int dimension)
    {
        if (!dimensionManagers.containsKey(dimension)) {
            for (int i = 0; i < Config.perceptionDimensions.length; i++) {
                if (Config.perceptionDimensions[i] == dimension) {
                    dimensionManagers.put(dimension, new RegionManager(dimension));
                }
            }
        }

        return dimensionManagers.get(dimension);
    }

    protected RegionManager(int dimension)
    {
        this.dimension = dimension;
    }

    /**
     * Get the next available network id and increment by 1
     * 
     * @return int
     */
    private int getNextId()
    {
        regionId++;
        return regionId;
    }

    public boolean isInside(int x, int z)
    {
        for (Iterator<RegionNetwork> i = networks.values().iterator(); i.hasNext();) {
            RegionNetwork network = i.next();

            if (network.isInside(x, z)) {
                return true;
            }
        }

        return false;
    }

    public boolean isCovering(int[] start, int[] end)
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
    public int addNetwork(int[] start, int[] end)
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
    public RegionNetwork getNetwork(int id)
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
    public void removeNetwork(int id)
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
    public boolean hasController(int id, RegionControllerType type)
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
    public int getNetworkForCoords(int x, int z)
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
    public void addController(ITileEntityRegionController controller)
    {
        RegionControllerType type = controller.getType();

        if (!controllers.containsKey(type)) {
            controllers.put(type, new ArrayList<ITileEntityRegionController>());
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
    public List<ITileEntityRegionController> getControllers(RegionControllerType type)
    {
        if (controllers.containsKey(type)) {
            return controllers.get(type);
        }

        return null;
    }

    public List<ITileEntityRegionController> getControllers(RegionControllerType type, int id)
    {
        if (controllers.containsKey(type)) {
            List<ITileEntityRegionController> controllerList = controllers.get(type);
            List<ITileEntityRegionController> newControllerList = new ArrayList<ITileEntityRegionController>();

            for (ITileEntityRegionController controller : controllerList) {
                if (controller.getNetworkId() == id) {
                    newControllerList.add(controller);
                }
            }

            return newControllerList;
        }

        return null;
    }

    public void sync()
    {
        if (TechnoMagi.proxy.isServer()) {
            NBTTagCompound compound = new NBTTagCompound();
            writeToNBT(compound);

            PacketHandler.INSTANCE.sendToAll(new MessageSyncRegions(dimension, compound));
        }
    }

    public void load(NBTTagCompound compound)
    {
        if (TechnoMagi.proxy.isClient()) {
            readFromNBT(compound);
        }
    }

    public void writeToNBT(NBTTagCompound compound)
    {
        NBTTagList networkList = new NBTTagList();

        for (Iterator<RegionNetwork> i = networks.values().iterator(); i.hasNext();) {
            RegionNetwork entry = i.next();
            NBTTagCompound n = new NBTTagCompound();
            entry.sync(compound);
            networkList.appendTag(n);
        }

        NBTTagList controllerList = new NBTTagList();

        for (Iterator<List<ITileEntityRegionController>> i = controllers.values().iterator(); i.hasNext();) {
            List<ITileEntityRegionController> entry = i.next();
            for (Iterator<ITileEntityRegionController> it = entry.iterator(); it.hasNext();) {
                ITileEntityRegionController list = it.next();
                NBTTagCompound c = new NBTTagCompound();
                c.setInteger("Type", list.getType().ordinal());
                c.setIntArray("Coords", list.getCoords());
            }
        }

        compound.setTag("Controllers", controllerList);
        compound.setInteger("RegionID", regionId);
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        NBTTagList networkList = compound.getTagList("Networks", compound.getId());
        networks = new HashMap<Integer, RegionNetwork>();

        for (int i = 0; i < networkList.tagCount(); i++) {
            NBTTagCompound n = networkList.getCompoundTagAt(i);
            int id = n.getInteger("ID");
            networks.put(i, new RegionNetwork(id, n.getIntArray("Start"), n.getIntArray("End")));
        }

        NBTTagList controllerList = compound.getTagList("Controllers", compound.getId());
        controllers = new HashMap<RegionControllerType, List<ITileEntityRegionController>>();
        networkControllerTypes = new HashMap<Integer, List<RegionControllerType>>();

        for (int i = 0; i < controllerList.tagCount(); i++) {
            NBTTagCompound c = controllerList.getCompoundTagAt(i);
            RegionControllerType type = RegionControllerType.values()[c.getInteger("Type")];
            int[] coords = c.getIntArray("Coords");

            if (!controllers.containsKey(type)) {
                controllers.put(type, new ArrayList<ITileEntityRegionController>());
            }

            ITileEntityRegionController controller = (ITileEntityRegionController) TechnoMagi.proxy.getClientWorld().getTileEntity(coords[0], coords[1], coords[2]);

            controllers.get(type).add(controller);

            if (!networkControllerTypes.containsKey(controller.getNetworkId())) {
                networkControllerTypes.put(controller.getNetworkId(), new ArrayList<RegionControllerType>());
            }

            networkControllerTypes.get(controller.getNetworkId()).add(type);
        }

        regionId = compound.getInteger("RegionID");
    }

    /**
     * The type of controller available for the regions.
     * 
     * Perception: Other players & entities cannot perceive the chunks, instead
     * see seed version
     * 
     * Interaction: Control over what players & entities can do
     * 
     * Presence: Control over the presence of players & entities, including
     * spawn control
     * 
     * Damage: Control over damage, taking and dishing out
     * 
     * Effect: Applies effects to a player, like a beacon
     * 
     * @author ollieread
     *
     */
    public static enum RegionControllerType {
        PERCEPTION, INTERACTION, PRESENCE, DAMAGE, EFFECT
    }

}
