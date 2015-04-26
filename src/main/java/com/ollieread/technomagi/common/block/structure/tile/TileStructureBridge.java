package com.ollieread.technomagi.common.block.structure.tile;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.tile.ITileLink;
import com.ollieread.technomagi.common.block.structure.BlockHardlight;
import com.ollieread.technomagi.common.init.Blocks;
import com.ollieread.technomagi.util.BlockHelper;

public class TileStructureBridge extends TileStructure implements ITileLink
{

    protected ChunkCoordinates link;
    protected List<ChunkCoordinates> coords;

    @Override
    public void enable()
    {
        if (!enabled && isLinked()) {
            enabled = true;
            TileStructureBridge bridge = getLinkBridge();

            if (!bridge.isEnabled()) {
                coords = new ArrayList<ChunkCoordinates>();
                bridge.enable();
                int distance = BlockHelper.getDistance(xCoord, yCoord, zCoord, bridge.xCoord, bridge.yCoord, bridge.zCoord) - 1;
                ForgeDirection direction = getDirection();

                for (int i = 1; i <= distance; i++) {
                    int x = xCoord + (i * direction.offsetX);
                    int y = yCoord + (i * direction.offsetY);
                    int z = zCoord + (i * direction.offsetZ);

                    if (worldObj.isAirBlock(x, y, z)) {
                        worldObj.setBlock(x, y, z, Blocks.hardlight);
                        coords.add(new ChunkCoordinates(x, y, z));
                    } else {
                        break;
                    }
                }

                bridge.setCoords(coords);
            }

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    @Override
    public void disable()
    {
        if (enabled && isLinked()) {
            enabled = false;
            TileStructureBridge bridge = getLinkBridge();

            if (bridge.isEnabled()) {
                bridge.disable();

                for (ChunkCoordinates coord : coords) {
                    if (worldObj.getBlock(coord.posX, coord.posY, coord.posZ) instanceof BlockHardlight) {
                        worldObj.setBlockToAir(coord.posX, coord.posY, coord.posZ);
                    }
                }

                coords = new ArrayList<ChunkCoordinates>();
            }

            worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
        }
    }

    public void setCoords(List<ChunkCoordinates> coords)
    {
        this.coords = coords;
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (link != null) {
            compound.setIntArray("Link", new int[] { link.posX, link.posY, link.posZ });
        }

        if (coords != null && coords.size() > 0) {
            NBTTagList coordCompound = new NBTTagList();

            for (ChunkCoordinates coord : coords) {
                NBTTagCompound c = new NBTTagCompound();
                c.setIntArray("Coord", new int[] { coord.posX, coord.posY, coord.posZ });
                coordCompound.appendTag(c);
            }

            compound.setTag("Coords", coordCompound);
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

        if (compound.hasKey("Coords")) {
            NBTTagList coordCompound = compound.getTagList("Coords", compound.getId());
            coords = new ArrayList<ChunkCoordinates>();

            for (int i = 0; i < coordCompound.tagCount(); i++) {
                NBTTagCompound nbt = coordCompound.getCompoundTagAt(i);
                int[] c = nbt.getIntArray("Coord");
                coords.add(new ChunkCoordinates(c[0], c[1], c[2]));
            }
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
                return BlockHelper.getDistance(xCoord, yCoord, zCoord, tile.xCoord, tile.yCoord, tile.zCoord) <= getRange();
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
