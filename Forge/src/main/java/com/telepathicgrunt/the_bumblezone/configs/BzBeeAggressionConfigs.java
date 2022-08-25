package com.telepathicgrunt.the_bumblezone.configs;

import net.minecraftforge.common.ForgeConfigSpec;

public class BzBeeAggressionConfigs {
    public static final ForgeConfigSpec GENERAL_SPEC;

    // bee aggression
    public static ForgeConfigSpec.BooleanValue beehemothTriggersWrath;
    public static ForgeConfigSpec.BooleanValue allowWrathOfTheHiveOutsideBumblezone;
    public static ForgeConfigSpec.BooleanValue showWrathOfTheHiveParticles;
    public static ForgeConfigSpec.BooleanValue aggressiveBees;
    public static ForgeConfigSpec.IntValue aggressionTriggerRadius;
    public static ForgeConfigSpec.IntValue howLongWrathOfTheHiveLasts;
    public static ForgeConfigSpec.IntValue howLongProtectionOfTheHiveLasts;
    public static ForgeConfigSpec.IntValue speedBoostLevel;
    public static ForgeConfigSpec.IntValue absorptionBoostLevel;
    public static ForgeConfigSpec.IntValue strengthBoostLevel;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }

    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.push("Wrath of the Hive Options");

            builder.push("Bees Aggression Options");

                beehemothTriggersWrath = builder
                    .comment(" \n-----------------------------------------------------\n",
                            " If set to true, any entity that harms a beehemoth and is not owner of it,",
                            " that entity will get Wrath of the Hive effect.")
                    .translation("the_bumblezone.config.beehemothTriggersWrath")
                    .define("beehemothTriggersWrath", false);

                allowWrathOfTheHiveOutsideBumblezone = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " Determines if Wrath of the Hive can be applied to players outside",
                   " the Bumblezone dimension when they pick up Honey blocks, take honey",
                   " from Filled Porous Honey blocks, or drink Honey Bottles.\n")
                    .translation("the_bumblezone.config.allowwrathofthehiveoutsidebumblezone")
                    .define("allowWrathOfTheHiveOutsideBumblezone", false);


                showWrathOfTheHiveParticles = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " Show the orangish particles when you get Wrath of the Hive",
                   " after you angered the bees in the Bumblezone dimension.\n")
                    .translation("the_bumblezone.config.showwrathofthehiveparticles")
                    .define("showWrathOfTheHiveParticles", true);


                aggressiveBees = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " Turn off or on the ability to get Wrath of the Hive effect.",
                   " ",
                   " The bees can see you through walls and will have ",
                   " speed, absorption, and strength effects applied to them.",
                   " ",
                   " Will also affect the bee's aggression toward other mobs in the dimension.",
                   " Note: Peaceful mode will always override the bee aggressive setting.\n")
                    .translation("the_bumblezone.config.aggressivebees")
                    .define("aggressiveBees", true);


                aggressionTriggerRadius = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " How far away the bee can be to become angry and hunt you down if\n "
                    +" you get Wrath of the Hive effect in the Bumblezone dimension.",
                   " ",
                   " Will also affect the bee's aggression range toward other mobs in the dimension.\n")
                    .translation("the_bumblezone.config.aggressiontriggerradius")
                    .defineInRange("aggressionTriggerRadius", 64, 1, 200);


                howLongWrathOfTheHiveLasts = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " How long bees will keep their effects for (speed, absorption, strength).",
                   " Note: This is in ticks. 20 ticks = 1 second. And bee's normal anger will remain.\n")
                    .translation("the_bumblezone.config.howlongwrathofthehivelasts")
                    .defineInRange("howLongWrathOfTheHiveLasts", 1680, 1, Integer.MAX_VALUE);


                howLongProtectionOfTheHiveLasts = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " How long entities will keep Protection of the Hive effect after feeding bees",
                   " or Brood Blocks. Bees will attack anyone that damages someone with the effect.\n")
                    .translation("the_bumblezone.config.howLongProtectionOfTheHiveLasts")
                    .defineInRange("howLongProtectionOfTheHiveLasts", 1680, 1, Integer.MAX_VALUE);



        builder.pop();

            builder.push("Bees Effects Options");


                speedBoostLevel = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " How fast bees move along the ground (Not while flying).",
                   " You will see this a lot when bees are about to attack",
                   " you, they tend to touch the floor and the speed boost",
                   " makes them dash forward at you. Set this to higher for",
                   " faster dash attacks from bees.\n")
                    .translation("the_bumblezone.config.speedboostlevel")
                    .defineInRange("speedBoostLevel", 2, 1, Integer.MAX_VALUE);


                absorptionBoostLevel = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " How much extra health bees get that always instantly regenerates.",
                   " This means you need to deal more damage than the extra health gives",
                   " order to actually damage the bee's real health bar.",
                   " ",
                   " For example, Absorption 1 here makes bees get 4 extra padding of health (2 full hearts).",
                   " Your attacks need to deal 4 1/2 or more damage to actually be able to",
                   " kill the bee. This means using Bane of Arthropod 5 is needed to kill bees",
                   " if you set the absorption to a higher value like 2 or 3.",
                   " If you set this to like 5 or something, bees may be invicible! Game over.\n")
                    .translation("the_bumblezone.config.absorptionboostlevel")
                    .defineInRange("absorptionBoostLevel", 1, 1, Integer.MAX_VALUE);


                strengthBoostLevel = builder
                    .comment(" \n-----------------------------------------------------\n",
                   " How strong the bees attacks become. ",
                   " (5 or higher will instant kill you without armor).\n")
                    .translation("the_bumblezone.config.strengthboostlevel")
                    .defineInRange("strengthBoostLevel", 2, 1, Integer.MAX_VALUE);

            builder.pop();
        builder.pop();
    }
}
