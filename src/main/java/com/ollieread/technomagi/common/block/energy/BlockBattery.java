package com.ollieread.technomagi.common.block.energy;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockBaseContainer;
import com.ollieread.technomagi.common.block.energy.tile.TileBattery;
import com.ollieread.technomagi.common.item.block.ItemBlockBattery;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBattery extends BlockBaseContainer
{

    public BlockBattery(String name)
    {
        super(name, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        if (metadata == 0) {
            return new TileBattery(3200, 250, 250);
        } else if (metadata == 1) {
            return new TileBattery(6400, 500, 500);
        } else if (metadata == 2) {
            return new TileBattery(12800, 1024, 1024);
        }

        return null;
    }

    @Override
    public int getRenderType()
    {
        return -1;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        TileBattery battery = (TileBattery) world.getTileEntity(x, y, z);
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (battery != null) {
            Item item = getItemDropped(metadata, world.rand, fortune);
            ItemStack stack = new ItemStack(getItemDropped(metadata, world.rand, fortune), 1, damageDropped(metadata));
            ((ItemBlockBattery) item).configure(stack, battery.getMaxEnergyStored(null), battery.getMaxExtract(), battery.getMaxReceive());
            ((ItemBlockBattery) item).setEnergyStored(stack, battery.getEnergyStored(null));
            ret.add(stack);
        }

        return ret;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        if (!world.isRemote) {
            TileBattery battery = (TileBattery) world.getTileEntity(x, y, z);

            if (battery != null) {
                ItemBlockBattery item = (ItemBlockBattery) stack.getItem();

                if (item.getCapacity(stack) > 0) {
                    battery.setCapacity(item.getCapacity(stack));
                    battery.setMaxExtract(item.getMaxExtract(stack));
                    battery.setMaxReceive(item.getMaxReceive(stack));
                    battery.setEnergyStored(item.getEnergyStored(stack));
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        list.add(createStackedBlock(0));
        list.add(createStackedBlock(1));
        list.add(createStackedBlock(2));

        ItemStack fullBasic = createStackedBlock(0);
        ItemStack fullAdvanced = createStackedBlock(1);
        ItemStack fullElite = createStackedBlock(2);

        ((ItemBlockBattery) fullBasic.getItem()).setEnergyStored(fullBasic, 3200);
        ((ItemBlockBattery) fullAdvanced.getItem()).setEnergyStored(fullAdvanced, 6400);
        ((ItemBlockBattery) fullElite.getItem()).setEnergyStored(fullElite, 12800);
        list.add(fullBasic);
        list.add(fullAdvanced);
        list.add(fullElite);

    }

    @Override
    protected ItemStack createStackedBlock(int metadata)
    {
        Item item = Item.getItemFromBlock(this);
        ItemStack stack = new ItemStack(item, 1, metadata);

        if (metadata == 0) {
            ((ItemBlockBattery) item).configure(stack, 3200, 250, 250);
        } else if (metadata == 1) {
            ((ItemBlockBattery) item).configure(stack, 6400, 500, 500);
        } else {
            ((ItemBlockBattery) item).configure(stack, 12800, 1024, 1024);
        }

        return stack;
    }

}
