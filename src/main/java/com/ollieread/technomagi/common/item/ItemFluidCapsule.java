package com.ollieread.technomagi.common.item;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import com.ollieread.technomagi.util.PlayerHelper;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFluidCapsule extends ItemFluid
{

    @SideOnly(Side.CLIENT)
    public IIcon itemOverlay;
    @SideOnly(Side.CLIENT)
    public IIcon itemOverlayGas;

    public ItemFluidCapsule(String name)
    {
        super(name);

        this.setMaxDamage(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        FluidStack fluid = getFluid(stack);

        if (fluid != null) {
            list.add(fluid.getLocalizedName());
            list.add(fluid.amount + "/" + getCapacity(stack));
        } else {
            list.add("0/" + getCapacity(stack));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register)
    {
        itemIcon = register.registerIcon(getTexturePath("capsule"));
        itemOverlay = register.registerIcon(getTexturePath("capsule/overlay"));
        itemOverlayGas = register.registerIcon(getTexturePath("capsule/overlay_gas"));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return getIconIndex(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean shouldRotateAroundWhenRendering()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack stack)
    {
        return itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
        ItemStack capsuleStack = new ItemStack(item, 1);
        setCapacity(capsuleStack, FluidContainerRegistry.BUCKET_VOLUME);

        list.add(capsuleStack);

        for (Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
            ItemStack stack = capsuleStack.copy();
            stack.setItemDamage(1);
            setFluid(stack, fluid, FluidContainerRegistry.BUCKET_VOLUME);
            list.add(stack);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        MovingObjectPosition movingobjectposition = getMovingObjectPositionFromPlayer(world, player, true);

        if (getFluid(stack) == null) {
            if (movingobjectposition == null) {
                return stack;
            } else {
                FillBucketEvent event = new FillBucketEvent(player, stack, world, movingobjectposition);

                if (MinecraftForge.EVENT_BUS.post(event)) {
                    return stack;
                }

                if (event.getResult() == Event.Result.ALLOW) {
                    if (player.capabilities.isCreativeMode) {
                        return stack;
                    }

                    if (--stack.stackSize <= 0) {
                        return event.result;
                    }

                    PlayerHelper.giveInventoryItem(player, event.result);

                    return stack;
                }

                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int x = movingobjectposition.blockX;
                    int y = movingobjectposition.blockY;
                    int z = movingobjectposition.blockZ;

                    if (!world.canMineBlock(player, x, y, z)) {
                        return stack;
                    }

                    if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, stack)) {
                        return stack;
                    }

                    Block block = world.getBlock(x, y, z);

                    Fluid fluid = FluidRegistry.lookupFluidForBlock(block);

                    if (fluid != null) {
                        ItemStack fullStack = new ItemStack(stack.getItem(), 1, 1);
                        setCapacity(fullStack, getCapacity(stack));
                        setFluid(fullStack, fluid, FluidContainerRegistry.BUCKET_VOLUME);

                        if (PlayerHelper.consumeInventoryItem(player, stack)) {
                            PlayerHelper.giveInventoryItem(player, fullStack);
                        }
                    }
                }
            }
        }

        return stack;
    }

}
