package net.telepathicgrunt.bumblezone.configs;

import me.sargunvohra.mcmods.autoconfig1u.ConfigData;
import me.sargunvohra.mcmods.autoconfig1u.annotation.Config;
import me.sargunvohra.mcmods.autoconfig1u.annotation.ConfigEntry;
import me.sargunvohra.mcmods.autoconfig1u.shadowed.blue.endless.jankson.Comment;

@Config(name = "block_mechanics")
public class BZBlockMechanicsConfig implements ConfigData {

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = "\nShould Dispensers always drop the Glass Bottle when using specific \n"
            +"bottle items on certain The Bumblezone blocks? \n"
            +"\n"
            +"Example: Using Honey Bottle to feed Honeycomb Brood Blocks will grow the larva and \n"
            +"drop the Glass Bottle instead of putting it back into Dispenser if this is set to true.")
    public boolean dispensersDropGlassBottles = false;

    @ConfigEntry.Gui.PrefixText
    @ConfigEntry.Gui.Tooltip(count = 0)
    @Comment(value = "\nBrood Blocks will automatically spawn bees until the number of active bees is the value below. \n" +
                    "Set this higher to allow Brood Blocks to spawn more bees in a smaller area or set it to 0 to turn \n" +
                    "off automatic Brood Block bee spawning.")
    @ConfigEntry.BoundedDiscrete(min = 0, max = 1000)
    public int broodBlocksBeeSpawnCapacity = 80;
}
