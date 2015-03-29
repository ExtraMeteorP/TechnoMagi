package com.ollieread.technomagi.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockInert extends ItemBlockBase
{

    public ItemBlockInert(Block block)
    {
        super(block);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (world.isAirBlock(x, y, z)) {
            int i1 = this.getMetadata(stack.getItemDamage());
            int j1 = this.field_150939_a.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, i1);

            if (placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, j1)) {
                world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.field_150939_a.stepSound.func_150496_b(), (this.field_150939_a.stepSound.getVolume() + 1.0F) / 2.0F, this.field_150939_a.stepSound.getPitch() * 0.8F);
                --stack.stackSize;
            }

            return true;
        } else {
            return false;
        }
    }

}
