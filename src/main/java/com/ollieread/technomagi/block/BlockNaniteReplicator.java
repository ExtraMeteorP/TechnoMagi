package com.ollieread.technomagi.block;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityNaniteReplicator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNaniteReplicator extends BlockOwnable
{

    public BlockNaniteReplicator(String name)
    {
        super(Material.iron, name);
        setBlockTextureName("construct");
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcon = register.registerIcon(Reference.MODID.toLowerCase() + ":" + getTextureName());
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2)
    {
        return new TileEntityNaniteReplicator();
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

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityNaniteReplicator entity = (TileEntityNaniteReplicator) world.getTileEntity(x, y, z);

            if (entity != null) {
                if (entity.isPlayer(player)) {
                    player.openGui(TechnoMagi.instance, CommonProxy.GUI_REPLICATOR, world, x, y, z);
                } else {
                    System.out.println(entity.getPlayer());
                }
            }

            return true;
        }
    }

}
