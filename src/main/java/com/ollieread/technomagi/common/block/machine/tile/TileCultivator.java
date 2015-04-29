package com.ollieread.technomagi.common.block.machine.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntitySample;
import com.ollieread.technomagi.api.tile.ISideFacing;
import com.ollieread.technomagi.api.tile.ITileGui;
import com.ollieread.technomagi.api.tile.ITileProcessor;
import com.ollieread.technomagi.client.gui.window.WindowCultivator;
import com.ollieread.technomagi.client.gui.window.abstracts.Window;
import com.ollieread.technomagi.common.block.machine.container.ContainerCultivator;
import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.component.Facing;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.common.component.Progress;
import com.ollieread.technomagi.common.item.entity.ItemEntityNanites;
import com.ollieread.technomagi.common.item.entity.ItemEntitySample;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class TileCultivator extends TileBase implements ISideFacing, ITileProcessor, IInventory, ITileGui
{

    protected Facing facing = new Facing();
    protected Progress progress = new Progress();
    protected Inventory inventory;

    protected int maxNanites = 0;
    protected int maxSample = 0;
    protected int maxCultivated = 0;
    protected int currentNanites = 0;
    protected int currentSample = 0;
    protected int currentCultivated = 0;

    protected String entityNanites = null;
    protected String entitySample = null;

    protected float modifier = 1F;
    protected float increasePer = 0F;
    protected int chance = 1;

    protected int consumeNanites = 1;
    protected int consumeSample = 1;

    public TileCultivator()
    {
        this.inventory = new Inventory("cultivator", 4);
    }

    public abstract void consume();

    @Override
    public boolean canProcess()
    {
        return progress.getMaxProgress() > 0 && entityNanites != null && entitySample != null && currentNanites >= consumeNanites && currentSample >= consumeSample && currentCultivated < maxCultivated;
    }

    @Override
    public void process()
    {
        if (!entityNanites.equals(entitySample)) {
            currentNanites -= consumeNanites;
        }

        currentSample -= consumeSample;

        int increase = (int) (Math.max((5 * modifier), 1) + (currentCultivated * increasePer));

        if (worldObj.rand.nextInt(chance) == 0) {
            if ((currentCultivated + increase) <= maxCultivated) {
                currentCultivated += increase;
            } else {
                currentCultivated = maxCultivated;
            }
        }

        if (currentNanites == 0) {
            entityNanites = null;
        }

        if (currentSample == 0) {
            entitySample = null;
        }
    }

    @Override
    public boolean isProcessing()
    {
        return progress.getMaxProgress() > 0 && entityNanites != null && entitySample != null;
    }

    public void populate()
    {
        ItemStack empty = inventory.getStackInSlot(2);

        if (entitySample != null && currentCultivated >= ItemEntityNanites.CAPACITY) {
            if (empty != null && empty.getItem() instanceof ItemEntityNanites) {
                if (((ItemEntityNanites) empty.getItem()).isEmpty(empty)) {
                    ItemStack result = inventory.getStackInSlot(3);

                    if (result == null) {
                        ItemStack newStack = empty.copy();
                        newStack.stackSize = 1;
                        ((ItemEntityNanites) newStack.getItem()).setEntity(newStack, entitySample);
                        inventory.setInventorySlotContents(3, newStack);
                        inventory.decrStackSize(2, 1);
                        currentCultivated -= ItemEntityNanites.CAPACITY;
                    } else if ((result.isItemEqual(empty) && ((ItemEntityNanites) empty.getItem()).getEntity(result).equals(entitySample))) {
                        if (result.stackSize < result.getMaxStackSize()) {
                            result.stackSize++;
                            inventory.decrStackSize(2, 1);
                            currentCultivated -= ItemEntityNanites.CAPACITY;
                        }
                    }
                }
            }
        }

        ItemStack nanites = inventory.getStackInSlot(0);
        ItemStack sample = inventory.getStackInSlot(1);

        if ((currentNanites + ItemEntityNanites.CAPACITY) <= maxNanites) {
            if (nanites != null && nanites.getItem() instanceof ItemEntityNanites) {
                if (entityNanites != null && ((ItemEntityNanites) nanites.getItem()).getEntity(nanites).equals(entityNanites)) {
                    currentNanites += ItemEntityNanites.CAPACITY;
                    inventory.decrStackSize(0, 1);
                } else if (entityNanites == null) {
                    entityNanites = ((ItemEntityNanites) nanites.getItem()).getEntity(nanites);
                    currentNanites += ItemEntityNanites.CAPACITY;
                    inventory.decrStackSize(0, 1);
                }
            }
        }

        if (currentSample < maxSample) {
            if (sample != null && sample.getItem() instanceof ItemEntitySample) {
                String sampleEntity = ((ItemEntitySample) sample.getItem()).getEntity(sample);
                IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(sampleEntity);

                if (descriptor instanceof IEntitySample) {
                    float volume = ((IEntitySample) descriptor).getSampleVolume();
                    int add = (int) Math.ceil(Math.max(5 / volume, 1));

                    if ((currentSample + add) <= maxSample) {
                        if (entitySample != null && entitySample.equals(sampleEntity)) {
                            currentSample += add;
                            inventory.decrStackSize(1, 1);
                        } else if (entitySample == null) {
                            entitySample = sampleEntity;
                            currentSample += add;
                            inventory.decrStackSize(1, 1);
                        }
                    }
                }
            }
        }

        if (entityNanites != null && entitySample != null && progress.getMaxProgress() == 0 && increasePer == 0) {
            IEntityDescriptor naniteDescriptor = TechnomagiApi.entity().getEntity(entityNanites);
            IEntityDescriptor sampleDescriptor = TechnomagiApi.entity().getEntity(entitySample);

            if (entityNanites.equals(entitySample)) {
                progress.setMaxProgress(((IEntityResearchNanites) naniteDescriptor).getNaniteRegenTicks());
                increasePer = ((IEntityResearchNanites) naniteDescriptor).getNaniteRegen() * modifier;
            } else {
                if (naniteDescriptor.isUndead()) {
                    if (sampleDescriptor.isUndead()) {
                        progress.setMaxProgress(100);
                        increasePer = 0.05F;
                    } else {
                        progress.setMaxProgress(200);
                        increasePer = 0.025F;
                    }
                } else {
                    if (naniteDescriptor.isMonster()) {
                        if (sampleDescriptor.isMonster()) {
                            progress.setMaxProgress(100);
                            increasePer = 0.05F;
                        } else {
                            progress.setMaxProgress(200);
                            increasePer = 0.025F;
                        }
                    } else {
                        if (sampleDescriptor.isMonster()) {
                            progress.setMaxProgress(250);
                            increasePer = 0.025F;
                        } else {
                            progress.setMaxProgress(100);
                            increasePer = 0.05F;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            populate();

            if (canProcess()) {
                progress.incrementProgress();
                consume();

                if (progress.isMaxProgress()) {
                    process();
                    progress.resetProgress();
                    progress.setMaxProgress(0);
                    increasePer = 0;
                }
            }
        }
    }

    public void setNanites(int amount)
    {
        currentNanites = amount;
    }

    public int getNanites()
    {
        return currentNanites;
    }

    public int getMaxNanites()
    {
        return maxNanites;
    }

    public int getNanitesScaled(int scale)
    {
        return maxNanites > 0 ? currentNanites * scale / maxNanites : 0;
    }

    public void setSample(int amount)
    {
        currentSample = amount;
    }

    public int getSample()
    {
        return currentSample;
    }

    public int getMaxSample()
    {
        return maxSample;
    }

    public int getSampleScaled(int scale)
    {
        return maxSample > 0 ? currentSample * scale / maxSample : 0;
    }

    public void setCultivated(int amount)
    {
        currentCultivated = amount;
    }

    public int getCultivated()
    {
        return currentCultivated;
    }

    public int getMaxCultivated()
    {
        return maxCultivated;
    }

    public int getCultivatedScaled(int scale)
    {
        return maxCultivated > 0 ? currentCultivated * scale / maxCultivated : 0;
    }

    public String getNaniteEntity(boolean localised)
    {
        if (localised) {
            return StatCollector.translateToLocal("entity." + entityNanites + ".name");
        }

        return entityNanites;
    }

    public String getSampleEntity(boolean localised)
    {
        if (localised) {
            return StatCollector.translateToLocal("entity." + entitySample + ".name");
        }

        return entitySample;
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

    @Override
    public void setDirection(ForgeDirection direction)
    {
        facing.setDirection(direction);
    }

    @Override
    public ForgeDirection getDirection()
    {
        return facing.getDirection();
    }

    @Override
    public void rotate()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (ForgeDirection direction : directions) {
            if (direction.equals(getDirection()) || direction.equals(ForgeDirection.UP) || direction.equals(ForgeDirection.DOWN)) {
                continue;
            }

            setDirection(direction);
            markDirty();
            break;
        }
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
        return inventory.getStackInSlot(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        inventory.setInventorySlotContents(slot, stack);
    }

    @Override
    public String getInventoryName()
    {
        return inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit()
    {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player)
    {
        return inventory.isUseableByPlayer(player);
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
        return true;
    }

    @Override
    public Container getContainer(EntityPlayer player)
    {
        return new ContainerCultivator(player.inventory, this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Window getWindow(EntityPlayer player)
    {
        return new WindowCultivator((ContainerCultivator) getContainer(player));
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        NBTTagCompound directionCompound = new NBTTagCompound();
        NBTTagCompound progressCompound = new NBTTagCompound();
        NBTTagCompound inventoryCompound = new NBTTagCompound();

        facing.writeToNBT(directionCompound);
        progress.writeToNBT(progressCompound);
        inventory.writeToNBT(inventoryCompound);

        compound.setTag("Direction", directionCompound);
        compound.setTag("Progress", progressCompound);
        compound.setTag("Inventory", inventoryCompound);

        compound.setInteger("CurrentNanites", currentNanites);
        compound.setInteger("CurrentSample", currentSample);
        compound.setInteger("CurrentCultivated", currentCultivated);
        compound.setFloat("IncreasePer", increasePer);

        if (entityNanites != null) {
            compound.setString("EntityNanites", entityNanites);
        }

        if (entitySample != null) {
            compound.setString("EntitySample", entitySample);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        facing.readFromNBT(compound.getCompoundTag("Direction"));
        progress.readFromNBT(compound.getCompoundTag("Progress"));
        inventory.readFromNBT(compound.getCompoundTag("Inventory"));

        currentNanites = compound.getInteger("CurrentNanites");
        currentSample = compound.getInteger("CurrentSample");
        currentCultivated = compound.getInteger("CurrentCultivated");
        increasePer = compound.getFloat("IncreasePer");

        if (compound.hasKey("EntityNanites")) {
            entityNanites = compound.getString("EntityNanites");
        }

        if (compound.hasKey("EntitySample")) {
            entitySample = compound.getString("EntitySample");
        }
    }

}
