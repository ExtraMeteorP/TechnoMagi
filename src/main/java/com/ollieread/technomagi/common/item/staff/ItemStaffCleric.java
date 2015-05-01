package com.ollieread.technomagi.common.item.staff;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Specialisations;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ItemStaffCleric extends ItemStaffAbility
{

    public ItemStaffCleric(String name)
    {
        super(name);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (PlayerHelper.isSpecialisation(player, Specialisations.cleric)) {
            if (!player.canPlayerEdit(x, y, z, side, stack)) {
                return false;
            } else {
                UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
                if (MinecraftForge.EVENT_BUS.post(event)) {
                    return false;
                }

                if (event.getResult() == Result.ALLOW) {
                    return true;
                }

                Block block = world.getBlock(x, y, z);
                boolean flag = ItemStackHelper.matchesBlock(ItemStackHelper.block("grass"), block) || ItemStackHelper.matchesBlock(ItemStackHelper.block("dirt"), block);

                if (side != 0 && world.getBlock(x, y + 1, z).isAir(world, x, y + 1, z) && flag) {
                    Block block1 = Blocks.naniteFarmland;
                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block1.stepSound.getStepResourcePath(), (block1.stepSound.getVolume() + 1.0F) / 2.0F, block1.stepSound.getPitch() * 0.8F);

                    if (world.isRemote) {
                        return true;
                    } else {
                        world.setBlock(x, y, z, block1);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack)
    {
        return EnumRarity.epic;
    }

}
