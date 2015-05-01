package com.ollieread.technomagi.common.block.research.tile;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Chance;
import com.ollieread.technomagi.api.event.TechnomagiHooks;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.knowledge.research.IResearcher;
import com.ollieread.technomagi.api.knowledge.research.Researcher;
import com.ollieread.technomagi.api.tile.ITileGui;
import com.ollieread.technomagi.api.tile.ITilePlayerLocked;
import com.ollieread.technomagi.api.tile.ITileProcessor;
import com.ollieread.technomagi.client.gui.window.WindowAnalyser;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.research.container.ContainerAnalyser;
import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.common.component.Progress;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.PlayerHelper;

public class TileAnalyser extends TileBase implements IInventory, ITilePlayerLocked, ITileProcessor, IResearcher, ITileGui
{

    protected Inventory inventory = new Inventory("analyse", 3);
    protected Progress progress = new Progress();
    protected Researcher researcher = new Researcher();
    protected String player;
    protected String research = null;
    protected Map<String, Integer> researchData = new ConcurrentHashMap<String, Integer>();
    protected int data = 0;
    protected int maxData = 50;

    public TileAnalyser()
    {
        super();
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            EntityPlayer playerEntity = this.getPlayerEntity();

            if (playerEntity != null) {
                if (research == null) {
                    PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(playerEntity);
                    ItemStack focus = this.getStackInSlot(0);

                    if (focus != null && focus.getItem() != null) {
                        List<String> mappings = TechnomagiApi.scan().getAnalysisMapping(ItemStackHelper.getItemStackRepresentation(focus));

                        if (mappings != null && mappings.size() > 0) {
                            for (String mapping : mappings) {
                                IResearch r = TechnomagiApi.knowledge().getResearch(mapping);

                                if (r != null) {
                                    if (canResearch(r) && technomage.knowledge().canDiscover(TechnomagiApi.getKnowledge(r.getKnowledge()))) {
                                        research = mapping;
                                        progress.setMaxProgress(2400);
                                    }
                                }
                            }
                        }
                    }
                } else if (isProcessing()) {

                }
            }

            if (canProcess()) {
                progress.incrementProgress();

                if (progress.isMaxProgress()) {
                    process();
                }
            } else {
                if (true) {
                    progress.resetProgress();
                }
            }
        }
    }

    @Override
    public boolean canProcess()
    {
        EntityPlayer playerEntity = this.getPlayerEntity();
        return playerEntity != null && progress.getMaxProgress() > 0 && research != null;
    }

    @Override
    public void process()
    {
        EntityPlayer playerEntity = this.getPlayerEntity();
        PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(playerEntity);
        IResearch iresearch = TechnomagiApi.getResearch(research);
        Random rand = worldObj.rand;

        if (iresearch != null) {
            int chance = iresearch.getChance();
            Technomagi.debug("Chance: " + chance);
            Chance chanceEvent = TechnomagiHooks.researchChance(playerEntity, iresearch, iresearch.getChance());
            Technomagi.debug("Modified Chance: " + chanceEvent.chance);

            if (rand.nextInt(chanceEvent.chance) == 0) {
                if (researchData.containsKey(research)) {
                    researchData.put(research, researchData.get(research) + iresearch.getProgress());
                }
            }
        }

        if (rand.nextInt(5) != 0) {
            decrStackSize(0, 1);
        }

        progress.resetProgress();
        progress.setMaxProgress(0);
        research = null;
    }

    @Override
    public boolean isProcessing()
    {
        return research != null && progress.getMaxProgress() > 0;
    }

    public int getData()
    {
        return data;
    }

    public int getDataScaled(int scale)
    {
        return maxData > 0 ? data * scale / maxData : 0;
    }

    public void setData(int data)
    {
        this.data = data;
    }

    public int getMaxData()
    {
        return maxData;
    }

    public void setMaxData(int maxData)
    {
        this.maxData = maxData;
    }

    @Override
    public EntityPlayer getPlayerEntity()
    {
        if (player != null && !player.isEmpty()) {
            return worldObj.getPlayerEntityByName(player);
        }

        return null;
    }

    @Override
    public String getPlayer()
    {
        return player;
    }

    @Override
    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

    @Override
    public boolean isPlayer(String player)
    {
        if (this.player != null && !this.player.isEmpty()) {
            return this.player.equals(player);
        }

        return false;
    }

    @Override
    public void setPlayer(EntityPlayer player)
    {
        setPlayer(player.getCommandSenderName());
    }

    @Override
    public void setPlayer(String player)
    {
        this.player = player;
    }

    @Override
    public boolean isPlayerLocked()
    {
        return true;
    }

    @Override
    public int getSizeInventory()
    {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount)
    {
        return inventory.decrStackSize(slot, amount);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot)
    {
        return inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory.setInventorySlotContents(slot, stack);

        if (slot == 0 && (stack == null || stack.stackSize == 0)) {
            progress.resetProgress();
            progress.setMaxProgress(0);
            research = null;
        }
    }

    @Override
    public String getInventoryName()
    {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return isPlayer(player);
    }

    @Override
    public void openInventory()
    {
        inventory.openInventory();
    }

    @Override
    public void closeInventory()
    {
        inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack)
    {
        return inventory.isItemValidForSlot(slot, stack);
    }

    @Override
    public int getProgress()
    {
        return progress.getProgress();
    }

    public int getMaxProgress()
    {
        return progress.getMaxProgress();
    }

    public void setMaxProgress(int maxProgress)
    {
        this.progress.setMaxProgress(maxProgress);
    }

    public void setProgress(int progress)
    {
        this.progress.setProgress(progress);
    }

    @Override
    public int getProgressScaled(int scale)
    {
        return progress.getMaxProgress() > 0 ? progress.getProgress() * scale / progress.getMaxProgress() : 0;
    }

    public int getProgressPercentage()
    {
        return progress.getMaxProgress() > 0 ? progress.getProgress() / (progress.getMaxProgress() / 100) : 0;
    }

    @Override
    public boolean canResearch(IResearch research)
    {
        return researcher.canResearch(research) && (research.getProgress() + data) <= maxData;
    }

    @Override
    public int addResearch(IResearch research, int modifier)
    {
        return researcher.addResearch(research, modifier);
    }

    @Override
    public void copyFrom(IResearcher research)
    {
        researcher.copyFrom(research);
    }

    @Override
    public List<String> getCompleteResearch()
    {
        return researcher.getCompleteResearch();
    }

    @Override
    public Map<String, Integer> getResearchRepetition()
    {
        return researcher.getResearchRepetition();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound inventoryCompound = new NBTTagCompound();
        NBTTagCompound progressCompound = new NBTTagCompound();
        NBTTagCompound researcherCompound = new NBTTagCompound();

        inventory.writeToNBT(inventoryCompound);
        progress.writeToNBT(progressCompound);
        researcher.saveNBTData(researcherCompound);

        compound.setTag("Inventory", inventoryCompound);
        compound.setTag("Progress", progressCompound);
        compound.setTag("Researcher", researcherCompound);

        if (player != null && !player.isEmpty()) {
            compound.setString("Player", player);
        }

        if (research != null && !research.isEmpty()) {
            compound.setString("Research", research);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        inventory.readFromNBT(compound.getCompoundTag("Inventory"));
        progress.readFromNBT(compound.getCompoundTag("Progress"));
        researcher.loadNBTData(compound.getCompoundTag("Researcher"));

        if (compound.hasKey("Player")) {
            player = compound.getString("Player");
        }

        if (compound.hasKey("Research")) {
            research = compound.getString("Research");
        }
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return new ContainerAnalyser(player.inventory, this);
    }

    @Override
    public Window getWindow(EntityPlayer player)
    {
        return new WindowAnalyser(((ContainerAnalyser) getContainer(player)));
    }

}