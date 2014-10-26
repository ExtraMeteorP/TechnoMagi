package com.ollieread.technomagi.block;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.ollieread.technomagi.TechnoMagi;
import com.ollieread.technomagi.common.CommonProxy;
import com.ollieread.technomagi.tileentity.IPlayerLocked;
import com.ollieread.technomagi.tileentity.TileEntityConstruct;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockConstruct extends BlockTMContainer
{

    public BlockConstruct(String name)
    {
        super(Material.iron, name);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityConstruct();
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)
    {
        int l = MathHelper.floor_double((double) (entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) {
            world.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            world.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            world.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            world.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }

        TileEntity te = world.getTileEntity(x, y, z);

        if (te instanceof IPlayerLocked) {
            ((IPlayerLocked) te).setPlayer(((EntityPlayer) entity).getCommandSenderName());
        }
    }

    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
        if (world.isRemote) {
            return true;
        } else {
            TileEntityConstruct entity = (TileEntityConstruct) world.getTileEntity(x, y, z);

            if (entity != null) {
                if (entity.isPlayer(player) && !entity.isBuilding()) {
                    player.openGui(TechnoMagi.instance, CommonProxy.GUI_CONSTRUCT, world, x, y, z);
                }
            }

            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand)
    {
        TileEntityConstruct construct = (TileEntityConstruct) world.getTileEntity(x, y, z);

        if (construct != null && construct.isBuilding() && rand.nextInt(5) == 0) {
            double px = x + 0.5D;
            double py = y + 0.5D;
            double pz = z + 0.5D;

            double m = (rand.nextFloat() - 0.5D) * 0.5D;

            EntityFX effect1 = new EntityPortalFX(world, px + 0.5D, py, pz, +1.0D + m, 0, 0);
            effect1.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

            EntityFX effect2 = new EntityPortalFX(world, px - 0.5D, py, pz, -1.0D + m, 0, 0);
            effect2.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

            EntityFX effect3 = new EntityPortalFX(world, px, py + 0.5D, pz, 0, 1.0D + m, 0);
            effect3.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

            EntityFX effect4 = new EntityPortalFX(world, px, py - 0.5D, pz, 0, 0 - 1.0D + m, 0);
            effect4.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

            EntityFX effect5 = new EntityPortalFX(world, px, py, pz + 0.5D, 0, 0, 1.0D + m);
            effect5.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

            EntityFX effect6 = new EntityPortalFX(world, px, py, pz - 0.5D, 0, 0, -1.0D + m);
            effect6.setRBGColorF(147 / 255.0F, 225 / 255.0F, 242 / 255.0F);

            Minecraft.getMinecraft().effectRenderer.addEffect(effect1);
            Minecraft.getMinecraft().effectRenderer.addEffect(effect2);
            Minecraft.getMinecraft().effectRenderer.addEffect(effect3);
            Minecraft.getMinecraft().effectRenderer.addEffect(effect4);
            Minecraft.getMinecraft().effectRenderer.addEffect(effect5);
            Minecraft.getMinecraft().effectRenderer.addEffect(effect6);
        }
    }
}
