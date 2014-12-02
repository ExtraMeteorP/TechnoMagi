package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityFocuser;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFocuser extends BlockOwnable
{

    public BlockFocuser(String name)
    {
        super(Material.iron, name);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        setBlockTextureName("construct");
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public TileEntity createNewTileEntity(World world, int var2)
    {
        return new TileEntityFocuser();
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        if (!world.isAirBlock(x, y + 1, z)) {
            return false;
        }

        return true;
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityFocuser entity = (TileEntityFocuser) world.getTileEntity(x, y, z);

            if (entity != null) {
                if (entity.isPlayer(player)) {
                    player.openGui(TechnoMagi.instance, CommonProxy.GUI_FOCUSER, world, x, y, z);
                }
            }
        }

        return true;
    }

    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof TileEntityFocuser) {
            ((TileEntityFocuser) tile).checkForMultiplier();
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        super.randomDisplayTick(world, x, y, z, rand);

        TileEntityFocuser focuser = (TileEntityFocuser) world.getTileEntity(x, y, z);

        if (focuser.getProgress() > 0) {
            for (int l = x - 2; l <= x + 2; ++l) {
                for (int i1 = z - 2; i1 <= z + 2; ++i1) {
                    if (l > x - 2 && l < x + 2 && i1 == z - 1) {
                        i1 = z + 2;
                    }

                    for (int j1 = y; j1 <= y + 1; ++j1) {
                        if (world.getBlock(l, j1, i1) == Blocks.blockFocusCharger) {
                            if (!world.isAirBlock((l - x) / 2 + x, j1, (i1 - z) / 2 + z)) {
                                break;
                            }

                            EntityFX effect = new EntityPortalFX(world, (double) x + 0.5D, (double) y + 1.0D, (double) z + 0.5D, (double) ((float) (l - x) + rand.nextFloat()) - 0.5D, (double) ((float) (j1 - y) - rand.nextFloat() - 0.5F), (double) ((float) (i1 - z) + rand.nextFloat()) - 0.5D);
                            effect.setRBGColorF(228 / 255.0F, 69 / 255.0F, 152 / 255.0F);
                            Minecraft.getMinecraft().effectRenderer.addEffect(effect);
                        }
                    }
                }
            }
        }
    }

}
