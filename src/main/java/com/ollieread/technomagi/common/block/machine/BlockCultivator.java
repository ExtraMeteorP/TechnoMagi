package com.ollieread.technomagi.common.block.machine;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.EnumRarity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivator;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivatorBasic;
import com.ollieread.technomagi.common.block.machine.tile.TileCultivatorElectric;
import com.ollieread.technomagi.util.BlockHelper;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCultivator extends BlockContainerSubtypes
{

    @SideOnly(Side.CLIENT)
    protected IIcon basicIcons[];
    @SideOnly(Side.CLIENT)
    protected IIcon electricIcons[];
    @SideOnly(Side.CLIENT)
    protected IIcon naniteIcons[];

    public BlockCultivator(String name)
    {
        super(name, new String[] { "basic", "electric" }, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        switch (metadata) {
            default:
            case 0:
                return new TileCultivatorBasic();
            case 1:
                return new TileCultivatorElectric();
        }
    }

    @Override
    public EnumRarity getItemRarity(int metadata)
    {
        return metadata == 0 ? EnumRarity.common : (metadata == 1 ? EnumRarity.uncommon : EnumRarity.rare);
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileCultivatorBasic.class, "cultivator_basic");
        BlockHelper.registerTileEntity(TileCultivatorElectric.class, "cultivator_electric");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.basicIcons = new IIcon[2];
        this.basicIcons[0] = register.registerIcon(ResourceHelper.icon("machine/basic/cultivator_front"));
        this.basicIcons[1] = register.registerIcon(ResourceHelper.icon("machine/basic/generic_side"));

        this.electricIcons = new IIcon[3];
        this.electricIcons[0] = register.registerIcon(ResourceHelper.icon("machine/electric/cultivator_front"));
        this.electricIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_side"));
        this.electricIcons[2] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_back"));

        this.naniteIcons = new IIcon[3];
        this.naniteIcons[0] = register.registerIcon(ResourceHelper.icon("machine/nanite/cultivator_front"));
        this.naniteIcons[1] = register.registerIcon(ResourceHelper.icon("machine/nanite/generic_side"));
        this.naniteIcons[2] = register.registerIcon(ResourceHelper.icon("machine/nanite/generic_back"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata == 0) {
            if (side == 4) {
                return this.basicIcons[0];
            } else if (side == 5) {
                return this.basicIcons[1];
            }

            return this.basicIcons[1];
        } else if (metadata == 1) {
            if (side == 4) {
                return this.electricIcons[0];
            } else if (side == 5) {
                return this.electricIcons[2];
            }

            return this.electricIcons[1];
        } else {
            if (side == 4) {
                return this.naniteIcons[0];
            } else if (side == 5) {
                return this.naniteIcons[2];
            }

            return this.naniteIcons[1];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);

        if (metadata == 0) {
            if (side == 0 || side == 1) {
                return this.basicIcons[1];
            } else {
                TileCultivator tile = (TileCultivator) world.getTileEntity(x, y, z);

                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        return this.basicIcons[0];
                    }
                }
            }

            return this.basicIcons[1];
        } else if (metadata == 1) {
            if (side == 0 || side == 1) {
                return this.electricIcons[1];
            } else {
                TileCultivator tile = (TileCultivator) world.getTileEntity(x, y, z);

                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        return this.electricIcons[0];
                    } else if (tile.getDirection().getOpposite().ordinal() == side) {
                        return this.electricIcons[2];
                    }
                }
            }

            return this.electricIcons[1];
        } else {
            if (side == 0 || side == 1) {
                return this.naniteIcons[1];
            } else {
                TileCultivator tile = (TileCultivator) world.getTileEntity(x, y, z);

                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        return this.naniteIcons[0];
                    } else if (tile.getDirection().getOpposite().ordinal() == side) {
                        return this.naniteIcons[2];
                    }
                }
            }

            return this.naniteIcons[1];
        }
    }

}
