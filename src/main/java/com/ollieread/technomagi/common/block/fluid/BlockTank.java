package com.ollieread.technomagi.common.block.fluid;

import java.util.ArrayList;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.ollieread.technomagi.client.renderers.blocks.BlockTankRenderer;
import com.ollieread.technomagi.common.block.BlockContainerSubtypes;
import com.ollieread.technomagi.common.block.fluid.tile.TileTank;
import com.ollieread.technomagi.common.item.block.ItemBlockTank;
import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockTank extends BlockContainerSubtypes
{

    public BlockTank(String name)
    {
        super(name, new String[] { "basic", "enhanced" }, Material.iron);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata)
    {
        return new TileTank(metadata);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister register)
    {
        this.blockIcons = new IIcon[4];
        this.blockIcons[0] = register.registerIcon(getTexturePath(texturePrefix + "/basic/side"));
        this.blockIcons[1] = register.registerIcon(getTexturePath(texturePrefix + "/basic/top"));
        this.blockIcons[2] = register.registerIcon(getTexturePath(texturePrefix + "/enhanced/side"));
        this.blockIcons[3] = register.registerIcon(getTexturePath(texturePrefix + "/enhanced/top"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metadata)
    {
        if (metadata == 0) {
            if (side == 0 || side == 1) {
                return this.blockIcons[1];
            } else {
                return this.blockIcons[0];
            }
        } else {
            if (side == 0 || side == 1) {
                return this.blockIcons[3];
            } else {
                return this.blockIcons[2];
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
        return getIcon(side, world.getBlockMetadata(x, y, z));
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
    public boolean canRenderInPass(int pass)
    {
        return pass == 1;
    }

    @Override
    public int getRenderType()
    {
        return BlockTankRenderer.id;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        TileTank tank = (TileTank) world.getTileEntity(x, y, z);

        if (stack.stackTagCompound != null) {
            tank.setCapacity(((ItemBlockTank) stack.getItem()).getCapacity(stack));
            tank.fill(null, ((ItemBlockTank) stack.getItem()).getFluid(stack), true);
        }

        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    private boolean fillTankAbove(TileTank tank, FluidStack containerFluid, World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.getBlock(x, y, z) instanceof BlockTank && world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y - 1, z)) {
            TileTank tankAbove = (TileTank) world.getTileEntity(x, y, z);

            if (tankAbove.getFluidAmount() == 0 || tankAbove.canFill(containerFluid)) {
                return ((BlockTank) world.getBlock(x, y, z)).onBlockActivated(world, x, y, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
            }
        }

        return false;
    }

    private boolean fillTankBelow(TileTank tank, FluidStack containerFluid, World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.getBlock(x, y, z) instanceof BlockTank && world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y + 1, z)) {
            TileTank tankBelow = (TileTank) world.getTileEntity(x, y, z);

            if (tankBelow.getFluidAmount() == 0 || tankBelow.canFill(containerFluid)) {
                return ((BlockTank) world.getBlock(x, y, z)).onBlockActivated(world, x, y, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
            }
        }

        return false;
    }

    private boolean drainTankAbove(TileTank tank, FluidStack containerFluid, World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.getBlock(x, y, z) instanceof BlockTank && world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y - 1, z)) {
            TileTank tankAbove = (TileTank) world.getTileEntity(x, y, z);

            if (tankAbove.getFluidAmount() > 0 && tankAbove.getFluid().equals(containerFluid)) {
                return ((BlockTank) world.getBlock(x, y, z)).onBlockActivated(world, x, y, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
            }
        }

        return false;
    }

    private boolean drainTankBelow(TileTank tank, FluidStack containerFluid, World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.getBlock(x, y, z) instanceof BlockTank && world.getBlockMetadata(x, y, z) == world.getBlockMetadata(x, y + 1, z)) {
            TileTank tankBelow = (TileTank) world.getTileEntity(x, y, z);

            if (tankBelow.getFluidAmount() > 0) {
                return ((BlockTank) world.getBlock(x, y, z)).onBlockActivated(world, x, y, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
            }
        }

        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (player != null) {
            TileTank tank = (TileTank) world.getTileEntity(x, y, z);

            if (tank != null) {
                ItemStack stack = player.getHeldItem();
                ForgeDirection direction = ForgeDirection.getOrientation(side);

                FluidStack containerFluid;

                containerFluid = FluidContainerRegistry.getFluidForFilledItem(stack);

                if (containerFluid != null) {
                    if (tank.getFluidAmount() == tank.getCapacity()) {
                        if (containerFluid.getFluid().isGaseous()) {
                            return fillTankBelow(tank, containerFluid, world, x, y - 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
                        } else {
                            return fillTankAbove(tank, containerFluid, world, x, y + 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
                        }
                    } else if (tank.getFluidAmount() == 0) {
                        if (containerFluid.getFluid().isGaseous()) {
                            if (fillTankAbove(tank, containerFluid, world, x, y + 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_)) {
                                return true;
                            }
                        } else {
                            if (fillTankBelow(tank, containerFluid, world, x, y - 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_)) {
                                return true;
                            }
                        }
                    }

                    int qty = tank.fill(direction, containerFluid, true);

                    if (qty != 0 && !player.capabilities.isCreativeMode) {
                        PlayerHelper.consumeHeldItem(player);
                    }

                    tank.sync();

                    return true;
                } else {
                    FluidStack fluid = tank.getFluid();

                    if (!world.isRemote) {
                        if (fluid == null || fluid.amount <= 0) {
                            return drainTankBelow(tank, tank.getFluid(), world, x, y - 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_) || drainTankAbove(tank, tank.getFluid(), world, x, y + 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_);
                        }

                        if (fluid.amount == tank.getCapacity()) {
                            if (fluid.getFluid().isGaseous()) {
                                if (drainTankBelow(tank, tank.getFluid(), world, x, y - 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_)) {
                                    return true;
                                }
                            } else {
                                if (drainTankAbove(tank, tank.getFluid(), world, x, y + 1, z, player, side, p_149727_7_, p_149727_8_, p_149727_9_)) {
                                    return true;
                                }
                            }
                        }

                        ItemStack container = FluidContainerRegistry.fillFluidContainer(fluid, stack);
                        containerFluid = FluidContainerRegistry.getFluidForFilledItem(container);

                        if (containerFluid != null) {
                            if (!player.capabilities.isCreativeMode) {
                                if (stack.stackSize > 1) {
                                    if (!player.inventory.addItemStackToInventory(container)) {
                                        return false;
                                    }

                                    PlayerHelper.consumeHeldItem(player);
                                } else {
                                    player.inventory.setInventorySlotContents(player.inventory.currentItem, container);
                                }
                            }

                            tank.drain(direction, containerFluid.amount, true);
                            tank.sync();

                            return true;
                        }
                    } else {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        TileTank tank = (TileTank) world.getTileEntity(x, y, z);
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        if (tank != null) {
            Item item = getItemDropped(metadata, world.rand, fortune);
            ItemStack stack = new ItemStack(getItemDropped(metadata, world.rand, fortune), 1, damageDropped(metadata));
            ((ItemBlockTank) item).setCapacity(stack, tank.getCapacity());
            ((ItemBlockTank) item).setFluid(stack, tank.getFluid());
            ret.add(stack);
        }

        return ret;
    }

}