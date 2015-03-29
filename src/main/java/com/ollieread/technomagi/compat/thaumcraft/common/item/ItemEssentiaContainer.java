package com.ollieread.technomagi.compat.thaumcraft.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaContainerItem;

import com.ollieread.technomagi.common.item.ItemBase;
import com.ollieread.technomagi.util.ItemNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEssentiaContainer extends ItemBase implements IEssentiaContainerItem
{

    @SideOnly(Side.CLIENT)
    protected IIcon overlayIcon;

    public static int MAX_AMOUNT = 32;

    public ItemEssentiaContainer(String name)
    {
        super("thaumcraft." + name);
    }

    @Override
    public AspectList getAspects(ItemStack stack)
    {
        AspectList aspects = new AspectList();

        if (ItemNBTHelper.has(stack, "Aspects")) {
            aspects.readFromNBT(ItemNBTHelper.getCompound(stack, "Aspects"));
        }

        return aspects;
    }

    @Override
    public void setAspects(ItemStack stack, AspectList aspects)
    {
        NBTTagCompound aspectCompound = new NBTTagCompound();
        aspects.writeToNBT(aspectCompound);
        ItemNBTHelper.setCompound(stack, "Aspects", aspectCompound);
    }

    public boolean setAspect(ItemStack stack, Aspect aspect, int amount)
    {
        AspectList aspects = getAspects(stack);

        if (aspect != null) {
            if (aspects.getAmount(aspect) > 0) {
                int actual = aspects.getAmount(aspect) + amount;

                if (actual <= MAX_AMOUNT) {
                    aspects.add(aspect, actual);
                    setAspects(stack, aspects);
                    stack.setItemDamage(1);
                    return true;
                }
            } else if (aspects.size() == 0) {
                aspects.add(aspect, amount);
                setAspects(stack, aspects);
                stack.setItemDamage(1);
                return true;
            }
        } else {
            setAspects(stack, new AspectList());
            stack.setItemDamage(0);
        }

        return false;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof IAspectContainer) {
            IAspectContainer container = (IAspectContainer) tile;

            if (stack.getItemDamage() == 1) {
                AspectList aspects = getAspects(stack);

                if (aspects.size() > 0) {
                    Aspect aspect = aspects.getAspects()[0];
                    int amount = aspects.getAmount(aspect);

                    if (container.doesContainerAccept(aspect)) {
                        int leftOver = container.addToContainer(aspect, amount);

                        if (leftOver == 0) {
                            setAspect(stack, null, 0);
                        } else {
                            setAspect(stack, aspect, leftOver);
                        }

                        return true;
                    }
                } else {
                    AspectList containerAspects = container.getAspects();

                    if (containerAspects.aspects.size() > 0) {
                        Aspect aspect = containerAspects.getAspects()[0];
                        int amount = containerAspects.getAmount(aspect);
                        amount = amount > MAX_AMOUNT ? MAX_AMOUNT : amount;

                        if (container.takeFromContainer(aspect, amount)) {
                            return setAspect(stack, aspect, amount);
                        }
                    }
                }
            } else {
                AspectList containerAspects = container.getAspects();

                if (containerAspects.aspects.size() > 0) {
                    Aspect aspect = containerAspects.getAspects()[0];
                    int amount = containerAspects.getAmount(aspect);
                    amount = amount > MAX_AMOUNT ? MAX_AMOUNT : amount;

                    if (container.takeFromContainer(aspect, amount)) {
                        return setAspect(stack, aspect, amount);
                    }
                }
            }
        }

        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        overlayIcon = register.registerIcon(getTexturePath(this.getIconString()) + "/overlay");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(ItemStack stack, int pass)
    {
        return super.getIcon(stack, pass);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemStack(ItemStack stack, int damage)
    {
        if (damage != 0) {
            AspectList aspects = getAspects(stack);

            if (aspects.size() > 0) {
                return aspects.getAspects()[0].getColor();
            }
        }

        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public int getRenderPasses(int damage)
    {
        return damage == 0 ? 1 : 2;
    }

}
