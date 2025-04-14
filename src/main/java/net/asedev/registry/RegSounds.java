package net.asedev.registry;

import net.asedev.xenmod.Embedded;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @Author = ASEStefan
 */
@EventBusSubscriber(modid = Embedded.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class RegSounds
{
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Embedded.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        SOUNDS.register(eventBus);
    }

    private static RegistryObject<SoundEvent> soundRegistry(String id)
    {
        return SOUNDS.register(id, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Embedded.MOD_ID, id)));
    }

    public static final RegistryObject<SoundEvent> BEAMSTART = soundRegistry("beamstart");

    public static final RegistryObject<SoundEvent> HEADCRAB_ATTACK = soundRegistry("hc_attack");
    public static final RegistryObject<SoundEvent> HEADCRAB_DIE = soundRegistry("hc_die");
    public static final RegistryObject<SoundEvent> HEADCRAB_HEADBITE = soundRegistry("hc_headbite");
    public static final RegistryObject<SoundEvent> HEADCRAB_IDLE = soundRegistry("hc_idle");
    public static final RegistryObject<SoundEvent> HEADCRAB_PAIN = soundRegistry("hc_pain");
}
