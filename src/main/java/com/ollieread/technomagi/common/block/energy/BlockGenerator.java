package com.ollieread.technomagi.common.block.energy;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.energy.tile.TileGeneratorBasic;
import com.ollieread.technomagi.common.block.energy.tile.TileGeneratorEnhanced;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenerator extends BlockContainerSubtypes
{

    @SideOnly(Side.CLIENT)
    protected IIcon[] sideIcons;
    @SideOnly(Side.CLIENT)
    protected IIcon[] onIcons;
    @SideOnly(Side.CLIENT)
    protected IIcon[] offIcons;
    @SideOnly(Side.CLIENT)
    protected IIcon[] backIcons;

    public BlockGenerator(String name)
    {
        super(name, new String[] { "basic", "enhanced" }, Material.rock);

        this.setLightLevel(10F).setHardness(3.5F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        switch (metadata) {
            case 0:
                return new TileGeneratorBasic();
            case 1:
                return new TileGeneratorEnhanced();
            default:
                return null;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        sideIcons = new IIcon[this.names.length];
        sideIcons[0] = register.registerIcon(ResourceHelper.icon("machine/basic/generic_side"));
        sideIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_side"));
        onIcons = new IIcon[this.names.length];
        onIcons[0] = register.registerIcon(ResourceHelper.icon("machine/basic/generator_on"));
        onIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/generator_on"));
        offIcons = new IIcon[this.names.length];
        offIcons[0] = register.registerIcon(ResourceHelper.icon("machine/basic/generator_off"));
        offIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/generator_off"));
        backIcons = new IIcon[this.names.length];
        backIcons[0] = register.registerIcon(ResourceHelper.icon("machine/basic/generator_back"));
        backIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_back"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 0 || side == 1) {
            return sideIcons[metadata];
        } else if (side == 4) {
            return offIcons[metadata];
        } else if (side == 5) {
            return backIcons[metadata];
        }

        return sideIcons[metadata];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileGeneratorBasic generator = (TileGeneratorBasic) world.getTileEntity(x, y, z);

        if (generator.getDirection() != null) {
            if (side == generator.getDirection().ordinal()) {
                return generator.isProcessing() ? onIcons[generator.getBlockMetadata()] : offIcons[generator.getBlockMetadata()];
            } else if (side == generator.getDirection().getOpposite().ordinal()) {
                return backIcons[generator.getBlockMetadata()];
            }
        }

        return sideIcons[generator.getBlockMetadata()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileGeneratorBasic generator = (TileGeneratorBasic) world.getTileEntity(x, y, z);

        if (generator.isProcessing()) {
            int l = generator.getDirection().ordinal();
            float f = x + 0.5F;
            float f1 = y + 0.35F + rand.nextFloat() * 6.0F / 16.0F;
            float f2 = z + 0.5F;
            float f3 = 0.52F;
            float f4 = rand.nextFloat() * 0.6F - 0.3F;

            if (l == 4) {
                world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            } else if (l == 5) {
                world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
            } else if (l == 2) {
                world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
            } else if (l == 3) {
                world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
