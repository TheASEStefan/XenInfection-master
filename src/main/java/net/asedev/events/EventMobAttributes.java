package net.asedev.events;

import net.asedev.entities.generic.HeadcrabEntity;
import net.asedev.registry.RegEntities;
import net.asedev.xenmod.Embedded;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * @Author = ASEStefan
 */

@Mod.EventBusSubscriber(modid = Embedded.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventMobAttributes
{

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event)
    {
        event.put(RegEntities.HEADCRAB.get(), HeadcrabEntity.createAttributes().build());
    }

}
