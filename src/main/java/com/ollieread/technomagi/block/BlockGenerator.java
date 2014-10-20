package com.ollieread.technomagi.block;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.TileEntityGeneratorBasic;
import com.ollieread.technomagi.util.EntityHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGenerator extends BlockTMContainer
{

    public BlockGenerator(String name)
    {
        super(Material.iron, name);

        setCreativeTab(null);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        if (meta == 0) {
            return new TileEntityGeneratorBasic();
        }

        return null;
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        super.getSubBlocks(item, tab, list);

        // list.add(new ItemStack(this, 1, 1));
        // list.add(new ItemStack(this, 1, 2));
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
            TileEntityGeneratorBasic entity = (TileEntityGeneratorBasic) world.getTileEntity(x, y, z);

            if (entity != null) {
                EntityHelper.addChatMessage(player, "Energy: " + entity.getEnergyStored(null));
            }

            return true;
        }
    }

    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        if (!world.isRemote && world.getBlockMetadata(x, y, z) == 0) {
            TileEntityGeneratorBasic generator = (TileEntityGeneratorBasic) world.getTileEntity(x, y, z);

            if (generator != null) {
                generator.setGenerationByLocation(y);
            }
        }
    }

}
