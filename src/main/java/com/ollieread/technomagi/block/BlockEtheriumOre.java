package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

import com.ollieread.technomagi.common.init.Items;

public class BlockEtheriumOre extends BlockTM
{

    private Random rand = new Random();

    public BlockEtheriumOre(String name)
    {
        super(Material.rock, name);
    }

    public Item getItemDropped(int var1, Random rand, int var2)
    {
        return Items.itemEtherium;
    }

    public int quantityDropped(Random rand)
    {
        int d = rand.nextInt(3);
        return d > 0 ? d : 1;
    }

    @Override
    public int getExpDrop(IBlockAccess world, int var1, int var2)
    {
        if (this.getItemDropped(var1, rand, var2) != Item.getItemFromBlock(this)) {
            return MathHelper.getRandomIntegerInRange(rand, 3, 7);
        }

        return 0;
    }

}
