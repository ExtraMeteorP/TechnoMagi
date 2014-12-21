package com.ollieread.technomagi.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityGenerator;
import com.ollieread.technomagi.tileentity.TileEntityGeneratorLife;
import com.ollieread.technomagi.tileentity.TileEntityGeneratorSolar;
import com.ollieread.technomagi.tileentity.TileEntityGeneratorVoid;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenerator extends BlockTMContainer
{
    @SideOnly(Side.CLIENT)
    protected IIcon voidIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon lightIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon lifeIcon;

    public BlockGenerator(String name)
    {
        super(Material.iron, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        switch (meta) {
            case 0:
                return new TileEntityGeneratorVoid();
            case 1:
                return new TileEntityGeneratorSolar();
            case 2:
                return new TileEntityGeneratorLife();
        }

        return null;
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

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        super.registerBlockIcons(register);

        voidIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":generatorVoid");
        lightIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":generatorLight");
        lifeIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":generatorLife");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        if (side != 0 && side != 1) {
            if (meta == 0) {
                return voidIcon;
            } else if (meta == 1) {
                return lightIcon;
            } else if (meta == 2) {
                return lifeIcon;
            }
        }

        return blockIcon;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        super.getSubBlocks(item, tab, list);

        list.add(new ItemStack(this, 1, 1));
        list.add(new ItemStack(this, 1, 2));
    }

    @Override
    public int damageDropped(int meta)
    {
        return meta;
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityGenerator entity = (TileEntityGenerator) world.getTileEntity(x, y, z);

            if (entity != null) {
                PlayerHelper.addChatMessage(player, "Energy: " + entity.getEnergyStored(null));
            }

            return true;
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        if (!world.isRemote) {
            if (world.getBlockMetadata(x, y, z) == 0) {
                TileEntityGeneratorVoid generator = (TileEntityGeneratorVoid) world.getTileEntity(x, y, z);

                if (generator != null) {
                    generator.setGenerationByLocation(y);
                }
            }
        }
    }

}
