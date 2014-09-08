package com.ollieread.technomagi.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
import com.ollieread.technomagi.util.PlayerHelper;
import com.ollieread.technomagi.util.TeleportHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTeleporter extends BlockOwnable implements IDigitalToolable
{

    @SideOnly(Side.CLIENT)
    public IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    public IIcon pairIcon;
    @SideOnly(Side.CLIENT)
    public IIcon redirectIcon;
    @SideOnly(Side.CLIENT)
    public IIcon interuptIcon;

    public BlockTeleporter(String name)
    {
        super(Material.iron, name);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityTeleporter();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
        sideIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":teleporterSide");
        pairIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":teleporterPair");
        redirectIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":teleporterRedirect");
        interuptIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":teleporterInterupt");
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        super.getSubBlocks(item, tab, list);

        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
        list.add(new ItemStack(this, 1, 3));
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side == 1 || side == 0) {
            if (meta == 0) {
                return blockIcon;
            } else if (meta == 1) {
                return pairIcon;
            } else if (meta == 2) {
                return redirectIcon;
            } else if (meta == 3) {
                return interuptIcon;
            }
        }

        return sideIcon;
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    @Override
    public void onEntityWalking(World world, int x, int y, int z, Entity entity)
    {
        if (!world.isRemote && world.getBlockMetadata(x, y, z) == 1) {
            TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(x, y, z);

            if (!teleporter.canPartner()) {
                TileEntityTeleporter partner = teleporter.getPartner();

                if (entity instanceof EntityPlayer) {
                    TeleportHelper.teleportPlayerToTeleporter((EntityPlayer) entity, teleporter, partner);
                } else if (entity instanceof EntityLiving) {
                    TeleportHelper.teleportEntityToTeleporter((EntityLiving) entity, teleporter, partner);
                }
            }
        }
    }

    @Override
    public boolean onTooled(EntityPlayer player, World world, int x, int y, int z, ItemStack tool)
    {
        if (!world.isRemote) {
            if (world.getBlockMetadata(x, y, z) == 1) {
                TileEntityTeleporter thisTeleporter = (TileEntityTeleporter) world.getTileEntity(x, y, z);

                if (ItemDigitalTool.hasFocusLocation(tool)) {
                    int[] location = ItemDigitalTool.getFocusLocation(tool);
                    Block block = world.getBlock(location[0], location[1], location[2]);

                    if (block != null && block instanceof BlockTeleporter && world.getBlockMetadata(location[0], location[1], location[2]) == 1) {
                        TileEntityTeleporter otherTeleporter = (TileEntityTeleporter) world.getTileEntity(location[0], location[1], location[2]);

                        if (thisTeleporter.canPartner() && otherTeleporter.canPartner()) {
                            thisTeleporter.partner(location[0], location[1], location[2]);
                            otherTeleporter.partner(x, y, z);
                            ItemDigitalTool.resetFocusLocation(tool);
                            PlayerHelper.addChatMessage(player, "Teleporter Paired: " + x + " " + y + " " + z);
                        }

                        return true;
                    }
                } else if (thisTeleporter.canPartner()) {
                    ItemDigitalTool.setFocusLocation(tool, x, y, z);
                    PlayerHelper.addChatMessage(player, "Teleporter Focused: " + x + " " + y + " " + z);

                    return true;
                }
            }
        }

        return false;
    }

}
