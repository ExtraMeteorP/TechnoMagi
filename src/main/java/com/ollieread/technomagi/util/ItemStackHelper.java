package com.ollieread.technomagi.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.commons.lang3.ArrayUtils;

import cofh.api.energy.IEnergyContainerItem;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityBrain;
import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.BlockSubtypes;
import com.ollieread.technomagi.common.inventory.SlotApplicable.ISlotApplicable;
import com.ollieread.technomagi.common.item.ItemSubtypes;
import com.ollieread.technomagi.common.item.entity.ItemEntityBrain;

import cpw.mods.fml.common.registry.GameRegistry;

public class ItemStackHelper
{

    public static void dropBlockAsItem(World world, int x, int y, int z, ItemStack stack)
    {
        if (!world.isRemote) {
            float f = 0.7F;
            double d0 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d1 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, x + d0, y + d1, z + d2, stack);
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }
    }

    public static boolean containsKey(Map map, ItemStack stack, boolean nbt)
    {
        for (Iterator<ItemStack> i = map.keySet().iterator(); i.hasNext();) {
            ItemStack key = i.next();

            if (nbt) {
                if (ItemStack.areItemStacksEqual(key, stack)) {
                    return true;
                }
            } else {
                if (key.isItemEqual(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean containsValue(Map map, ItemStack stack, boolean nbt)
    {
        for (Iterator<ItemStack> i = map.values().iterator(); i.hasNext();) {
            ItemStack key = i.next();

            if (nbt) {
                if (ItemStack.areItemStacksEqual(key, stack)) {
                    return true;
                }
            } else {
                if (key.isItemEqual(key)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean contains(List list, ItemStack stack, boolean nbt)
    {
        for (Iterator<ItemStack> i = list.iterator(); i.hasNext();) {
            ItemStack entry = i.next();

            if (nbt) {
                if (ItemStack.areItemStacksEqual(entry, stack)) {
                    return true;
                }
            } else {
                if (entry.isItemEqual(entry)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void spawnInWorld(ItemStack stack, World world, double x, double y, double z)
    {
        Random rand = new Random();

        float f = rand.nextFloat() * 0.8F + 0.1F;
        float f1 = rand.nextFloat() * 0.8F + 0.1F;
        float f2 = rand.nextFloat() * 0.8F + 0.1F;

        while (stack.stackSize > 0) {
            int j1 = rand.nextInt(21) + 10;

            if (j1 > stack.stackSize) {
                j1 = stack.stackSize;
            }

            stack.stackSize -= j1;
            EntityItem entityitem = new EntityItem(world, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), j1, stack.getItemDamage()));

            if (stack.hasTagCompound()) {
                entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
            }

            float f3 = 0.05F;
            entityitem.motionX = (float) rand.nextGaussian() * f3;
            entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
            entityitem.motionZ = (float) rand.nextGaussian() * f3;
            world.spawnEntityInWorld(entityitem);
        }
    }

    public static void placeInWorld(ItemStack stack, World world, double x, double y, double z)
    {
        Random rand = new Random();

        while (stack.stackSize > 0) {
            int j1 = rand.nextInt(21) + 10;

            if (j1 > stack.stackSize) {
                j1 = stack.stackSize;
            }

            stack.stackSize -= j1;
            EntityItem entityitem = new EntityItem(world, x, y, z, new ItemStack(stack.getItem(), j1, stack.getItemDamage()));

            if (stack.hasTagCompound()) {
                entityitem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
            }

            world.spawnEntityInWorld(entityitem);
        }
    }

    public static boolean matchesBlock(ItemStack stack, Block block)
    {
        if (stack != null && stack.getItem() != null) {
            Block block2 = Block.getBlockFromItem(stack.getItem());

            if (block2 != null && block2 == block) {
                return true;
            }
        }

        return false;
    }

    public static boolean matches(ItemStack stack1, ItemStack stack2)
    {
        return matches(stack1, stack2, true);
    }

    public static boolean matches(ItemStack stack1, ItemStack stack2, boolean nbt)
    {
        if (!nbt) {
            return stack1.isItemEqual(stack2);
        }

        return ItemStack.areItemStacksEqual(stack1, stack2);
    }

    public static boolean matchesOreDict(ItemStack stack1, ItemStack stack2)
    {
        boolean flag = false;

        List<Integer> thisOreIds = Arrays.asList(ArrayUtils.toObject(OreDictionary.getOreIDs(stack1)));
        List<Integer> thoseOreIds = Arrays.asList(ArrayUtils.toObject(OreDictionary.getOreIDs(stack2)));

        for (Integer id : thisOreIds) {
            if (thoseOreIds.contains(id)) {
                flag = true;
                break;
            }
        }

        return flag;
    }

    public static ItemStack brain(Class<? extends EntityLivingBase> entityClass)
    {
        IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entityClass);

        if (descriptor != null && descriptor instanceof IEntityBrain) {
            ItemEntityBrain item = (ItemEntityBrain) com.ollieread.technomagi.common.init.Items.entityBrain;
            ItemStack stack = new ItemStack(item, 1);
            int maxLife = ((IEntityBrain) descriptor).getBrainMaxLife();

            item.setEntity(stack, entityClass);
            item.setMaxLife(stack, maxLife);
            item.setLife(stack, maxLife);

            return stack;
        }

        return null;
    }

    public static ItemStack item(String name)
    {
        return item(name, 1, 0);
    }

    public static ItemStack item(String name, int count)
    {
        return item(name, count, 0);
    }

    public static ItemStack item(String name, int count, int damage)
    {
        Item item = (Item) Item.itemRegistry.getObject(name);

        if (item != null) {
            return new ItemStack(item, count, damage);
        }

        return null;
    }

    public static ItemStack itemSubtype(ItemSubtypes item, String name, int count)
    {
        return new ItemStack(item, count, item.getDamageFromName(name));
    }

    public static ItemStack block(String modid, String name)
    {
        return block(modid, name, 1, 0);
    }

    public static ItemStack block(String modid, String name, int meta)
    {
        return block(modid, name, 1, meta);
    }

    public static ItemStack block(String modid, String name, int count, int meta)
    {
        Block block = GameRegistry.findBlock(modid, name);

        if (block != null) {
            return new ItemStack(Item.getItemFromBlock(block), count, meta);
        }

        return null;
    }

    public static ItemStack block(String name)
    {
        return block(name, 1, 0);
    }

    public static ItemStack block(String name, int count)
    {
        return block(name, count, 0);
    }

    public static ItemStack block(String name, int count, int damage)
    {
        Item item = Item.getItemFromBlock(Block.getBlockFromName(name));

        if (item != null) {
            return new ItemStack(item, count, damage);
        }

        return null;
    }

    public static ItemStack blockSubtype(BlockSubtypes block, String name, int count)
    {
        return new ItemStack(Item.getItemFromBlock(block), count, block.getDamageFromName(name));
    }

    public static ItemStack blockSubtype(BlockContainerSubtypes block, String name, int count)
    {
        return new ItemStack(Item.getItemFromBlock(block), count, block.getDamageFromName(name));
    }

    public static int getFuelValue(ItemStack stack)
    {
        return TileEntityFurnace.getItemBurnTime(stack);
    }

    public static ISlotApplicable energyContainerSlot = new ISlotApplicable()
    {
        @Override
        public boolean isItemStackApplicable(ItemStack stack)
        {
            return stack.getItem() instanceof IEnergyContainerItem;
        }
    };

    public static ISlotApplicable fuelSlot = new ISlotApplicable()
    {
        @Override
        public boolean isItemStackApplicable(ItemStack stack)
        {
            return ItemStackHelper.getFuelValue(stack) > 0;
        }
    };

    public static ISlotApplicable resultSlot = new ISlotApplicable()
    {
        @Override
        public boolean isItemStackApplicable(ItemStack stack)
        {
            return false;
        }
    };

    /**
     * Get an instance of {@link ItemStackRepresentation} for the provided
     * ItemStack.
     *
     * @param stack
     * @return
     */
    public static ItemStackRepresentation getItemStackRepresentation(ItemStack stack)
    {
        return new ItemStackRepresentation(stack);
    }

    /**
     * Get an instance of {@link ItemStackRepresentation} for the provided Item
     * and damage value.
     *
     * @param item
     * @param damage
     * @return
     */
    public static ItemStackRepresentation getItemStackRepresentation(Item item, int damage)
    {
        return getItemStackRepresentation(new ItemStack(item, 1, damage));
    }

}
