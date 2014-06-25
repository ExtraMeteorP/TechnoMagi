package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.extended.ExtendedPlayerKnowledge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemNaniteContainer extends ItemTM
{

    @SideOnly(Side.CLIENT)
    protected IIcon itemIconFull;

    public ItemNaniteContainer(String name)
    {
        super(name);

        setMaxStackSize(64);
        setHasSubtypes(true);
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return StatCollector.translateToLocal(this.getUnlocalizedName() + (stack.getItemDamage() > 0 ? ".full.name" : ".name"));
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        String info = "";

        switch (stack.getItemDamage()) {
            default:
                return;
            case 1:
                info = EnumChatFormatting.DARK_PURPLE + "Player";
                break;
            case 2:
                info = EnumChatFormatting.DARK_GREEN + "Cow";
                break;
            case 3:
                info = EnumChatFormatting.DARK_GREEN + "Sheep";
                break;
            case 4:
                info = EnumChatFormatting.DARK_GREEN + "Pig";
                break;
            case 5:
                info = EnumChatFormatting.DARK_GREEN + "Chicken";
                break;
        }

        list.add(info);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString());
        itemIconFull = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "Full");
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        list.add(new ItemStack(item, 1, 0));
        list.add(new ItemStack(item, 1, 1));
        list.add(new ItemStack(item, 1, 2));
        list.add(new ItemStack(item, 1, 3));
        list.add(new ItemStack(item, 1, 4));
        list.add(new ItemStack(item, 1, 5));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int par1)
    {
        return par1 > 0 ? itemIconFull : itemIcon;
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
