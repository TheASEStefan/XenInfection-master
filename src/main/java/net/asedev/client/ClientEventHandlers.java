package net.asedev.client;

import net.asedev.client.renderer.HeadcrabRenderer;
import net.asedev.client.renderer.XenPortalRenderer;
import net.asedev.registry.RegEntities;
import net.asedev.xenmod.Embedded;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @Author = ASEStefan
 */
@Mod.EventBusSubscriber(modid = Embedded.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientEventHandlers
{

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(RegEntities.XEN_PORTAL.get(), XenPortalRenderer::new);
        event.registerEntityRenderer(RegEntities.HEADCRAB.get(), HeadcrabRenderer::new);
    }

}
