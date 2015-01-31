package com.ollieread.technomagi.api.entity;

import net.minecraft.entity.EntityLivingBase;

/**
 * This interface is for the registration of entities with the various
 * Technomagi specific features.
 * 
 * @author ollieread
 *
 */
public interface IEntityDescriptor
{

    /**
     * The main entity class, must extend {@link EntityLivingBase}
     * 
     * @return
     */
    public Class<? extends EntityLivingBase> getEntity();

    /**
     * Whether or not the entity is a monster, this just changes the colour of
     * the textures and the formatting applied to the names.
     * 
     * @return
     */
    public boolean isMonster();

    /**
     * Whether or not the entity is undead, this will reverse certain effects,
     * in the same way that certain potions have reverse effects on Zombies.
     * 
     * @return
     */
    public boolean isUndead();

    /**
     * Whether or not the entity can be captured.
     * 
     * @return
     */
    public boolean canBeCaptured();

    /**
     * Whether or not the entity can be monitored. It's expected that if this
     * returns true, that the object also implement
     * {@link IEntityResearchNanites}.
     * 
     * @return
     */
    public boolean canBeMonitored();

    /**
     * This interface should be used alongside {@link IEntityDescriptor} for
     * entities that should drop a brain.
     * 
     * @author ollieread
     *
     */
    public static interface IEntityBrain
    {

        /**
         * The ticks that a brain can exist for. I recommend 8400 which is a
         * approximately 7 minutes, the average time before brain death once a
         * brains oxygen has been cut off.
         * 
         * @return
         */
        public int getBrainMaxLife();

        /**
         * The multiplier for determining how long a brain will last once it has
         * been preserved.
         * 
         * @return
         */
        public int getBrainPreservedMultiplier();

        /**
         * The chance that the brain will drop upon the entities death.
         * 
         * @return
         */
        public int getBrainDropChance();

    }

    /**
     * This interface should be used alongside {@link IEntityDescriptor} for
     * entities that can yield blood samples, meaning they can be affected by
     * the targeted nanites. This should only be used for entities that are
     * capable of having a circulatory system.
     * 
     * If intention is for the entity to have research nanites using
     * {@link IEntityResearchNanites} then there is no need to explicitly
     * implement this interface.
     * 
     * @author ollieread
     *
     */
    public static interface IEntitySample
    {

        /**
         * The volume of the sample, average player sample would be 1.0F.
         * 
         * @return
         */
        public float getSampleVolume();

        /**
         * The amount of damage to apply to the entity after a sample is taken.
         * 
         * @return
         */
        public int getSampleDamage();

        /**
         * The damage to be applied to the extractor once the sample is
         * collected.
         * 
         * @return
         */
        public int getExtractorDamage();

    }

    /**
     * This interface should be used alongside {@link IEntityDescriptor} for
     * entities that can have their own internal nanites, which would be used
     * for research.
     * 
     * There is no need to use {@link IEntitySample} with this.
     * 
     * @see IEntitySample
     * @author ollieread
     *
     */
    public static interface IEntityResearchNanites extends IEntitySample
    {

        /**
         * The maximum amount of nanites the entity can have.
         * 
         * @return
         */
        public int getMaxNanites();

        /**
         * The regen modifier when nanites are depleted.
         * 
         * @return
         */
        public float getNaniteRegen();

        /**
         * The amount of ticks between nanite regeneration.
         * 
         * @return
         */
        public int getNaniteRegenTicks();

        /**
         * The maximum amount of data the entity can hold.
         * 
         * @return
         */
        public int getMaxData();

    }

    /**
     * This interface should be used alongside {@link IEntityDescriptor} for
     * entities that have a robotic counterpart.
     * 
     * @author ollieread
     *
     */
    public static interface IEntityRobot
    {

        /**
         * The class for the robot entity, must extend {@link EntityRobot}.
         * 
         * @return
         */
        public Class<? extends EntityRobot> getRobotClass();

    }

}
