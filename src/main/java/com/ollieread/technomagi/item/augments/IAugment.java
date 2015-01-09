package com.ollieread.technomagi.item.augments;

import java.util.Map;

import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.technomagi.item.augments.AugmentRegistry.AugmentType;

public interface IAugment
{

    public String getUnlocalisedName();

    public boolean isValid(AugmentType type);

    public Map<IAttribute, AttributeModifier> getModifiers();

    public void applyAugment(EntityPlayer player);

    public void resetAugment(EntityPlayer player);

    public boolean shouldUpdate();

    public void update(EntityPlayer player);

    public void writeToNBT(NBTTagCompound compound);

    public void readFromNBT(NBTTagCompound compound);

}
