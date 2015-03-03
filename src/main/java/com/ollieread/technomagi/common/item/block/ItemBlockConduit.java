package com.ollieread.technomagi.common.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidContainerRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemBlockConduit extends ItemBlockBase
{

    public ItemBlockConduit(Block block)
    {
        super(block);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        int i = stack.getItemDamage();

        return i == 0 || i == 3 ? EnumRarity.common : i == 1 || i == 4 ? EnumRarity.uncommon : EnumRarity.rare;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean advanced)
    {
        switch (stack.getItemDamage()) {
            case 0:
                list.add(512 + " RF/t");
                return;
            case 1:
                list.add(2048 + " RF/t");
                return;
            case 2:
                list.add(4096 + " RF/t");
                return;
            case 3:
                list.add(FluidContainerRegistry.BUCKET_VOLUME * 10 + " mb");
                return;
            case 4:
                list.add(FluidContainerRegistry.BUCKET_VOLUME * 100 + " mb");
                return;
            case 5:
                list.add(FluidContainerRegistry.BUCKET_VOLUME * 1000 + " mb");
                return;
        }
    }

}
