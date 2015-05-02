package com.ollieread.technomagi.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.Technomagi;
import com.ollieread.technomagi.api.knowledge.research.IResearcher;
import com.ollieread.technomagi.api.tile.ISideFacing;
import com.ollieread.technomagi.api.tile.ITileDisguisable;
import com.ollieread.technomagi.api.tile.ITileGui;
import com.ollieread.technomagi.api.tile.ITilePlayerLocked;
import com.ollieread.technomagi.api.tile.ITilePlayerOwned;
import com.ollieread.technomagi.api.tile.ITilePlayerRestricted;
import com.ollieread.technomagi.api.tile.ITileRetainsData;
import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.common.tabs.TechnomagiTabs;
import com.ollieread.technomagi.util.ItemNBTHelper;
import com.ollieread.technomagi.util.ItemStackHelper;
import com.ollieread.technomagi.util.PlayerHelper;

public abstract class BlockBaseContainer extends BlockContainer
{

    protected String name;

    protected BlockBaseContainer(String name, Material material)
    {
        super(material);

        this.name = name;
        this.setCreativeTab(TechnomagiTabs.blocks);
        this.setBlockTextureName(getTexturePath(name));
    }

    public String getTexturePath(String name)
    {
        return Technomagi.MODID.toLowerCase() + ":" + name;
    }

    @Override
    public int damageDropped(int metadata)
    {
        return metadata;
    }

    public EnumRarity getItemRarity(int metadata)
    {
        return EnumRarity.common;
    }

    @Override
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)
    {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(x, y, z);

            if (tile instanceof ITileRetainsData) {
                ItemStack stack = new ItemStack(getItem(world, x, y, z));
                ItemNBTHelper.setCompound(stack, "RetainedData", ((ITileRetainsData) tile).getRetainedData());
                ItemStackHelper.dropBlockAsItem(world, x, y, z, stack);
            } else if (tile instanceof IInventory) {
                IInventory inventory = (IInventory) tile;

                for (int i = 0; i < inventory.getSizeInventory(); i++) {
                    ItemStack istack = inventory.getStackInSlot(i);

                    if (istack != null) {
                        ItemStackHelper.dropBlockAsItem(world, x, y, z, istack);
                    }
                }
            }
        }
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion explosion)
    {

    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
    {
        TileBase tile = (TileBase) world.getTileEntity(x, y, z);

        if (tile instanceof ITileRetainsData && ItemNBTHelper.has(stack, "RetainedData")) {
            ((ITileRetainsData) tile).setRetainedData(ItemNBTHelper.getCompound(stack, "RetainedData"));
        }

        if (tile instanceof ITilePlayerOwned && entityLiving instanceof EntityPlayer) {
            ((ITilePlayerOwned) tile).setPlayer((EntityPlayer) entityLiving);
        }

        if (tile instanceof ISideFacing) {
            int l = MathHelper.floor_double(entityLiving.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
            ForgeDirection direction = null;

            if (l == 0) {
                direction = ForgeDirection.getOrientation(2);
            }

            if (l == 1) {
                direction = ForgeDirection.getOrientation(5);
            }

            if (l == 2) {
                direction = ForgeDirection.getOrientation(3);
            }

            if (l == 3) {
                direction = ForgeDirection.getOrientation(4);
            }

            ((ISideFacing) tile).setDirection(direction);
        }

        if (tile instanceof IResearcher) {
            if (entityLiving instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entityLiving;

                ((IResearcher) tile).copyFrom(PlayerHelper.getKnowledge(player));
            }
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof ITilePlayerRestricted && ((ITilePlayerRestricted) tile).isPlayerLocked()) {
            if (!((ITilePlayerRestricted) tile).isPlayer(player) && !((ITilePlayerRestricted) tile).hasAccess(player)) {
                return false;
            }
        } else if (tile instanceof ITilePlayerLocked && ((ITilePlayerLocked) tile).isPlayerLocked()) {
            if (!((ITilePlayerLocked) tile).isPlayer(player)) {
                return false;
            }
        }

        if (tile instanceof ITileDisguisable && player.getHeldItem() != null) {
            if (((ITileDisguisable) tile).setDisguiseBlock(player.getHeldItem())) {
                return true;
            }
        }

        if (tile instanceof ITileGui) {
            if (!world.isRemote) {
                if (tile instanceof TileBase) {
                    ((TileBase) tile).sync();
                }

                player.openGui(Technomagi.instance, Technomagi.proxy.GUI_TILE, world, x, y, z);
            }

            return true;
        }

        return false;
    }

    public abstract void registerTiles();

    @Override
    public String getUnlocalizedName()
    {
        return "tile.technomagi." + name;
    }

}
