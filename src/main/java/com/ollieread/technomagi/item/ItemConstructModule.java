package com.ollieread.technomagi.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.ollieread.ennds.item.IConstructModule;
import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemConstructModule extends Item implements IConstructModule
{

    public static final String[] names = new String[] { "archive", "knowledgeReceptacle", "naniteReplicator" };
    @SideOnly(Side.CLIENT)
    private IIcon[] itemIcons;

    public ItemConstructModule(String name)
    {
        setUnlocalizedName(name);
        setTextureName(name);
        setHasSubtypes(true);
        setMaxDamage(0);
        setCreativeTab(TechnoMagi.tabTM);
    }

    @Override
    public Block getModuleBlock(ItemStack stack)
    {
        int dmg = stack.getItemDamage();
        return dmg == 0 ? Blocks.blockArchive : (dmg == 1 ? Blocks.blockKnowledgeReceptacle : (dmg == 2 ? Blocks.blockNaniteReplicator : null));
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + names[stack.getItemDamage()];
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return itemIcons[i];
    }

    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < names.length; ++i) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcons = new IIcon[names.length];

        for (int i = 0; i < names.length; ++i) {
            itemIcons[i] = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getIconString() + "_" + names[i]);
        }
    }

}
