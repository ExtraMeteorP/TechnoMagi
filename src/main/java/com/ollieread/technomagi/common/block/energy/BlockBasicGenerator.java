package com.ollieread.technomagi.common.block.energy;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.energy.tile.TileBasicGenerator;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBasicGenerator extends BlockBaseContainer
{

    @SideOnly(Side.CLIENT)
    protected IIcon sideIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon onIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon offIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon backIcon;

    public BlockBasicGenerator(String name)
    {
        super(name, Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileBasicGenerator();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        sideIcon = register.registerIcon(ResourceHelper.icon("machine/basic/generic_side"));
        onIcon = register.registerIcon(ResourceHelper.icon("machine/basic/generator_on"));
        offIcon = register.registerIcon(ResourceHelper.icon("machine/basic/generator_off"));
        backIcon = register.registerIcon(ResourceHelper.icon("machine/basic/generator_back"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (side == 0 || side == 1) {
            return sideIcon;
        } else if (side == 4) {
            return offIcon;
        } else if (side == 5) {
            return backIcon;
        }

        return sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileBasicGenerator generator = (TileBasicGenerator) world.getTileEntity(x, y, z);

        if (generator.getDirection() != null) {
            if (side == generator.getDirection().ordinal()) {
                return generator.isProcessing() ? onIcon : offIcon;
            } else if (side == generator.getDirection().getOpposite().ordinal()) {
                return backIcon;
            }
        }

        return sideIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileBasicGenerator generator = (TileBasicGenerator) world.getTileEntity(x, y, z);

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
