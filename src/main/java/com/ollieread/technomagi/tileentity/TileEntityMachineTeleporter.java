package com.ollieread.technomagi.tileentity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.ollieread.technomagi.tileentity.abstracts.Machine;
import com.ollieread.technomagi.tileentity.component.IHasLink;
import com.ollieread.technomagi.tileentity.component.Linked;

public class TileEntityMachineTeleporter extends Machine implements IHasLink
{

    protected Linked linked = new Linked<TileEntityMachineTeleporter>();
    protected int cooldown = -1;

    protected int mode = 0;

    protected int maxCooldown = 10;

    public TileEntityMachineTeleporter()
    {
        super(3200, 100, 0);
    }

    @Override
    public Container getContainer(InventoryPlayer playerInventory, World world, int x, int y, int z)
    {
        return null;
    }

    @Override
    public int getGui(World world, int x, int y, int z, EntityPlayer player)
    {
        return -1;
    }

    @Override
    public boolean canProcess()
    {
        return false;
    }

    @Override
    public boolean isProcessing()
    {
        return false;
    }

    @Override
    public void process()
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Cooldown", cooldown);
        compound.setInteger("Mode", mode);

        NBTTagCompound linkedCompound = new NBTTagCompound();
        linked.writeToNBT(linkedCompound);

        compound.setTag("Linked", linkedCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        cooldown = compound.getInteger("Cooldown");
        mode = compound.getInteger("Mode");

        linked.readFromNBT(compound.getCompoundTag("Linked"));
    }

    @Override
    public void updateEntity()
    {
        if (cooldown > -1) {
            if (cooldown == maxCooldown) {
                cooldown = -1;
            } else {
                cooldown++;
            }
        }
    }

    public void startCooldown()
    {
        if (cooldown == -1) {
            cooldown = 0;
        }
    }

    public boolean canUse(EntityLivingBase entity)
    {
        if (cooldown == -1) {
            if (mode == 0 && entity instanceof EntityPlayer) {
                return true;
            } else if (mode == 1 && entity instanceof EntityPlayer && isOwner(entity.getCommandSenderName())) {
                return true;
            } else if (mode == 2 && !(entity instanceof EntityPlayer)) {
                return true;
            } else if (mode == 3) {
                return true;
            }
        }

        return false;
    }

    public void setMode(int mode)
    {
        this.mode = mode;

        if (getBlockMetadata() == 1) {
            TileEntityMachineTeleporter partner = (TileEntityMachineTeleporter) linked.getLinked(worldObj);

            if (partner != null) {
                partner.setModeChain(mode);
            }
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        markDirty();
    }

    public void setModeChain(int mode)
    {
        this.mode = mode;

        sync();
    }

    public int getMode()
    {
        return mode;
    }

    /* Everything below is just a proxy for the interfaces */

    /* LINKED */

    public boolean isLinked()
    {
        return linked.isLinked();
    }

    public void unlink()
    {
        linked.unlink();
    }

    public void setLinked(int x, int y, int z)
    {
        linked.setLinked(x, y, z);
    }

    public TileEntityMachineTeleporter getLinked(World world)
    {
        return (TileEntityMachineTeleporter) linked.getLinked(world);
    }

}
