package com.ollieread.technomagi.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.TileEntityAreaLight;
import com.ollieread.technomagi.tileentity.TileEntityLightAir;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAreaLight extends BlockContainer
{

    public BlockAreaLight(String name)
    {
        super(Material.iron);

        setBlockName(name);
        setBlockTextureName(name);
        setCreativeTab(TechnoMagi.tabTM);
        setLightLevel(1.0F);
        setLightOpacity(0);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityAreaLight();
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return !world.canBlockSeeTheSky(x, y, z);
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (!world.isRemote) {
            if (world.isAirBlock(x, y + 1, z)) {
                world.setBlock(x, y + 1, z, Blocks.blockLightAir);
                world.notifyBlockChange(x, y + 1, z, Blocks.blockLightAir);
                TileEntityLightAir light = (TileEntityLightAir) world.getTileEntity(x, y + 1, z);

                if (light != null) {
                    light.setMaster(x, y, z);
                    light.spreadBlocks();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

}
