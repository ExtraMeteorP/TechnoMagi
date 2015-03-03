package com.ollieread.technomagi.api.nanites;

import net.minecraft.nbt.NBTTagCompound;

public class NaniteRegen
{

    private int currentTicks = 0;

    public void regen(int maxTicks, float multiplier, EntityNanites nanites)
    {
        int diff = nanites.getMaxNanites() - nanites.getNanites();

        if (diff > 0) {
            this.currentTicks++;

            if (this.currentTicks >= maxTicks) {
                int amount = (int) Math.max(Math.ceil(nanites.getNanites() * multiplier), 1);

                if (amount > diff) {
                    amount = diff;
                }

                nanites.increaseNanites(amount);
                this.currentTicks = 0;
            }
        } else {
            this.currentTicks = 0;
        }
    }

    public void saveNBTData(NBTTagCompound compound)
    {
        compound.setInteger("CurrentTicks", this.currentTicks);
    }

    public void loadNBTData(NBTTagCompound compound)
    {
        this.currentTicks = compound.getInteger("CurrentTicks");
    }

}
