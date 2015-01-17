package com.ollieread.technomagi.tileentity;

import net.minecraft.item.ItemStack;

public interface ITileEntityStorage
{

    public void setItem(ItemStack item);

    public void setAmount(int c);

    public void setCapacity(int c);

    public ItemStack getItem();

    public int getAmount();

    public int getCapacity();

    public int deposit(ItemStack item, boolean doDeposit);

    public int deposit(ItemStack item, int count, boolean doDeposit);

    public ItemStack withdraw(boolean doWithdrawal);

    public ItemStack withdraw(boolean doWithdrawal, int count);

    public String getLocalizedName();

    public String getUnlocalizedName();

    public boolean isStackEqual(ItemStack compare);

}
