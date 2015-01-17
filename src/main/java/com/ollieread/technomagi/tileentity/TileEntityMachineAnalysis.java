package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.ResearchRegistry;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityMachineResearch;
import com.ollieread.technomagi.tileentity.component.ComponentInventory;

public class TileEntityMachineAnalysis extends TileEntityMachineResearch implements IInventory
{

    protected int maxWaiting;

    public TileEntityMachineAnalysis()
    {
        super(Config.analysisPowerMax, Config.analysisPowerRecieve, 0, 100, new ComponentInventory(9));

        maxWaiting = Config.analysisWaitingMax;
        setMaxProgress(Config.analysisProgressMax);
        setUsage(Config.analysisPowerUse);
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    @Override
    public boolean isProcessing()
    {
        return inProgress;
    }

    public boolean canProcess()
    {
        if (data == 100) {
            return false;
        }

        IResearchAnalysis analysis = getResearchAnalysis();
        EntityPlayer player = getOwner(worldObj);

        if (analysis != null) {
            IResearch research = (IResearch) analysis;

            if (!inProgress()) {
                if (player != null) {
                    ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                    if (!charon.canSpecialise()) {
                        if (charon.hasResearched(research.getName()) || charon.hasKnowledge(research.getKnowledge())) {
                            return false;
                        }

                        if (player != null && research.getProgress() + data > 100 && research.canPerform(charon)) {
                            return false;
                        }
                    }
                }
            }

            return getEnergyStored(null) > usage;
        }

        return false;
    }

    public void process()
    {
        if (energy.modifyEnergyStored(getUsage())) {
            setProgress(getProgress() + 1);

            if (getProgress() >= getMaxProgress()) {
                IResearch research = (IResearch) getResearchAnalysis();
                addResearch(research.getKnowledge(), research.getProgress());
                reduceStacks(1);
                setInProgress(false);
                setProgress(0);
            }

            sync();
        }
    }

    private IResearchAnalysis getResearchAnalysis()
    {
        return ResearchRegistry.findMatchingAnalysis(inventory);
    }

    @Override
    public boolean isConnected()
    {
        return false;
    }

}
