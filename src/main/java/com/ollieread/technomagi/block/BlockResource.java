package com.ollieread.technomagi.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockResource extends BlockTM
{

    private Random rand = new Random();
    protected String[] blockNames = new String[] { "etheriumOre", "voidstone" };

    @SideOnly(Side.CLIENT)
    protected IIcon[] blockIcons;

    public BlockResource(String name)
    {
        super(Material.rock, name);
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcons = new IIcon[blockNames.length];

        for (int i = 0; i < blockNames.length; i++) {
            blockIcons[i] = register.registerIcon(Reference.MODID.toLowerCase() + ":" + blockNames[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return blockIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < blockNames.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    public Item getItemDropped(int meta, Random rand, int fortune)
    {
        return meta == 0 ? Items.itemResource : Item.getItemFromBlock(this);
    }

    public int quantityDropped(int meta, int fortune, Random rand)
    {
        if (meta == 0) {
            int d = rand.nextInt(3);
            return d > 0 ? d : 1;
        }

        return 1;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int var1, int var2)
    {
        if (this.getItemDropped(var1, rand, var2) != Item.getItemFromBlock(this)) {
            return MathHelper.getRandomIntegerInRange(rand, 3, 7);
        }

        return 0;
    }

    public int damageDropped(int meta)
    {
        return meta;
    }

}
