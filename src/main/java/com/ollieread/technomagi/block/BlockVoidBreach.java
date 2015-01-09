package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.potion.PotionTM;
import com.ollieread.technomagi.tileentity.TileEntityVoidBreach;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockVoidBreach extends BlockTMContainer
{

    @SideOnly(Side.CLIENT)
    protected IIcon[] blockIcons = new IIcon[10];

    public BlockVoidBreach(String name)
    {
        super(Material.glass, name);

        setBlockUnbreakable();
        setBlockBounds(0F, 0F, 0F, 1F, 1F, 1F);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        for (int i = 0; i < (blockIcons.length - 1); i++) {
            blockIcons[i] = register.registerIcon("destroy_stage_" + i);
        }

        blockIcons[9] = register.registerIcon(Reference.MODID.toLowerCase() + ":voidBreach");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        TileEntityVoidBreach tile = (TileEntityVoidBreach) world.getTileEntity(x, y, z);

        return blockIcons[tile.getStage()];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return blockIcons[meta];
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityVoidBreach();
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        return false;
    }

    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean canSilkHarvest()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int quantityDropped(Random random)
    {
        return 0;
    }

    public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
    {
        return true;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }

    public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass()
    {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return true;
    }

    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
    {
        if (entity instanceof EntityLivingBase && !((EntityLivingBase) entity).isPotionActive(PotionTM.voidSickness)) {
            ((EntityLivingBase) entity).addPotionEffect(new PotionEffect(PotionTM.voidSickness.id, 400, 0, true));
        }
    }

}
