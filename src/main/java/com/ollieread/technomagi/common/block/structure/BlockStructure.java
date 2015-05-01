package com.ollieread.technomagi.common.block.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.structure.tile.TileStructure;
import com.ollieread.technomagi.common.block.structure.tile.TileStructureBridge;
import com.ollieread.technomagi.common.block.structure.tile.TileStructurePlatform;
import com.ollieread.technomagi.util.BlockHelper;
import com.ollieread.technomagi.util.ResourceHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStructure extends BlockContainerSubtypes
{

    @SideOnly(Side.CLIENT)
    public IIcon[] blockIcons = new IIcon[3];

    public BlockStructure(String name)
    {
        super(name, new String[] { "platform", "bridge" }, Material.iron);

        this.setHardness(3.5F);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        switch (metadata) {
            case 0:
                return new TileStructurePlatform();
            case 1:
                return new TileStructureBridge();
            default:
                return null;
        }
    }

    @Override
    public void registerTiles()
    {
        BlockHelper.registerTileEntity(TileStructurePlatform.class, "platform");
        BlockHelper.registerTileEntity(TileStructureBridge.class, "bridge");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        blockIcons[0] = register.registerIcon(ResourceHelper.icon("machine/electric/generic_side"));
        blockIcons[1] = register.registerIcon(ResourceHelper.icon("machine/electric/light_generator"));
        blockIcons[2] = register.registerIcon(ResourceHelper.icon("machine/electric/light_generator_off"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata == 1) {
            if (side == 4) {
                return this.blockIcons[1];
            }
        } else {
            if (side != 0 && side != 1) {
                return this.blockIcons[1];
            }
        }

        return this.blockIcons[0];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        int metadata = world.getBlockMetadata(x, y, z);
        TileStructure tile = (TileStructure) world.getTileEntity(x, y, z);

        if (metadata == 1) {
            if (side != 0 && side != 1) {
                if (tile != null && tile.getDirection() != null) {
                    if (tile.getDirection().ordinal() == side) {
                        if (tile.isEnabled()) {
                            return this.blockIcons[1];
                        } else {
                            return this.blockIcons[2];
                        }
                    }
                }
            }
        } else {
            if (side != 0 && side != 1) {
                return tile.isEnabled() ? this.blockIcons[1] : this.blockIcons[2];
            }
        }

        return this.blockIcons[0];
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile instanceof TileStructure) {
                TileStructure structure = (TileStructure) tile;
                boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z);

                /**
                 * If the structure is the bridge, we need to check that the
                 * linked block is powered, otherwise shit really hits the fan
                 * and we end up in spammy infinite loops.
                 */
                if (!flag && structure instanceof TileStructureBridge) {
                    TileStructureBridge bridge = (TileStructureBridge) structure;

                    if (bridge.isLinked()) {
                        flag = world.isBlockIndirectlyGettingPowered(bridge.getLinkBridge().xCoord, bridge.getLinkBridge().yCoord, bridge.getLinkBridge().zCoord);
                    }
                }

                if (flag) {
                    if (!structure.isEnabled()) {
                        structure.enable();
                    }
                } else {
                    if (structure.isEnabled()) {
                        structure.disable();
                    }
                }
            }
        }
    }
}
