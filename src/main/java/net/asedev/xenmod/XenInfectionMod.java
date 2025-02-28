package net.asedev.xenmod;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.asedev.config.XenInfectionModConfig;
import software.bernie.geckolib.GeckoLib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @Author = ASEStefan
 */
@Mod(XenInfectionMod.MOD_ID)
public class XenInfectionMod
{
    public static final String MOD_ID = "xenmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public XenInfectionMod()
    {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, XenInfectionModConfig.DATAGEN_SPEC ,"xenmoddata.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, XenInfectionModConfig.SERVER_SPEC ,"xenmodconfig.toml");
        XenInfectionModConfig.loadConfig(XenInfectionModConfig.SERVER_SPEC, FMLPaths.CONFIGDIR.get().resolve("xenmodconfig.toml").toString());

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(this);


        GeckoLib.initialize();

        modEventBus.addListener(this::commonSetup);

        // RegCreativeTab.register(modEventBus);



    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {


    }

}