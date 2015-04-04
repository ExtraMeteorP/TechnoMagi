package com.ollieread.technomagi.common.block.structure.tile;

import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;

import com.ollieread.technomagi.common.block.tile.ITileLink;

public class TileStructureBridge extends TileStructure implements ITileLink
{

    protected ChunkCoordinates link;
    protected List<ChunkCoordinates> coords;

    @Override
    public boolean isEnabled()
    {
        return enabled;
    }

    public void toggle(boolean clicked)
    {
        if (isLinked()) {
            if (enabled) {
                enabled = false;

                if (clicked) {
                    getLinkBridge().toggle(false);
                    disable();
                }
            } else {
                enabled = true;

                if (clicked) {
                    getLinkBridge().toggle(false);
                    enable();
                }
            }
        }
    }

    @Override
    protected void enable()
    {

    }

    @Override
    protected void disable()
    {

    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (link != null) {
            compound.setIntArray("Link", new int[] { link.posX, link.posY, link.posZ });
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Link")) {
            int[] linkCoords = compound.getIntArray("Link");
            link = new ChunkCoordinates(linkCoords[0], linkCoords[1], linkCoords[2]);
        }
    }

    @Override
    public boolean isLinked()
    {
        return link != null;
    }

    @Override
    public boolean canLink(int x, int y, int z)
    {
        if (y == yCoord) {
            TileEntity tile = worldObj.getTileEntity(x, y, z);

            if (tile != null && tile instanceof TileStructureBridge && ((TileStructureBridge) tile).getDirection().equals(getDirection().getOpposite())) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int getRange()
    {
        return 64;
    }

    @Override
    public void setLink(int x, int y, int z)
    {
        this.link = new ChunkCoordinates(x, y, z);
    }

    @Override
    public ITileLink getLink()
    {
        if (this.link != null) {
            TileEntity tile = worldObj.getTileEntity(link.posX, link.posY, link.posZ);

            return tile instanceof ITileLink ? (ITileLink) tile : null;
        }

        return null;
    }

    public TileStructureBridge getLinkBridge()
    {
        if (this.isLinked()) {
            TileEntity tile = worldObj.getTileEntity(link.posX, link.posY, link.posZ);

            if (tile != null && tile instanceof TileStructureBridge) {
                return (TileStructureBridge) tile;
            }
        }

        return null;
    }

    @Override
    public TileEntity getTile()
    {
        return this;
    }

    @Override
    public void removeLink()
    {
        this.link = null;
    }

    @Override
    public boolean isProxy()
    {
        return false;
    }

}
