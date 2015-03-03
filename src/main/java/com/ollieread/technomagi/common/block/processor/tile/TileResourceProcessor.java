package com.ollieread.technomagi.common.block.processor.tile;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.crafting.CraftingHandler;
import com.ollieread.technomagi.api.crafting.CraftingHandler.ProcessorRecipes.ProcessorType;
import com.ollieread.technomagi.api.crafting.IProcessorComponent;
import com.ollieread.technomagi.api.crafting.IProcessorRecipe;
import com.ollieread.technomagi.common.block.tile.ISideFacing;
import com.ollieread.technomagi.common.block.tile.ITileProcessor;
import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.component.Inventory;
import com.ollieread.technomagi.common.component.Progress;

public abstract class TileResourceProcessor extends TileBase implements IInventory, ITileProcessor, ISideFacing
{

    protected Inventory inventory;
    protected Progress progress = new Progress();
    protected boolean isProcessing = false;
    protected IProcessorRecipe currentRecipe = null;
    protected Random rand = new Random();
    protected float modifier = 1F;
    protected ForgeDirection direction;

    public IInventory getInventory()
    {
        return inventory;
    }

    public abstract void consumeEnergy();

    public abstract boolean hasSufficientEnergy();

    public abstract int getInputSlot();

    public abstract int getComponentSlot();

    public abstract int getOutputSlot();

    public abstract int getByProductSlot();

    @Override
    public void updateEntity()
    {
        super.updateEntity();

        if (!worldObj.isRemote) {
            if (canProcess()) {
                if (!isProcessing()) {
                    ItemStack componentStack = inventory.getStackInSlot(getComponentSlot());

                    if (progress.getMaxProgress() == 0) {
                        progress.setMaxProgress((int) (((IProcessorComponent) componentStack.getItem()).getMaxDuration(componentStack) * modifier));
                    }

                    currentRecipe = CraftingHandler.processor.find(((IProcessorComponent) componentStack.getItem()).getType(componentStack), inventory.getStackInSlot(2));
                    isProcessing = true;
                }

                progress.incrementProgress();
                consumeEnergy();

                if (progress.isMaxProgress()) {
                    process();
                }
            } else {
                if (inventory.getStackInSlot(getComponentSlot()) == null || inventory.getStackInSlot(getInputSlot()) == null) {
                    progress.resetProgress();
                }
            }
        }
    }

    @Override
    public boolean canProcess()
    {
        if (isProcessing() && currentRecipe != null && hasSufficientEnergy()) {
            return true;
        }

        ItemStack componentStack = inventory.getStackInSlot(getComponentSlot());

        if (hasSufficientEnergy() && componentStack != null && componentStack.getItem() != null) {
            ItemStack inputStack = inventory.getStackInSlot(getInputSlot());
            IProcessorComponent component = ((IProcessorComponent) componentStack.getItem());
            ProcessorType type = component.getType(componentStack);
            int damage = component.getCurrentDamage(componentStack);
            IProcessorRecipe recipe = CraftingHandler.processor.find(type, inputStack);

            if ((damage + recipe.getDamage(type)) <= component.getMaxDamage(componentStack)) {
                ItemStack output = recipe.getOutput(type);
                ItemStack currentOutput = inventory.getStackInSlot(getOutputSlot());

                if (currentOutput == null || (currentOutput.isItemEqual(output) && (currentOutput.stackSize + output.stackSize) < currentOutput.getMaxStackSize())) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public void process()
    {
        ItemStack componentStack = inventory.getStackInSlot(getComponentSlot());
        ItemStack inputStack = inventory.getStackInSlot(getInputSlot());
        ItemStack currentOutput = inventory.getStackInSlot(getOutputSlot());
        ItemStack currentByProduct = inventory.getStackInSlot(getByProductSlot());
        IProcessorComponent component = (IProcessorComponent) componentStack.getItem();

        ProcessorType type = component.getType(componentStack);
        ItemStack output = currentRecipe.getOutput(type);

        boolean flag = false;

        if (currentOutput == null) {
            ItemStack newOutput = output.copy();
            int chance = component.getDuplicationChance(componentStack);

            if (rand.nextInt(chance) == 0) {
                newOutput.stackSize++;
            }

            inventory.setInventorySlotContents(getOutputSlot(), newOutput);
            currentOutput = newOutput;
            flag = true;
        } else if (currentOutput.isItemEqual(output) && (currentOutput.stackSize + output.stackSize) <= currentOutput.getMaxStackSize()) {
            currentOutput.stackSize += output.stackSize;
            flag = true;
        }

        if (flag) {
            if (currentOutput.stackSize <= (currentOutput.getMaxStackSize() - 1)) {
                int duplicationChance = (int) (component.getDuplicationChance(componentStack) * modifier);

                if (rand.nextInt(duplicationChance) == 0) {
                    currentOutput.stackSize++;
                }
            }

            ItemStack byProduct = currentRecipe.getByProduct(type);
            int byProductChance = (int) (currentRecipe.getByProductChance(type) * modifier);

            if (rand.nextInt(byProductChance) == 0) {
                if (currentByProduct == null) {
                    inventory.setInventorySlotContents(getByProductSlot(), byProduct);
                } else if ((currentByProduct.stackSize + byProduct.stackSize) <= currentByProduct.getMaxStackSize()) {
                    currentByProduct.stackSize += byProduct.stackSize;
                }
            }

            decrStackSize(getInputSlot(), 1);
            component.setCurrentDamage(componentStack, component.getCurrentDamage(componentStack) + currentRecipe.getDamage(type));

            if (component.isBroken(componentStack)) {
                decrStackSize(getComponentSlot(), 1);
            }
        }

        isProcessing = false;
        progress.resetProgress();
        currentRecipe = null;
    }

    @Override
    public boolean isProcessing()
    {
        return this.isProcessing;
    }

    @Override
    public int getProgress()
    {
        return progress.getProgress();
    }

    @Override
    public int getProgressScaled(int scale)
    {
        return (scale / progress.getMaxProgress()) * progress.getProgress();
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Direction", direction.ordinal());
        compound.setFloat("Modifier", modifier);

        NBTTagCompound inventoryCompound = new NBTTagCompound();
        inventory.writeToNBT(inventoryCompound);
        compound.setTag("Inventory", inventoryCompound);

        NBTTagCompound progressCompound = new NBTTagCompound();
        progress.writeToNBT(progressCompound);
        compound.setTag("Progress", progressCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        direction = ForgeDirection.values()[compound.getInteger("Direction")];
        modifier = compound.getFloat("Modifier");

        inventory.readFromNBT(compound.getCompoundTag("Inventory"));
        progress.readFromNBT(compound.getCompoundTag("Progress"));
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
        if (slot == 0) {
            return stack.getItem() != null && stack.getItem() instanceof IProcessorComponent;
        }

        return false;
    }

    @Override
    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }

    @Override
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public void rotate()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (int i = 0; i < directions.length; i++) {
            ForgeDirection direction = directions[i];

            if (direction.equals(getDirection()) || direction.equals(ForgeDirection.UP) || direction.equals(ForgeDirection.DOWN)) {
                continue;
            }

            setDirection(direction);
            markDirty();
            break;
        }
    }

}
