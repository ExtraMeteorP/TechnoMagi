package com.ollieread.technomagi.tileentity;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.ennds.research.IResearch;
import com.ollieread.ennds.research.IResearchAnalysis;
import com.ollieread.ennds.research.ResearchRegistry;

public class TileEntityAnalysis extends TileEntityResearch
{

    protected Random rand = new Random();

    public TileEntityAnalysis()
    {
        super(9);
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (canAnalyse()) {
                if (inProgress()) {
                    analyse();
                }
            } else {
                waiting++;

                if (waiting == 30) {
                    waiting = 0;
                    setInProgress(false);
                }
            }
        }
    }

    public boolean canAnalyse()
    {
        if (data == 100) {
            return false;
        }
        IResearchAnalysis analysis = getResearchAnalysis();
        if (analysis != null) {
            IResearch research = (IResearch) analysis;
            EntityPlayer player = worldObj.getPlayerEntityByName(this.getPlayer());

            if (player != null) {
                ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

                if (charon.hasResearched(research.getName())) {
                    return false;
                }

                if (player != null && research.getProgress() + data > 100 && research.canPerform(charon)) {
                    return false;
                }
            } else if (!inProgress) {
                return false;
            }

            return true;
        }

        return false;
    }

    public void analyse()
    {
        ticks++;

        if (ticks >= 20) {
            ticks = 0;
            progress++;
        }

        if (progress >= 100) {
            IResearchAnalysis analysis = getResearchAnalysis();
            int c = rand.nextInt(analysis.getChance()) + 1;

            IResearch research = (IResearch) analysis;
            EntityPlayer player = worldObj.getPlayerEntityByName(this.getPlayer());

            ResearchRegistry.researchAnalysis(Arrays.asList(inventory), ExtendedPlayerKnowledge.get(player), this, c);

            data += research.getProgress();

            reduceStacks(1);
            setInProgress(false);
            progress = 0;
        }
    }

    private IResearchAnalysis getResearchAnalysis()
    {
        return ResearchRegistry.findMatchingAnalysis(Arrays.asList(inventory));
    }
}
