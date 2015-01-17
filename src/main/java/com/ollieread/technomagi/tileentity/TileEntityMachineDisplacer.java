package com.ollieread.technomagi.tileentity;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.abstracts.Machine;
import com.ollieread.technomagi.util.GenerationHelper;

public class TileEntityMachineDisplacer extends Machine
{
    protected boolean on;

    public TileEntityMachineDisplacer()
    {
        super(0);
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
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        on = compound.getBoolean("On");
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setBoolean("On", on);
    }

    public boolean isOn()
    {
        return on;
    }

    public void toggleStatus()
    {
        if (!worldObj.isRemote) {
            if (on) {
                on = false;
            } else {
                on = true;

                if (worldObj.isAirBlock(xCoord, yCoord + 1, zCoord)) {
                    worldObj.setBlock(xCoord, yCoord + 1, zCoord, Blocks.blockDisplacedAir);
                    TileEntityAirDisplaced air = (TileEntityAirDisplaced) worldObj.getTileEntity(xCoord, yCoord + 1, zCoord);

                    if (air != null) {
                        air.setMaster(xCoord, yCoord, zCoord);
                    }
                }
            }
        }
    }

    public void placed(EntityLivingBase entity, ItemStack stack)
    {
        List affectedBlocks = GenerationHelper.getSphere(Vec3.createVectorHelper(xCoord, yCoord, zCoord), worldObj, 6, 6, 6, true);

        Iterator iterator = affectedBlocks.iterator();

        if (iterator != null) {
            while (iterator.hasNext()) {
                ChunkPosition pos = (ChunkPosition) iterator.next();
                Block block = worldObj.getBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ);

                if (worldObj.isAirBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ) || block == net.minecraft.init.Blocks.water || block == net.minecraft.init.Blocks.flowing_water) {
                    worldObj.setBlock(pos.chunkPosX, pos.chunkPosY, pos.chunkPosZ, Blocks.blockDisplacedAir);
                }
            }
        }
    }

}
