package com.ollieread.technomagi.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.item.ItemDigitalTool;
import com.ollieread.technomagi.tileentity.TileEntityTeleporter;
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int facing, float playerX, float playerY, float playerZ)
    {
        if (!world.isRemote && world.getBlockMetadata(x, y, z) == 1) {
            ItemStack stack = player.getHeldItem();

            if (stack != null) {
                ItemDigitalTool tool = (ItemDigitalTool) stack.getItem();

                if (tool != null) {
                    TileEntityTeleporter teleporter = (TileEntityTeleporter) world.getTileEntity(x, y, z);

                    if (teleporter != null) {
                        if (tool.getFocusType() == 0) {
                            tool.setFocusType(1);
                            tool.setFocusLocation(x, y, z);

                            player.addChatComponentMessage(new ChatComponentText("Paired teleporter focused"));

                            return true;
                        } else if (tool.getFocusType() == 1) {
                            int[] location = tool.getFocusLocation();

                            TileEntityTeleporter teleporter2 = (TileEntityTeleporter) world.getTileEntity(location[0], location[1], location[2]);

                            if (teleporter2 != null) {
                                ((TileEntityTeleporter) teleporter).partner(location[0], location[1], location[2]);
                                teleporter2.partner(teleporter.xCoord, teleporter.yCoord, teleporter.zCoord);

                                tool.setFocusType(0);
                                tool.setFocusId(0);
                                tool.setFocusLocation(0, 0, 0);

                                player.addChatComponentMessage(new ChatComponentText("Paired teleporters linked"));

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
