package com.ollieread.technomagi.tileentity;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import com.ollieread.ennds.extended.ExtendedPlayerKnowledge;
import com.ollieread.technomagi.tileentity.proxy.PlayerLocked;

public class TileEntityArchive extends TileEntityTM implements IPlayerLocked
{

    protected PlayerLocked locked = new PlayerLocked();

    public int field_145926_a;
    public float field_145933_i;
    public float field_145931_j;
    public float field_145932_k;
    public float field_145929_l;
    public float field_145930_m;
    public float field_145927_n;
    public float field_145928_o;
    public float field_145925_p;
    public float field_145924_q;
    private static Random field_145923_r = new Random();

    protected int syncCheck = 0;

    public void updateEntity()
    {
        super.updateEntity();
        this.field_145927_n = this.field_145930_m;
        this.field_145925_p = this.field_145928_o;
        EntityPlayer entityplayer = this.worldObj.getClosestPlayer((double) ((float) this.xCoord + 0.5F), (double) ((float) this.yCoord + 0.5F), (double) ((float) this.zCoord + 0.5F), 8.5D);

        if (entityplayer != null && isPlayer(entityplayer)) {
            double d0 = entityplayer.posX - (double) ((float) this.xCoord + 0.5F);
            double d1 = entityplayer.posZ - (double) ((float) this.zCoord + 0.5F);
            this.field_145924_q = (float) Math.atan2(d1, d0);
            this.field_145930_m += 0.1F;

            if (this.field_145930_m < 0.5F || field_145923_r.nextInt(40) == 0) {
                float f1 = this.field_145932_k;

                do {
                    this.field_145932_k += (float) (field_145923_r.nextInt(4) - field_145923_r.nextInt(4));
                } while (f1 == this.field_145932_k);
            }
        } else {
            this.field_145924_q += 0.02F;
            this.field_145930_m -= 0.1F;
        }

        while (this.field_145928_o >= (float) Math.PI) {
            this.field_145928_o -= ((float) Math.PI * 2F);
        }

        while (this.field_145928_o < -(float) Math.PI) {
            this.field_145928_o += ((float) Math.PI * 2F);
        }

        while (this.field_145924_q >= (float) Math.PI) {
            this.field_145924_q -= ((float) Math.PI * 2F);
        }

        while (this.field_145924_q < -(float) Math.PI) {
            this.field_145924_q += ((float) Math.PI * 2F);
        }

        float f2;

        for (f2 = this.field_145924_q - this.field_145928_o; f2 >= (float) Math.PI; f2 -= ((float) Math.PI * 2F)) {
            ;
        }

        while (f2 < -(float) Math.PI) {
            f2 += ((float) Math.PI * 2F);
        }

        this.field_145928_o += f2 * 0.4F;

        if (this.field_145930_m < 0.0F) {
            this.field_145930_m = 0.0F;
        }

        if (this.field_145930_m > 1.0F) {
            this.field_145930_m = 1.0F;
        }

        ++this.field_145926_a;
        this.field_145931_j = this.field_145933_i;
        float f = (this.field_145932_k - this.field_145933_i) * 0.4F;
        float f3 = 0.2F;

        if (f < -f3) {
            f = -f3;
        }

        if (f > f3) {
            f = f3;
        }

        this.field_145929_l += (f - this.field_145929_l) * 0.9F;
        this.field_145933_i += this.field_145929_l;

        if (!worldObj.isRemote) {
            if (syncCheck == 10) {
                if (canSync()) {
                    EntityPlayer owner = getEntityPlayer();
                    ExtendedPlayerKnowledge playerKnowledge = ExtendedPlayerKnowledge.get(owner);

                    if (playerKnowledge != null && playerKnowledge.nanites != null) {
                        Map<String, Integer> researchingKnowledge = playerKnowledge.nanites.getResearchingKnowledge();

                        if (researchingKnowledge.size() > 0) {
                            for (Iterator<String> i = researchingKnowledge.keySet().iterator(); i.hasNext();) {
                                String knowledge = i.next();
                                int value = researchingKnowledge.get(knowledge);

                                if (playerKnowledge.hasKnowledge(knowledge)) {
                                    playerKnowledge.nanites.decreaseData(value, knowledge);
                                    continue;
                                }

                                if (value > 0) {
                                    if (value >= 5 && playerKnowledge.nanites.decreaseData(5, knowledge)) {
                                        playerKnowledge.addKnowledgeProgress(knowledge, 5);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                syncCheck = 0;
            }
            syncCheck++;
        }
    }

    public boolean canSync()
    {
        EntityPlayer owner = getEntityPlayer();

        if (owner != null && owner.getDistance(xCoord, yCoord, zCoord) <= 8) {
            return true;
        }

        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        syncCheck = compound.getInteger("SyncCheck");

        locked.readFromNBT(compound);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("SyncCheck", syncCheck);

        locked.writeToNBT(compound);
    }

    /* Everything below is just a proxy for the interfaces */

    /* LOCKED */

    @Override
    public boolean hasPlayer()
    {
        return locked.hasPlayer();
    }

    @Override
    public void setPlayer(String name)
    {
        locked.setPlayer(name);
    }

    @Override
    public String getPlayer()
    {
        return locked.getPlayer();
    }

    @Override
    public boolean isPlayer(String name)
    {
        return locked.isPlayer(name);
    }

    public boolean isPlayer(EntityPlayer player)
    {
        return isPlayer(player.getCommandSenderName());
    }

    public EntityPlayer getEntityPlayer()
    {
        String playerName = getPlayer();

        if (playerName != null && !playerName.equals("none")) {
            return worldObj.getPlayerEntityByName(playerName);
        }

        return null;
    }

}
