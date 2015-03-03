package com.ollieread.technomagi.common.block.conduit;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.IFluidHandler;
import cofh.api.energy.IEnergyReceiver;

import com.ollieread.technomagi.client.renderers.blocks.BlockConduitRenderer;
import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.conduit.tile.TileConduitFluid;
import com.ollieread.technomagi.common.block.conduit.tile.TileConduitPower;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConduit extends BlockContainerSubtypes
{

    @SideOnly(Side.CLIENT)
    public IIcon[] blockIcons;
    @SideOnly(Side.CLIENT)
    public IIcon fluidOverlayIcon;

    public BlockConduit(String name)
    {
        super(name, new String[] { "power_basic", "power_advanced", "power_elite", "fluid_basic", "fluid_advanced", "fluid_elite" }, Material.iron);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
    {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        switch (metadata) {
            case 0:
                return new TileConduitPower(512);
            case 1:
                return new TileConduitPower(2048);
            case 2:
                return new TileConduitPower(4096);
            case 3:
                return new TileConduitFluid(FluidContainerRegistry.BUCKET_VOLUME * 10, 100);
            case 4:
                return new TileConduitFluid(FluidContainerRegistry.BUCKET_VOLUME * 100, 50);
            case 5:
                return new TileConduitFluid(FluidContainerRegistry.BUCKET_VOLUME * 1000, 10);
        }

        return null;
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    public boolean canConnectTo(IBlockAccess world, int x, int y, int z, ForgeDirection direction)
    {
        TileEntity tile = world.getTileEntity(x + direction.offsetX, y + direction.offsetY, z + direction.offsetZ);

        if (tile != null) {
            int meta = world.getBlockMetadata(x, y, z);

            if (meta < 3) {
                return tile instanceof IEnergyReceiver && ((IEnergyReceiver) tile).canConnectEnergy(direction.getOpposite());
            } else if (meta >= 3 && meta <= 5) {
                return tile instanceof IFluidHandler && (((IFluidHandler) tile).canFill(direction.getOpposite(), null) || ((IFluidHandler) tile).canDrain(direction.getOpposite(), null));
            }
        }

        return false;
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return BlockConduitRenderer.id;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z)
    {
        float d = 0.3125F;
        float d2 = d + 0.375F;

        boolean up = canConnectTo(blockAccess, x, y, z, ForgeDirection.UP);
        boolean down = canConnectTo(blockAccess, x, y, z, ForgeDirection.DOWN);
        boolean north = canConnectTo(blockAccess, x, y, z, ForgeDirection.NORTH);
        boolean south = canConnectTo(blockAccess, x, y, z, ForgeDirection.SOUTH);
        boolean east = canConnectTo(blockAccess, x, y, z, ForgeDirection.EAST);
        boolean west = canConnectTo(blockAccess, x, y, z, ForgeDirection.WEST);

        float minX = d;
        float minY = d;
        float minZ = d;
        float maxX = d2;
        float maxY = d2;
        float maxZ = d2;

        if (up) {
            maxY = 1F;
        }

        if (down) {
            minY = 0F;
        }

        if (north) {
            minZ = 0F;
        }

        if (south) {
            maxZ = 1F;
        }

        if (east) {
            maxX = 1F;
        }

        if (west) {
            minX = 0F;
        }

        setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
    }

}
