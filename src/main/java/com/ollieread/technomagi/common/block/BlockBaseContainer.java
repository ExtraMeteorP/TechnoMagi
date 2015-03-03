package com.ollieread.technomagi.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.common.block.tile.ITilePlayerOwned;
import com.ollieread.technomagi.common.block.tile.ITileRetainsData;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;
import com.ollieread.technomagi.util.ItemNBTHelper;

public abstract class BlockBaseContainer extends BlockContainer
{

    protected String name;

    protected BlockBaseContainer(String name, Material material)
    {
        super(material);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
    {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile instanceof ITileRetainsData && ItemNBTHelper.has(stack, "RetainedData")) {
                ((ITileRetainsData) tile).setRetainedData(ItemNBTHelper.getCompound(stack, "RetainedData"));
            }

            if (tile instanceof ITilePlayerOwned && entityLiving instanceof EntityPlayer) {
                ((ITilePlayerOwned) tile).setPlayer((EntityPlayer) entityLiving);
            }
        }
    }

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

}
