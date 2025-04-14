package net.asedev.events;

import net.asedev.config.XenInfectionModConfig;
import net.asedev.entities.generic.HeadcrabEntity;
import net.asedev.xenmod.Embedded;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Embedded.MOD_ID)
public class EventSpawnModifications
{
    @SubscribeEvent()
    public static void addSpawn(EntityJoinLevelEvent event)
    {

        if (event.getEntity() != null && event.getEntity() instanceof AbstractVillager abstractVillager && event.getEntity().getEncodeId() != null && XenInfectionModConfig.SERVER.villagers_running_from_xens.get())
        {
            for (String string : getNamespace())
            {
                if (string.contains(event.getEntity().getEncodeId()))
                {
                    abstractVillager.goalSelector.addGoal(1, new AvoidEntityGoal<>(abstractVillager, HeadcrabEntity.class, 16.0F, 0.7F, 0.75F));
                }
            }
        }


        /** From spore https://github.com/Entity442/Spore_2.0_1.20.1/blob/master/src/main/java/com/Harbinger/Spore/sEvents/HandlerEvents.java */
        if (event.getEntity() != null && event.getEntity() instanceof PathfinderMob mob)
        {
            for (String string : XenInfectionModConfig.SERVER.attackers_list.get())
            {
                if (string.endsWith(":"))
                {
                    String[] mod = string.split(":");
                    String[] iterations = Objects.requireNonNull(mob.getEncodeId()).split(":");
                    if (Objects.equals(mod[0], iterations[0]))
                    {
                        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HeadcrabEntity.class, true));
                    }
                }

                else
                {
                    if (XenInfectionModConfig.SERVER.attackers_list.get().contains(mob.getEncodeId()))
                    {
                        mob.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(mob, HeadcrabEntity.class, false));
                    }
                }
            }
        }

    }

    public static List<String> getNamespace()
    {
        return List.of("minecraft:villager", "minecraft:wandering_trader");
    }
}
