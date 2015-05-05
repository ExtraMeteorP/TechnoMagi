package com.ollieread.technomagi.common.init;

import com.ollieread.technomagi.api.TechnomagiApi;
import com.ollieread.technomagi.api.ability.IAbilityCast;
import com.ollieread.technomagi.common.ability.cast.AbilityCastPhaseJump;
import com.ollieread.technomagi.common.ability.cast.AbilityCastRegenerate;
import com.ollieread.technomagi.common.ability.cast.AbilityCastShiftBlock;
import com.ollieread.technomagi.common.ability.cast.AbilityCastTiltBlock;
import com.ollieread.technomagi.common.ability.cast.AbilityNaniteExchange;
import com.ollieread.technomagi.util.ResourceHelper;

public class Abilities
{

    // public static IAbilityCast scan;
    public static IAbilityCast regenerate;
    public static IAbilityCast naniteFood;
    public static IAbilityCast shiftBlock;
    public static IAbilityCast tiltBlock;
    public static IAbilityCast phasejump;

    public static void init()
    {
        // scan = new AbilityCastScan("scan",
        // ResourceHelper.texture("abilities/scan.png"));
        regenerate = new AbilityCastRegenerate("regenerate", ResourceHelper.texture("abilities/regenerate.png"));
        naniteFood = new AbilityNaniteExchange("nanite_exchange");
        shiftBlock = new AbilityCastShiftBlock("shift_block", ResourceHelper.texture("abilities/shift_block.png"));
        tiltBlock = new AbilityCastTiltBlock("tilt_block", ResourceHelper.texture("abilities/tilt_block.png"));
        phasejump = new AbilityCastPhaseJump("phase_jump", ResourceHelper.texture("abilities/phase_jump.png"));

        // TechnomagiApi.ability().addCastableAbility(scan);
        TechnomagiApi.ability().addCastableAbility(regenerate);
        TechnomagiApi.ability().addCastableAbility(naniteFood);
        TechnomagiApi.ability().addCastableAbility(shiftBlock);
        TechnomagiApi.ability().addCastableAbility(tiltBlock);
        TechnomagiApi.ability().addCastableAbility(phasejump);
    }

}
