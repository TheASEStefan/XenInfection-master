package net.asedev.registry;

import net.asedev.xenmod.Embedded;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.asedev.xenmod.XenInfectionMod;


/**
 * @Author = ASEStefan
 */
public class RegCreativeTab
{

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Embedded.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEM = TABS.register("items", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup." + Embedded.MOD_ID + ".item")).icon(() -> new ItemStack(RegItems.HEADCRAB_SPAWN_EGG.get())).displayItems((enabledFeatures, entries) ->
    {
        entries.accept(RegItems.HEADCRAB_SPAWN_EGG.get());

    }).build());

    public static void register(IEventBus eventBus)
    {
        TABS.register(eventBus);
    }

}

