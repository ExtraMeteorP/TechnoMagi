package com.ollieread.technomagi.api.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.entity.IEntityDescriptor.IEntityResearchNanites;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Chance;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.nanites.EntityNanites;

public class EntityTechnomagi implements IExtendedEntityProperties
{

    protected EntityLivingBase entity;
    protected Random rand = new Random();

    protected EntityNanites entityNanites;

    public EntityTechnomagi(EntityLivingBase entity)
    {
        this.entity = entity;

        IEntityDescriptor descriptor = TechnomagiApi.entity().getEntity(entity.getClass());

        if (descriptor instanceof IEntityResearchNanites) {
            IEntityResearchNanites nanites = (IEntityResearchNanites) descriptor;

            this.entityNanites = new EntityNanites(false);
            this.entityNanites.setMaxData(nanites.getMaxData());
            this.entityNanites.setMaxNanites(nanites.getMaxNanites());
            this.entityNanites.setRegen(nanites.getNaniteRegen());
            this.entityNanites.setRegenTicks(nanites.getNaniteRegenTicks());
        }
    }

    public static final void register(EntityLivingBase entity)
    {
        entity.registerExtendedProperties(TechnomagiApi.IDENT_ENTITY, new EntityTechnomagi(entity));
    }

    public static final EntityTechnomagi get(EntityLivingBase entity)
    {
        return (EntityTechnomagi) entity.getExtendedProperties(TechnomagiApi.IDENT_ENTITY);
    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        if (entityNanites != null) {
            /**
             * Save the entities nanite data.
             */
            NBTTagCompound nanitesCompound = new NBTTagCompound();
            entityNanites.saveNBTData(nanitesCompound);
            compound.setTag("Nanites", nanitesCompound);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        if (entityNanites != null) {
            /**
             * Load the entities nanite data.
             */
            entityNanites.loadNBTData(compound.getCompoundTag("Nanites"));
        }
    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    /**
     * Perform a piece of research.
     *
     * This checks to make sure that the knowledge hasn't already been
     * discovered, the research hasn't already been performed and whether or not
     * the research has been performed the maximum amount of times. It will also
     * automatically add knowledge progress.
     *
     * The chance of research succeeding is denoted by
     * {@link IResearch#getChance()}, but can be modified outside of this using
     * {@link Chance} on the {@link TechnomagiApi.EVENT_BUS}.
     *
     * @param technomage
     * @param research
     */
    public boolean performResearch(IResearch research, Knowledge knowledge)
    {
        if (entityNanites.hasOwner() && entityNanites.canResearch(research)) {
            int chance = research.getChance();

            if (rand.nextInt(chance) == 0) {

                if (entityNanites.canDecreaseNanites(research.getProgress()) && entityNanites.canIncreaseData(research.getProgress())) {
                    int progress = entityNanites.addResearch(research, 1);

                    if (progress > 0) {
                        entityNanites.decreaseNanites(progress);
                        entityNanites.increaseData(progress);

                        if (entityNanites.addKnowledgeProgress(knowledge.getName(), progress)) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

}
