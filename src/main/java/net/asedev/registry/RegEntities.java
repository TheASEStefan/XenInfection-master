package net.asedev.registry;

import net.asedev.entities.generic.HeadcrabEntity;
import net.asedev.entities.special.XenPortal;
import net.asedev.xenmod.Embedded;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author = ASEStefan
 */
public class RegEntities
{

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Embedded.MOD_ID);

    public static final MobCategory XEN = MobCategory.create("xen","xen", 65,false,false,400);

    public static final List<Entity> XEN_SPECIES = new ArrayList<>();

    public static final RegistryObject<EntityType<HeadcrabEntity>> HEADCRAB =
            ENTITY_TYPES.register("headcrab",
                    () -> EntityType.Builder.of(HeadcrabEntity::new, XEN)
                            .sized(1.05f, 0.95f)
                            .build(new ResourceLocation(Embedded.MOD_ID, "headcrab").toString()));

    public static final RegistryObject<EntityType<XenPortal>> XEN_PORTAL =
            ENTITY_TYPES.register("xen_portal",
                    () -> EntityType.Builder.<XenPortal>of(XenPortal::new, MobCategory.MISC)
                            .fireImmune()
                            .sized(1.0F, 1.0F)
                            .setUpdateInterval(Integer.MAX_VALUE)
                            .build(new ResourceLocation(Embedded.MOD_ID, "xen_portal").toString()));

    public static void register(IEventBus eventBus)
    {
        ENTITY_TYPES.register(eventBus);
    }
}
