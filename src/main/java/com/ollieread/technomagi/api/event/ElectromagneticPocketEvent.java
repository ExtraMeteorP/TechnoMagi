package com.ollieread.technomagi.api.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.ChunkPosition;

import com.ollieread.technomagi.api.electromagnetic.ElectromagneticPocket.PocketSize;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler;
import com.ollieread.technomagi.api.electromagnetic.EnergyHandler.EnergyType;
import com.ollieread.technomagi.common.block.electromagnetic.tile.TileElectromagnetic;

import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

public class ElectromagneticPocketEvent extends Event
{

    public final TileElectromagnetic pocket;
    public final EnergyHandler.EnergyType type;
    public final PocketSize size;
    public final boolean negative;
    public float modifier;
    public float amount;

    public ElectromagneticPocketEvent(TileElectromagnetic pocket, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount)
    {
        this.pocket = pocket;
        this.type = type;
        this.size = size;
        this.negative = negative;
        this.modifier = modifier;
        this.amount = amount;
    }

    @Cancelable
    @HasResult
    public static class ExposeEntity extends ElectromagneticPocketEvent
    {
        public final EntityLivingBase entity;
        public final boolean first;

        public ExposeEntity(TileElectromagnetic pocket, EntityLivingBase entity, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount, boolean first)
        {
            super(pocket, type, size, negative, modifier, amount);

            this.entity = entity;
            this.first = first;
        }
    }

    @Cancelable
    @HasResult
    public static class ExposeItem extends ElectromagneticPocketEvent
    {

        public final EntityItem item;

        public ExposeItem(TileElectromagnetic pocket, EntityItem item, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount)
        {
            super(pocket, type, size, negative, modifier, amount);

            this.item = item;
        }

    }

    @Cancelable
    @HasResult
    public static class ExposeBlock extends ElectromagneticPocketEvent
    {

        public final ChunkPosition block;

        public ExposeBlock(TileElectromagnetic pocket, ChunkPosition block, EnergyHandler.EnergyType type, PocketSize size, boolean negative, float modifier, float amount)
        {
            super(pocket, type, size, negative, modifier, amount);

            this.block = block;
        }

    }

}
