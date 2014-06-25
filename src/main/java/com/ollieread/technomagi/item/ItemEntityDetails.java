package com.ollieread.technomagi.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEntityDetails extends ItemTM
{

    public ItemEntityDetails(String name)
    {
        super(name);

        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        ExtendedPlayerKnowledge charon = ExtendedPlayerKnowledge.get(player);

        if (charon != null && !charon.canSpecialise()) {
            if (stack.getItemDamage() == 0) {
                if (charon.nanites.decreaseNanites(10)) {
                    player.inventory.addItemStackToInventory(new ItemStack(this, 1, 1));
                    stack.stackSize--;

                    if (stack.stackSize == 0) {
                        stack = null;
                    }
                }
            }
        }

        return stack;
    }

}
