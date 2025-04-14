package net.asedev.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

/**
 * @Author = ASEStefan
 */
public class EmptyOverlaySpawnEgg extends ForgeSpawnEggItem
{

    public EmptyOverlaySpawnEgg(Supplier<? extends EntityType<? extends Mob>> type)
    {
        super(type, -1, -1, new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag)
    {
        super.appendHoverText(stack, level, components, flag);
    }
}
