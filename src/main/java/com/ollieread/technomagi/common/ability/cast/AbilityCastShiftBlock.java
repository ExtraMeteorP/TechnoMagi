package com.ollieread.technomagi.common.ability.cast;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.ability.AbilityCast;
import com.ollieread.technomagi.api.ability.AbilityHandler.AbilityType;
import com.ollieread.technomagi.api.ability.AbilityPayload;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityResult;
import com.ollieread.technomagi.api.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.tile.ITileDisguisable;
import com.ollieread.technomagi.common.block.structure.BlockShifted;
import com.ollieread.technomagi.common.block.structure.tile.TileShifted;
import com.ollieread.technomagi.common.init.Blocks;

public class AbilityCastShiftBlock extends AbilityCast
{

    public final static int maxDuration = 1;

    public AbilityCastShiftBlock(String name, ResourceLocation icon)
    {
        super(name, icon);
    }

    @Override
    public boolean isAvailable(PlayerTechnomagi technomage)
    {
        return true;
    }

    @Override
    public boolean canUse(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        return payload.target.equals(AbilityUseTarget.BLOCK);
    }

    @Override
    public void use(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
        World world = technomage.getPlayer().worldObj;

        Block block = world.getBlock(payload.blockX, payload.blockY, payload.blockZ);
        int metadata = world.getBlockMetadata(payload.blockX, payload.blockY, payload.blockZ);

        if (block instanceof BlockShifted) {
            TileShifted shifted = (TileShifted) world.getTileEntity(payload.blockX, payload.blockY, payload.blockZ);

            ItemStack disguise = shifted.getDisguise();

            if (disguise != null && Block.getBlockFromItem(disguise.getItem()) != null) {
                world.setBlock(payload.blockX, payload.blockY, payload.blockZ, Block.getBlockFromItem(disguise.getItem()), disguise.getItemDamage(), 2);
                payload.setResult(AbilityResult.COMPLETE);
                return;
            }
        } else if (block.isNormalCube() && !block.hasTileEntity(metadata)) {
            Item item = Item.getItemFromBlock(block);

            if (item != null) {
                ItemStack stack = new ItemStack(item, 1, metadata);
                world.setBlock(payload.blockX, payload.blockY, payload.blockZ, Blocks.shifted);

                ITileDisguisable tile = (ITileDisguisable) world.getTileEntity(payload.blockX, payload.blockY, payload.blockZ);
                if (tile.setDisguiseBlock(stack)) {
                    payload.setResult(AbilityResult.COMPLETE);
                    return;
                }
            }
        }

        payload.setResult(AbilityResult.HALT);
        return;
    }

    @Override
    public void stoppedUsing(PlayerTechnomagi technomage, AbilityPayload payload, int abilityMode)
    {
    }

    @Override
    public AbilityType getType(int abilityMode)
    {
        return AbilityType.BLOCK;
    }

    @Override
    public int getMaxFocus(int abilityMode)
    {
        return maxDuration;
    }

    @Override
    public int getCooldown(int abilityMode)
    {
        return 30;
    }

}
