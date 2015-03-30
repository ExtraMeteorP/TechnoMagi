package com.ollieread.technomagi.common.item.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;

public class ItemBlockInert extends ItemBlockBase
{

    public ItemBlockInert(Block block)
    {
        super(block);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition moving = getMovingObjectPositionFromPlayer(world, player, true);

        if (moving != null) {
            Technomagi.debug("Pos: " + moving.blockX + ":" + moving.blockY + ":" + moving.blockZ);
        }

        return stack;
    }

}
