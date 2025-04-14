package net.asedev.registry;

import net.asedev.util.EmptyOverlaySpawnEgg;
import net.asedev.xenmod.Embedded;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * @Author = ASEStefan
 */
public class RegItems
{
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Embedded.MOD_ID);

    public static final RegistryObject<Item> HEADCRAB_SPAWN_EGG = ITEMS.register("headcrab_spawn_egg",
            () -> new ForgeSpawnEggItem(RegEntities.HEADCRAB, 0x84633e, 0x611010, defaultBuilder()));

    public static Item.Properties defaultBuilder()
    {
        return new Item.Properties();
    }

    public static Item.Properties unstackable()
    {
        return defaultBuilder().stacksTo(1).rarity(Rarity.RARE);
    }

    public static Item.Properties unstackableEpic()
    {
        return defaultBuilder().stacksTo(1).rarity(Rarity.EPIC);
    }

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
