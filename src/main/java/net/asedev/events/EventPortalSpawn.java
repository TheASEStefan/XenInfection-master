package net.asedev.events;

import net.asedev.registry.RegPortalSpawning;
import net.asedev.xenmod.Embedded;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Embedded.MOD_ID)
public class EventPortalSpawn
{
    private static final Map<ResourceLocation, RegPortalSpawning> portals = new HashMap<>();

    @SubscribeEvent
    public static void onServerStart(ServerStartingEvent event)
    {
        MinecraftServer server = event.getServer();
        portals.put(Level.OVERWORLD.location(), new RegPortalSpawning(server));
    }

    @SubscribeEvent
    public static void onServerStopped(ServerStoppedEvent event)
    {
        portals.clear();
    }

    @SubscribeEvent
    public static void onWorldTick(TickEvent.LevelTickEvent event)
    {
        if (event.phase != TickEvent.Phase.START)
            return;

        if (event.side != LogicalSide.SERVER)
            return;

        RegPortalSpawning portalSpawning = portals.get(event.level.dimension().location());
        if (portalSpawning != null)
        {
            portalSpawning.tick(event.level);
        }
    }
}
