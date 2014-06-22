package com.ollieread.technomagi.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSampleExtractor extends ItemTM
{

    public ItemSampleExtractor(String name)
    {
        super(name);
        setMaxStackSize(1);
        setMaxDamage(50);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        ItemStack sample = null;
        if (entity instanceof EntityCow) {
            sample = new ItemStack(Items.itemSampleVile, 1, 1);
        } else {
            return false;
        }

        if (PlayerHelper.consumeInventoryItem(player, new ItemStack(Items.itemSampleVile, 1, 0))) {
            entity.attackEntityFrom(DamageSource.generic, 2);
            player.swingItem();
            player.inventory.addItemStackToInventory(sample);

            return true;
        }

        return false;
    }
}
