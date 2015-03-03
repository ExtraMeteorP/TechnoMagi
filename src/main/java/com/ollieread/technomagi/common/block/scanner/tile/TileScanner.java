package com.ollieread.technomagi.common.block.scanner.tile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.knowledge.research.Researcher;
import com.ollieread.technomagi.api.scan.IScanTile;
import com.ollieread.technomagi.api.scan.ScanHandler.ScanRepresentation;
import com.ollieread.technomagi.common.block.tile.ITilePlayerOwned;
import com.ollieread.technomagi.common.block.tile.ISideFacing;
import com.ollieread.technomagi.common.block.tile.TileBase;

public class TileScanner extends TileBase implements ISideFacing, ITilePlayerOwned, IScanTile
{

    protected int mode;
    protected String player;
    protected ForgeDirection direction;
    protected boolean registered = false;

    protected Researcher researchHandler = new Researcher();

    public void setMode(int mode)
    {
        this.mode = mode;
    }

    @Override
    public void setDirection(ForgeDirection direction)
    {
        this.direction = direction;
    }

    @Override
    public ForgeDirection getDirection()
    {
        return direction;
    }

    @Override
    public void rotate()
    {
        ForgeDirection[] directions = ForgeDirection.VALID_DIRECTIONS;

        for (int i = 0; i < directions.length; i++) {
            ForgeDirection direction = directions[i];

            if (direction.equals(getDirection())) {
                continue;
            }

            if (worldObj.isSideSolid(xCoord, yCoord, zCoord, direction)) {
                setDirection(direction);
                TechnomagiApi.scan().removeScanner(getRepresentation());
                registered = false;
                break;
            }
        }
    }

    @Override
    public EntityPlayer getPlayerEntity()
    {
        return worldObj.getPlayerEntityByName(player);
    }

    @Override
    public String getPlayer()
    {
        return player;
    }

    @Override
    public boolean isPlayer(EntityPlayer player)
    {
        EntityPlayer owner = getPlayerEntity();

        return owner != null && owner.equals(player);
    }

    @Override
    public boolean isPlayer(String player)
    {
        return this.player != null && this.player.equals(player);
    }

    @Override
    public void setPlayer(EntityPlayer player)
    {
        setPlayer(player.getCommandSenderName());
    }

    @Override
    public void setPlayer(String player)
    {
        this.player = player;
    }

    @Override
    public void updateEntity()
    {
        if (!registered) {
            ForgeDirection direction = getDirection();
            TechnomagiApi.scan().addScanner(getRepresentation());
            registered = true;
        }
    }

    @Override
    public ScanRepresentation getRepresentation()
    {
        ForgeDirection direction = getDirection();

        return new ScanRepresentation(this, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ);
    }

    @Override
    public boolean canResearch(IResearch research)
    {
        return researchHandler.canResearch(research);
    }

    @Override
    public int addResearch(IResearch research, int modifier)
    {
        return researchHandler.addResearch(research, modifier);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (player != null && !player.isEmpty()) {
            compound.setString("Player", player);
        }

        compound.setInteger("Mode", mode);
        compound.setInteger("Direction", direction.ordinal());

        NBTTagCompound researchCompound = new NBTTagCompound();

        researchHandler.saveNBTData(researchCompound);

        compound.setTag("Research", researchCompound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Player")) {
            player = compound.getString("Player");
        }

        mode = compound.getInteger("Mode");
        direction = ForgeDirection.values()[compound.getInteger("Direction")];

        researchHandler.loadNBTData(compound.getCompoundTag("Research"));
    }

}
