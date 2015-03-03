package com.ollieread.technomagi.common.block.electromagnetic.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;

import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.EnergyType;
import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.PocketSize;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeBlock;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeEntity;
import com.ollieread.technomagi.api.event.ElectromagneticPocketEvent.ExposeItem;
import com.ollieread.technomagi.api.event.TechnomagiHooks;
import com.ollieread.technomagi.common.block.tile.TileBase;
import com.ollieread.technomagi.util.GenerationHelper;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class TileElectromagnetic extends TileBase
{

    protected EnergyType type;
    protected PocketSize size;
    protected int radius;
    protected int volume;
    protected int energyLevel;
    protected int regenSpeed;
    protected boolean negative;
    protected Random rand = new Random();
    protected int ticks = 0;

    protected List<String> exposedPlayers = new ArrayList<String>();

    public TileElectromagnetic()
    {
    }

    public void set(EnergyType type, int radius, boolean negative)
    {
        this.type = type;
        this.radius = radius;
        this.negative = negative;
        this.size = PocketSize.getPocketSize(radius);
        this.volume = (int) Math.ceil((4.0 / 3) * Math.PI * Math.pow(radius, 3));
        this.energyLevel = 100 * volume;
        this.regenSpeed = (radius / 4) * 20;
    }

    @Override
    public void updateEntity()
    {
        if (!worldObj.isRemote) {
            /*
             * int chance = size.maxRadius / 8;
             * 
             * if (rand.nextInt(chance) == 0) { expose(); }
             * 
             * ticks++;
             * 
             * if (ticks >= regenSpeed) { regen(); }
             */
        }
    }

    private void expose()
    {
        /**
         * Here we expose the living entities within range, including players.
         */
        List<EntityLivingBase> entities = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, getAABB());
        List<String> newPlayers = new ArrayList<String>();

        for (EntityLivingBase entity : entities) {
            if (withinRange(entity)) {
                /*
                 * This calculation is a bit messy, but it seemed to be the only
                 * I could come up with, that factored in distance from the
                 * actual pocket, without HUGELY increasing the chance the
                 * closer you are.
                 */
                float modifier = (float) Math.max(((radius / 4) / Math.max(entity.getDistance(xCoord, yCoord, zCoord), 1)) * 0.5F, 1);
                float amount = 0.5F * modifier;

                if (rand.nextInt((int) Math.ceil(32 / modifier)) == 0) {
                    boolean flag = false;

                    if (entity instanceof EntityPlayer) {
                        EntityPlayer player = (EntityPlayer) entity;

                        if (!exposedPlayers.contains(player.getCommandSenderName())) {
                            flag = true;
                        }
                    }

                    ExposeEntity event = TechnomagiHooks.electromagneticPocketEntity(this, entity, type, size, negative, modifier, amount, flag);
                    if (event.isCanceled() || event.getResult().equals(Result.DENY)) {
                        continue;
                    } else if (event.getResult().equals(Result.ALLOW)) {
                        if (flag) {
                            newPlayers.add(((EntityPlayer) entity).getCommandSenderName());
                        }

                        modifyEnergyLevel((int) -event.amount);
                    }
                }
            }
        }

        exposedPlayers = newPlayers;

        /**
         * Affect the items. Certain items exposed to certain energies will do
         * certain things. I appreciate how vague that is, but tough titties.
         */
        List<EntityItem> itemList = worldObj.getEntitiesWithinAABB(EntityItem.class, getAABB());

        for (EntityItem item : itemList) {
            if (withinRange(item)) {
                float modifier = (float) Math.max(((radius / 4) / Math.max(item.getDistance(xCoord, yCoord, zCoord), 1)) * 0.5F, 1);
                float amount = 0.5F * modifier;

                ExposeItem event = TechnomagiHooks.electromagneticPocketItem(this, item, type, size, negative, modifier, amount);

                if (event.isCanceled() || event.getResult().equals(Result.DENY)) {
                    continue;
                } else if (event.getResult().equals(Result.ALLOW)) {
                    modifyEnergyLevel((int) -event.amount);
                }
            }
        }

        /**
         * Affect the environment surround the pocket, rather than just provide
         * a list of all blocks within the aoe, we select a random number based
         * on the pockets radius. Minimum amount being r/4 and maximum being
         * (r/4)*2
         */
        List<ChunkPosition> blocks = GenerationHelper.getSphere(Vec3.createVectorHelper(xCoord, yCoord, zCoord), worldObj, radius, radius, radius, true);
        int affected = (radius / 4) + rand.nextInt(Math.max(radius / 4, 1));
        List<ChunkPosition> affectedBlocks = new ArrayList<ChunkPosition>();

        if (blocks.size() <= affected) {
            affected = blocks.size();
        }

        while (affectedBlocks.size() < affected) {
            ChunkPosition block = blocks.get(rand.nextInt(blocks.size()));

            if (!affectedBlocks.contains(block)) {
                affectedBlocks.add(block);
            }

            float modifier = Math.max(((radius / 4) / Math.max(getDistance(block.chunkPosX, block.chunkPosY, block.chunkPosZ), 1)) * 0.5F, 1);
            float amount = 0.5F * modifier;

            ExposeBlock event = TechnomagiHooks.electromagneticPocketBlock(this, block, type, size, negative, modifier, amount);

            if (event.isCanceled() || event.getResult().equals(Result.DENY)) {
                continue;
            } else if (event.getResult().equals(Result.ALLOW)) {
                modifyEnergyLevel((int) -event.amount);
            }
        }
    }

    public void regen()
    {

    }

    public void drain(int drain)
    {

    }

    public AxisAlignedBB getAABB()
    {
        return AxisAlignedBB.getBoundingBox(xCoord - radius, yCoord - radius, zCoord - radius, xCoord - radius, yCoord - radius, zCoord - radius);
    }

    public boolean withinRange(Entity entity)
    {
        return withinRange((int) entity.posX, (int) entity.posY, (int) entity.posZ);
    }

    public boolean withinRange(int x, int y, int z)
    {
        return getDistance(x, y, z) <= radius;
    }

    public float getDistance(int x, int y, int z)
    {
        double d3 = xCoord - x;
        double d4 = yCoord - y;
        double d5 = zCoord - z;

        return MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        compound.setInteger("Type", type.ordinal());
        compound.setInteger("Radius", radius);
        compound.setInteger("EnergyLevel", energyLevel);
        compound.setInteger("RegenSpeed", regenSpeed);
        compound.setBoolean("Negative", negative);
        compound.setInteger("Ticks", ticks);

        NBTTagList playerList = new NBTTagList();

        for (String player : exposedPlayers) {
            NBTTagCompound playerCompound = new NBTTagCompound();
            playerCompound.setString("Player", player);
            playerList.appendTag(playerCompound);
        }

        compound.setTag("Players", playerList);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        this.type = EnergyType.values()[compound.getInteger("Type")];
        this.radius = compound.getInteger("Radius");
        this.size = PocketSize.getPocketSize(radius);

        if (this.size == null) {
            this.radius = 64;
            this.size = PocketSize.LARGE;
        }

        this.volume = (int) Math.ceil((4.0 / 3) * Math.PI * Math.pow(radius, 3));
        this.energyLevel = compound.getInteger("EnergyLevel");
        this.regenSpeed = compound.getInteger("RegenSpeed");
        this.negative = compound.getBoolean("Negative");
        this.ticks = compound.getInteger("Ticks");

        NBTTagList playerList = compound.getTagList("Players", compound.getId());
        exposedPlayers = new ArrayList<String>();

        for (int i = 0; i < playerList.tagCount(); i++) {
            exposedPlayers.add(playerList.getCompoundTagAt(i).getString("Player"));
        }
    }

    public boolean isNegative()
    {
        return negative;
    }

    public EnergyType getType()
    {
        return type;
    }

    public PocketSize getSize()
    {
        return size;
    }

    public int getRadius()
    {
        return radius;
    }

    public int getEnergyLevel()
    {
        return energyLevel;
    }

    public void setRadius(int radius)
    {
        if (PocketSize.getPocketSize(radius) == null) {
            return;
        }

        this.radius = radius;
        this.size = PocketSize.getPocketSize(radius);
        this.volume = (int) Math.ceil((4.0 / 3) * Math.PI * Math.pow(radius, 3));
        this.regenSpeed = (radius / 4) * 20;
    }

    public void modifyEnergyLevel(int amount)
    {
        this.energyLevel += amount;

        int newRadius = (int) Math.ceil(Math.pow(((3.0 * (energyLevel / 100)) / (4.0 * Math.PI)), 1.0 / 3.0));

        if (newRadius != radius) {
            setRadius(radius);
        }
    }

}
