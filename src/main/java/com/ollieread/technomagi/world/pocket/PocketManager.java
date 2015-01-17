package com.ollieread.technomagi.world.pocket;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.ITileEntityPocket;

public class PocketManager
{

    public static Map<Integer, PocketManager> dimensionManagers = new HashMap<Integer, PocketManager>();
    private int dimension;

    protected List<WeakReference<ITileEntityPocket>> pockets = new ArrayList<WeakReference<ITileEntityPocket>>();

    public static PocketManager getInstance(int dimension)
    {
        if (!dimensionManagers.containsKey(dimension)) {
            for (int i = 0; i < Config.perceptionDimensions.length; i++) {
                if (Config.perceptionDimensions[i] == dimension) {
                    dimensionManagers.put(dimension, new PocketManager(dimension));
                }
            }
        }

        return dimensionManagers.get(dimension);
    }

    protected PocketManager(int dimension)
    {
        this.dimension = dimension;
    }

    public void addPocket(ITileEntityPocket pocket)
    {
        if (!pockets.contains(pocket)) {
            pockets.add(new WeakReference<ITileEntityPocket>(pocket));
        }
    }

    public void removePocket(ITileEntityPocket pocket)
    {
        pockets.remove(new WeakReference<ITileEntityPocket>(pocket));
    }

    public boolean isInside(int x, int z)
    {
        return getPocketForCoords(x, z) != null;
    }

    public ITileEntityPocket getPocketForCoords(int x, int z)
    {
        for (WeakReference<ITileEntityPocket> reference : pockets) {
            ITileEntityPocket pocket = reference.get();

            if (pocket != null) {
                if (pocket.isInside(x, z)) {
                    return pocket;
                }
            } else {
                removePocket(pocket);
                continue;
            }
        }

        return null;
    }

}
