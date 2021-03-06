package com.ollieread.technomagi.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.ollieread.technomagi.block.abstracts.BlockBasicContainer;
import com.ollieread.technomagi.common.Reference;
import com.ollieread.technomagi.tileentity.TileEntityRegionControllerDamage;
import com.ollieread.technomagi.tileentity.TileEntityRegionControllerEffect;
import com.ollieread.technomagi.tileentity.TileEntityRegionControllerInteraction;
import com.ollieread.technomagi.tileentity.TileEntityRegionControllerPerception;
import com.ollieread.technomagi.tileentity.TileEntityRegionControllerPresence;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRegionController extends BlockBasicContainer implements IBlockMulti
{

    private Random rand = new Random();
    public static String[] blockNames = new String[] { "perception", "interaction", "presence", "damage", "effect" };

    @SideOnly(Side.CLIENT)
    protected IIcon[] blockIcons;

    public BlockRegionController(String name)
    {
        super(Material.iron, name);
        setBlockTextureName("construct");
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcons = new IIcon[blockNames.length];

        for (int i = 0; i < blockNames.length; i++) {
            blockIcons[i] = register.registerIcon(Reference.MODID.toLowerCase() + ":" + blockNames[i]);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        return blockIcons[meta];
    }

    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < blockNames.length; i++) {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        switch (meta) {
            case 0:
                return new TileEntityRegionControllerPerception();
            case 1:
                return new TileEntityRegionControllerInteraction();
            case 2:
                return new TileEntityRegionControllerPresence();
            case 3:
                return new TileEntityRegionControllerDamage();
            case 4:
                return new TileEntityRegionControllerEffect();
        }

        return null;
    }

    @Override
    public String getName(int metadata)
    {
        return blockNames[metadata];
    }

}
