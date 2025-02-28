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

        public Server(ForgeConfigSpec.Builder builder)
        {

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