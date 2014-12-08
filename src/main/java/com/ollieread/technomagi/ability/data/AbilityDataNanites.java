package com.ollieread.technomagi.ability.data;

import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.ennds.ability.IAbilityData;

public class AbilityDataNanites implements IAbilityData
{

    public int timer = 0;

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        timer = compound.getInteger("Timer");
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setInteger("Timer", timer);
    }

}
