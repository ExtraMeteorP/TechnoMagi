package com.ollieread.technomagi.ability.active;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.ability.AbilityPayload;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseTarget;
import com.ollieread.ennds.ability.AbilityPayload.AbilityUseType;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.common.init.Config;
import com.ollieread.technomagi.tileentity.TileEntityReactiveCrafting;

public class ActiveAbilityReactive extends AbilityActive
{

    protected int cost;

    public ActiveAbilityReactive(String name)
    {
        super(name, Reference.MODID.toLowerCase());

        cost = Config.reactiveCost;
    }

    @Override
    public int getMaxFocus()
    {
        return 0;
    }

    @Override
    public boolean canIntervalFocus()
    {
        return false;
    }

    @Override
    public boolean canUse(ExtendedPlayerKnowledge charon, AbilityPayload cast)
    {
        return charon.nanites.getMaxNanites() >= cost && cast.type.equals(AbilityUseType.FLASH) && cast.target.equals(AbilityUseTarget.BLOCK);
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, AbilityPayload cast, ItemStack staff)
    {
        int x = cast.blockX;
        int y = cast.blockY;
        int z = cast.blockZ;
        int meta = charon.player.worldObj.getBlockMetadata(x, y, z);
        Block block = charon.player.worldObj.getBlock(x, y, z);

        if (block != null) {
            ItemStack target = new ItemStack(block, 1, meta);
            System.out.println(target);

            if (target != null) {
                ItemStack recipe = FurnaceRecipes.smelting().getSmeltingResult(target);
                System.out.println(recipe);

                if (recipe != null) {
                    if (recipe.getItem() != null) {
                        Block result = Block.getBlockFromItem(recipe.getItem());

                        if (result != null) {
                            if (charon.nanites.decreaseNanites(cost)) {
                                if (!charon.player.worldObj.isRemote) {
                                    charon.player.worldObj.setBlock(x, y, z, Blocks.blockReactiveCrafting);
                                    TileEntityReactiveCrafting reactive = (TileEntityReactiveCrafting) charon.player.worldObj.getTileEntity(x, y, z);

                                    if (reactive != null) {
                                        reactive.setResult(recipe, 100, new ItemStack(block, 1, meta), charon.player.isSneaking());
                                    }
                                }

                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

}
