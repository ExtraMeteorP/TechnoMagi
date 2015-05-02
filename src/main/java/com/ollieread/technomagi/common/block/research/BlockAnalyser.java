package com.ollieread.technomagi.common.block.research;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.api.entity.PlayerTechnomagi;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.client.renderers.blocks.BlockAnalyserRenderer;
import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.research.tile.TileAnalyser;
import com.ollieread.technomagi.common.init.Items;
import com.ollieread.technomagi.util.BlockHelper;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAnalyser extends BlockBaseContainer
{

    public BlockAnalyser(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileAnalyser();
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileAnalyser.class, "analyser");
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return true;
    }

    @Override
    public int getRenderType()
    {
        return BlockAnalyserRenderer.id;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileAnalyser analyser = (TileAnalyser) world.getTileEntity(x, y, z);

        if (analyser != null && analyser.isPlayer(player)) {
            PlayerKnowledge knowledge = PlayerHelper.getKnowledge(player);

            if (player.getHeldItem().isItemEqual(new ItemStack(Items.personalInterface))) {
                if (!world.isRemote) {
                    PlayerTechnomagi technomage = PlayerHelper.getTechnomagi(player);
                    technomage.knowledge().copyFrom(analyser);
                    analyser.resetResearch();
                }
            }

            analyser.copyFrom(knowledge);
        }

        return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
    }
}
