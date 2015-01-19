package com.ollieread.technomagi.tileentity;

import java.util.List;

import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;

import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.tileentity.abstracts.TileEntityPocket;
import com.ollieread.technomagi.util.GenerationHelper;

public class TileEntityPocketLight extends TileEntityPocket
{

    protected List<ChunkPosition> radiusBlocks = null;

    public TileEntityPocketLight(boolean negative, int size)
    {
        super(size);

        setNegative(negative);
    }

    @Override
    public boolean shouldPerform(int ticks)
    {
        return radiusBlocks != null && radiusBlocks.size() > 0 && ticks == 200;
    }

    @Override
    public void perform()
    {
        int index = rand.nextInt(radiusBlocks.size());
        ChunkPosition position = radiusBlocks.get(index);

        if (position != null) {
            if (worldObj.isAirBlock(position.chunkPosX, position.chunkPosY, position.chunkPosZ)) {
                worldObj.setBlock(position.chunkPosX, position.chunkPosY, position.chunkPosZ, Blocks.blockLight, isNegative() ? 1 : 0, 2);
            }
        }
    }

    @Override
    public int getModifier(double distance)
    {
        return 1;
    }

    @Override
    public PocketType getType()
    {
        return PocketType.AREA;
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            if (radiusBlocks == null) {
                int radius = size / 2;
                radiusBlocks = GenerationHelper.getSphere(Vec3.createVectorHelper(xCoord, yCoord, zCoord), worldObj, radius, radius, radius, true);
            }
        }

        super.updateEntity();
    }

}
