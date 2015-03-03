package com.ollieread.technomagi.common.ability.data;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.technomagi.api.ability.IAbilityData;

public class AbilityDataScan implements IAbilityData
{

    public ItemStack stack;
    public AbilityUseTarget target;
    public Block block;
    public int metadata;
    public Entity entity;
    public NBTTagCompound entityCompound;
    public int blockX;
    public int blockY;
    public int blockZ;

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        if (compound.hasKey("Stack")) {
            stack = ItemStack.loadItemStackFromNBT(compound.getCompoundTag("Stack"));
        }

        if (compound.hasKey("Target")) {
            target = AbilityUseTarget.values()[compound.getInteger("Target")];
        }

        if (compound.hasKey("Block")) {
            block = (Block) Block.blockRegistry.getObject(compound.getString("Block"));
        }

        metadata = compound.getInteger("Metadata");
        blockX = compound.getInteger("BlockX");
        blockY = compound.getInteger("BlockY");
        blockZ = compound.getInteger("BlockZ");

        if (compound.hasKey("Entity")) {
            entityCompound = compound.getCompoundTag("Entity");
        }
    }

    public void loadEntity(World world)
    {
        if (entityCompound != null) {
            entity = EntityList.createEntityFromNBT(entityCompound, world);
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        if (stack != null) {
            compound.setTag("Stack", stack.writeToNBT(new NBTTagCompound()));
        }

        if (target != null) {
            compound.setInteger("Target", target.ordinal());
        }

        if (block != null) {
            compound.setString("Block", Block.blockRegistry.getNameForObject(block));
        }

        compound.setInteger("Metadata", metadata);
        compound.setInteger("BlockX", blockX);
        compound.setInteger("BlockY", blockY);
        compound.setInteger("BlockZ", blockZ);

        if (entity != null) {
            NBTTagCompound entityCompound = new NBTTagCompound();
            entity.writeToNBT(entityCompound);
            compound.setTag("Entity", entityCompound);
        }
    }

    @Override
    public boolean requiresUpdate()
    {
        return false;
    }

    @Override
    public void update()
    {

    }

}
