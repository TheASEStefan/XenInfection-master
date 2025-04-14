package net.asedev.registry;

import net.asedev.config.XenInfectionModConfig;
import net.asedev.entities.special.XenPortal;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;

public class RegPortalSpawning
{
    private int nextTick;

    public RegPortalSpawning(MinecraftServer server)
    {

    }

    public int tick(Level level)
    {
        RandomSource random = level.random;
        --this.nextTick;
        if (this.nextTick > 0)
        {
            return 0;
        }
        else
        {
            this.nextTick += XenInfectionModConfig.SERVER.ticksBeforePortalSpawning.get() +  XenInfectionModConfig.SERVER.additionalRandomizedTicks.get();
            if (level instanceof ServerLevel world)
            {
                if (random.nextInt(5) != 0)
                {
                    return 0;
                }
                else
                {
                    int j = level.players().size();
                    if (j < 1)
                    {
                        return 0;
                    }
                    else
                    {
                        Player player = level.players().get(random.nextInt(j));
                        if (player.isSpectator())
                        {
                            return 0;
                        }
                        else
                        {
                            int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                            int l = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                            BlockPos.MutableBlockPos blockpos$mutableblockpos = player.blockPosition().mutable().move(k, 0, l);
                            int i1 = 10;
                            if (!level.hasChunksAt(blockpos$mutableblockpos.getX() - 10, blockpos$mutableblockpos.getZ() - 10, blockpos$mutableblockpos.getX() + 10, blockpos$mutableblockpos.getZ() + 10))
                            {
                                return 0;
                            }
                            else
                            {
                                Holder<Biome> holder = level.getBiome(blockpos$mutableblockpos);
                                if (holder.is(BiomeTags.WITHOUT_PATROL_SPAWNS))
                                {
                                    return 0;
                                }
                                else
                                {
                                    int j1 = 0;
                                    int k1 = XenInfectionModConfig.SERVER.maximumPortals.get();
                                    int k2 = 2 + random.nextInt(0, k1 - 1);
                                    for (int l1 = 0; l1 < k2; ++l1)
                                    {
                                        ++j1;
                                        blockpos$mutableblockpos.setY(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos).getY());
                                        if (l1 == 0)
                                        {
                                            if (!this.spawnHordeEntity(level, blockpos$mutableblockpos))
                                            {
                                                break;
                                            }
                                        }
                                        else
                                        {
                                            this.spawnHordeEntity(level, blockpos$mutableblockpos);

                                            level.players().forEach(player1 ->
                                            {
                                                player1.displayClientMessage(Component.literal("The Xen have opened a portal!").withStyle(ChatFormatting.GREEN), true);
                                                player1.level().playSound(null, player1.blockPosition(), RegSounds.BEAMSTART.get(), SoundSource.MASTER, 1.2F, 1.0F);
                                            });
                                        }

                                        blockpos$mutableblockpos.setX(blockpos$mutableblockpos.getX() + random.nextInt(5) - random.nextInt(5));
                                        blockpos$mutableblockpos.setZ(blockpos$mutableblockpos.getZ() + random.nextInt(5) - random.nextInt(5));
                                    }

                                    return j1;
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                return 0;
            }
        }
    }

    private boolean spawnHordeEntity(Level level, BlockPos blockPos)
    {
        BlockState blockstate = level.getBlockState(blockPos);
        BlockState downState = level.getBlockState(blockPos.below());
        if (level.getLightEmission(blockPos) > 1)
        {
            return false;
        }
        else if (blockstate.getFluidState().is(Fluids.WATER) || downState.getFluidState().is(Fluids.WATER) )
        {
            return false;
        }
        else
        {
            XenPortal xenPortal = new XenPortal(RegEntities.XEN_PORTAL.get(), level);
            assert xenPortal != null;
            xenPortal.setPos(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            level.addFreshEntity(xenPortal);
            return true;
        }
    }
}
