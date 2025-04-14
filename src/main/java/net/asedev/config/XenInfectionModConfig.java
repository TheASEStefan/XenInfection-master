package net.asedev.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.util.List;

/**
 * @Author = ASEStefan
 */
public class XenInfectionModConfig
{
    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;
    public static final DataGen DATAGEN;
    public static final ForgeConfigSpec DATAGEN_SPEC;

    static
    {

        Pair<Server, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER = commonSpecPair.getLeft();
        SERVER_SPEC = commonSpecPair.getRight();

        Pair<DataGen , ForgeConfigSpec> commonPair = new ForgeConfigSpec.Builder().configure(DataGen::new);
        DATAGEN = commonPair.getLeft();
        DATAGEN_SPEC = commonPair.getRight();

    }

    public static class Server
    {

        /* Targeting */
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> blacklist;
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> attackers_list;
        public final ForgeConfigSpec.ConfigValue<Boolean> villagers_running_from_xens;

        /* Portals */
        public final ForgeConfigSpec.ConfigValue<Integer> maximumPortals;
        public final ForgeConfigSpec.ConfigValue<Integer> ticksBeforePortalSpawning;
        public final ForgeConfigSpec.ConfigValue<Integer> additionalRandomizedTicks;

        /* Headcrabs */
        public final ForgeConfigSpec.ConfigValue<Double> headcrab_health;
        public final ForgeConfigSpec.ConfigValue<Double> headcrab_damage;

        public Server(ForgeConfigSpec.Builder builder)
        {
            builder.push("Headcrab");
            this.headcrab_health = builder.comment("Default 10").defineInRange("Sets Base Form's Max health", 10, 5, Double.MAX_VALUE);
            this.headcrab_damage = builder.comment("Default 8").defineInRange("Sets Base Form's Damage", 8, 3, Double.MAX_VALUE);
            builder.pop();

            builder.push("Targeting Tasks");
            this.blacklist = builder.defineList("Mobs Not Targeted",
                    Lists.newArrayList(
                            "minecraft:squid", "minecraft:bat", "minecraft:armor_stand", "minecraft:creeper", "minecraft:ghast", "minecraft:falling_block", "minecraft:abstract_arrow", "minecraft:arrow", "minecraft:spectral_arrow", "minecraft:trident") , o -> o instanceof String);

            this.attackers_list = builder.defineList("Mobs that TARGET xen forms",
                    Lists.newArrayList(
                            "minecraft:zombie", "minecraft:skeleton", "minecraft:iron_golem", "minecraft:spider", "minecraft:cave_spider", "minecraft:witch", "minecraft:drowned", "minecraft:husk", "minecraft:zombie_villager", "minecraft:stray", "minecraft:pillager", "minecraft:enderman", "minecraft:evoker", "minecraft:vindicator", "minecraft:ravager", "minecraft:vex") , o -> o instanceof String);

            this.villagers_running_from_xens = builder.comment("Default true").define("If true, villagers and wandering traders will run from the xens", true);

            builder.pop();

            builder.push("Portals");
            this.maximumPortals = builder.comment("Default 5").define("Maximum numbers of portals spawning at once",5);
            this.ticksBeforePortalSpawning = builder.comment("Default 2200").define("Ticks before the portals can spawn again",2200);
            this.additionalRandomizedTicks = builder.comment("Default 400").define("Additional portal spawning ticks",400);
            builder.pop();

        }
    }
    public static class DataGen
    {

        public DataGen(ForgeConfigSpec.Builder builder)
        {

        }

    }

    public static void loadConfig(ForgeConfigSpec config, String path)
    {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave()
                .writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
    }
}