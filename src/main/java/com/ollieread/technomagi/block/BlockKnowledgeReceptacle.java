package com.ollieread.technomagi.block;

import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityKnowledgeReceptor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockKnowledgeReceptacle extends BlockContainer
{

    public BlockKnowledgeReceptacle(String name)
    {
        super(Material.iron);

        setBlockName(name);
        setBlockTextureName(name);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityKnowledgeReceptor();
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            TileEntityKnowledgeReceptor tile = (TileEntityKnowledgeReceptor) world.getTileEntity(x, y, z);

            tile.setPlayer(player.getCommandSenderName());
        }
    }

}
