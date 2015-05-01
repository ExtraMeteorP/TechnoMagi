package com.ollieread.technomagi.common.block.machine;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.client.renderers.blocks.BlockFauxPocketRenderer;
import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.machine.tile.TileFauxPocket;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.util.BlockHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFauxPocket extends BlockBaseContainer
{

    public BlockFauxPocket(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileFauxPocket();
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileFauxPocket.class, "faux_pocket");
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return BlockFauxPocketRenderer.id;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.getBlockMetadata(x, y, z) == 0) {
            ItemStack stack = player.getHeldItem();

            if (stack != null && stack.getItem() != null) {
                if (stack.getItem() == Items.crystalCharged) {
                    if (!world.isRemote) {
                        world.setBlockMetadataWithNotify(x, y, z, stack.getItemDamage() + 1, 2);
                        stack.stackSize--;
                    }

                    return true;
                }
            }
        }

        return false;
    }

}
