package com.ollieread.technomagi.ability.active;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

import com.ollieread.ennds.ability.AbilityActive;
import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.item.crafting.ReactiveManager;
import com.ollieread.technomagi.tileentity.TileEntityReactiveCrafting;

import cpw.mods.fml.common.eventhandler.Event;

public class ActiveAbilityExothermic extends AbilityActive
{

    public ActiveAbilityExothermic(String name)
    {
        super(name, Reference.MODID.toLowerCase());
    }

    @Override
    public boolean isAvailable(ExtendedPlayerKnowledge charon)
    {
        return charon.hasKnowledge("exothermic");
    }

    @Override
    public boolean use(ExtendedPlayerKnowledge charon, Event event)
    {
        if (event instanceof PlayerInteractEvent) {
            PlayerInteractEvent interact = (PlayerInteractEvent) event;
            EntityPlayer player = interact.entityPlayer;

            if (interact.action.equals(Action.RIGHT_CLICK_BLOCK)) {
                int x = interact.x;
                int y = interact.y;
                int z = interact.z;
                int meta = player.worldObj.getBlockMetadata(x, y, z);
                Block block = player.worldObj.getBlock(x, y, z);
                Item item = Item.getItemFromBlock(block);

                if (item != null) {
                    ItemStack stack = new ItemStack(block, 1, meta);

                    if (stack != null) {
                        List recipe = ReactiveManager.getInstance().findMatchingRecipe(stack, 0);

                        if (recipe != null && recipe.size() == 2) {
                            ItemStack resultStack = ((ItemStack) recipe.get(0)).copy();

                            if (resultStack.getItem() != null) {
                                Block result = Block.getBlockFromItem(resultStack.getItem());

                                if (result != null) {
                                    if (charon.nanites.decreaseNanites(10)) {
                                        if (!interact.world.isRemote) {
                                            interact.world.setBlock(x, y, z, Blocks.blockReactiveCrafting);
                                            TileEntityReactiveCrafting reactive = (TileEntityReactiveCrafting) player.worldObj.getTileEntity(x, y, z);

                                            if (reactive != null) {
                                                reactive.setResult(resultStack, (Integer) recipe.get(1), 0, new ItemStack(block, 1, meta));
                                            }
                                        }

                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public String[] getEnhancements()
    {
        return new String[] {};
    }

    @Override
    public String[] getKnowledge()
    {
        return new String[] { "exothermic" };
    }

}
