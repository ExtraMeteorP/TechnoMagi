package com.ollieread.technomagi.common.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.tile.ISideFacing;
import com.ollieread.technomagi.common.block.tile.ITileLink;
import com.ollieread.technomagi.util.ItemNBTHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDigitalTool extends ItemBase
{

    public ItemDigitalTool(String name)
    {
        super(name);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        if (!player.worldObj.isRemote) {
            TileEntity tile = player.worldObj.getTileEntity(x, y, z);

            if (tile instanceof ITileLink) {
                ITileLink link = (ITileLink) tile;

                if (player.isSneaking()) {
                    link.removeLink();
                } else {
                    if (!link.isLinked()) {
                        if (hasFocus(stack)) {
                            int[] focus = getFocus(stack);

                            if (link.canLink(focus[0], focus[1], focus[2])) {
                                link.setLink(focus[0], focus[1], focus[2]);

                                TileEntity focusTile = player.worldObj.getTileEntity(focus[0], focus[1], focus[2]);

                                if (focusTile instanceof ITileLink) {
                                    ITileLink focusLink = (ITileLink) focusTile;

                                    if (!focusLink.isLinked()) {
                                        focusLink.setLink(x, y, z);
                                        PlayerHelper.addChatMessage(player, "Linked: " + x + ":" + y + ":" + z);
                                        return true;
                                    }
                                }
                            }
                        } else {
                            setFocus(stack, x, y, z);
                            PlayerHelper.addChatMessage(player, "Focused: " + x + ":" + y + ":" + z);
                            return true;
                        }
                    }
                }
            } else if (tile instanceof ISideFacing) {
                ISideFacing facing = (ISideFacing) tile;

                if (player.isSneaking()) {

                } else {
                    facing.rotate();
                    return true;
                }
            } else {
                resetFocus(stack);
                PlayerHelper.addChatMessage(player, "Focus Reset");
            }
        }

        return false;
    }

    public void setFocus(ItemStack stack, int x, int y, int z)
    {
        ItemNBTHelper.setIntArray(stack, "Focus", new int[] { x, y, z });
    }

    public int[] getFocus(ItemStack stack)
    {
        return ItemNBTHelper.getIntArray(stack, "Focus");
    }

    public void resetFocus(ItemStack stack)
    {
        ItemNBTHelper.removeKey(stack, "Focus");
    }

    public boolean hasFocus(ItemStack stack)
    {
        return getFocus(stack).length == 3;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }

}
