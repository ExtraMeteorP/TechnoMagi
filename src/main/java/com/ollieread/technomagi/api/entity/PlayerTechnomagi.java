package com.ollieread.technomagi.api.entity;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Chance;
import com.ollieread.technomagi.api.event.KnowledgeEvent.Progress.Pre;
import com.ollieread.technomagi.api.event.TechnomagiHooks;
import com.ollieread.technomagi.api.knowledge.Knowledge;
import com.ollieread.technomagi.api.knowledge.PlayerKnowledge;
import com.ollieread.technomagi.api.knowledge.research.IResearch;
import com.ollieread.technomagi.api.nanites.PlayerNanites;
import com.ollieread.technomagi.api.specialisation.Specialisation;

public class PlayerTechnomagi implements IExtendedEntityProperties
{

    protected EntityPlayer player;
    protected Random rand = new Random();

    protected PlayerKnowledge playerKnowledge;
    protected PlayerNanites playerNanites;

    protected Specialisation specialisation;

    public PlayerTechnomagi(EntityPlayer player)
    {
        this.player = player;
        this.playerKnowledge = new PlayerKnowledge();
        this.playerNanites = new PlayerNanites();
    }

    public static final void register(EntityPlayer player)
    {
        player.registerExtendedProperties(TechnomagiApi.IDENT_PLAYER, new PlayerTechnomagi(player));
    }

    public static final PlayerTechnomagi get(EntityPlayer player)
    {
        return (PlayerTechnomagi) player.getExtendedProperties(TechnomagiApi.IDENT_PLAYER);
    }

    @Override
    public void init(Entity entity, World world)
    {

    }

    @Override
    public void saveNBTData(NBTTagCompound compound)
    {
        /**
         * Specialisation
         */
        if (this.specialisation != null) {
            compound.setString("Specialisation", this.specialisation.getName());
        }

        /**
         * Save the players knowledge data.
         */
        NBTTagCompound knowledgeCompound = new NBTTagCompound();
        playerKnowledge.saveNBTData(knowledgeCompound);
        compound.setTag("Knowledge", knowledgeCompound);

        /**
         * Save the players nanite data.
         */
        NBTTagCompound nanitesCompound = new NBTTagCompound();
        playerNanites.saveNBTData(nanitesCompound);
        compound.setTag("Nanites", nanitesCompound);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound)
    {
        /**
         * Specialisation
         */
        if (compound.hasKey("Specialisation")) {
            this.specialisation = TechnomagiApi.specialisation().getSpecialisation(compound.getString("Specialisation"));
        } else {
            this.specialisation = null;
        }

        /**
         * Load the players knowledge data.
         */
        playerKnowledge.loadNBTData(compound.getCompoundTag("Knowledge"));

        /**
         * Load the players nanite data.
         */
        playerKnowledge.loadNBTData(compound.getCompoundTag("Nanites"));
    }

    public PlayerKnowledge knowledge()
    {
        return playerKnowledge;
    }

    public PlayerNanites nanites()
    {
        return playerNanites;
    }

    public void setSpecialisation(Specialisation specialisation)
    {
        this.specialisation = specialisation;
    }

    public boolean hasSpecialised()
    {
        return specialisation != null;
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
     * @param player
     * @param research
     */
    public void performResearch(IResearch research, Knowledge knowledge)
    {
        // Verify that the knowledge and research is usable
        if (playerKnowledge.canDiscover(knowledge) && playerKnowledge.canResearch(research)) {
            int chance = research.getChance();

            // Create and post the chance event, allowing the chance of
            // research completing to be modified
            Chance chanceEvent = TechnomagiHooks.researchChance(player, research, research.getChance());
            if (rand.nextInt(chanceEvent.chance) == 0) {
                Pre pre = TechnomagiHooks.preKnowledgeProgress(player, research, playerKnowledge.getKnowledgeProgress(research.getKnowledge()), research.getProgress());

                if (nanites().canDecreaseNanites(research.getProgress()) && nanites().canIncreaseData(research.getProgress())) {
                    if (!pre.isCanceled()) {
                        int progress = playerKnowledge.addResearch(research, pre.modifier);

                        if (progress > 0) {
                            nanites().decreaseNanites(progress);
                            nanites().increaseData(progress);

                            if (playerKnowledge.addKnowledgeProgress(knowledge.getName(), progress)) {
                                TechnomagiHooks.postKnowledgeProgress(player, research, playerKnowledge.getKnowledgeProgress(research.getKnowledge()), research.getProgress());
                            }
                        }
                    }
                }
            }
        }
    }
}
