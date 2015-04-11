package com.ollieread.technomagi.common.block.structure;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.structure.tile.TileStructure;
import com.ollieread.technomagi.common.block.structure.tile.TileStructureBridge;
import com.ollieread.technomagi.common.block.structure.tile.TileStructurePlatform;

public class BlockStructure extends BlockContainerSubtypes
{

    public BlockStructure(String name)
    {
        super(name, new String[] { "platform", "bridge" }, Material.iron);
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
