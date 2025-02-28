package net.asedev.client.gecko.utils;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.util.RenderUtils;

/**
 * @Author = ASEStefan
 */
public class GeckoRenderingUtils
{

    public static void translateItemToHand(LivingEntity animatable, GeoBone bone, PoseStack stack, VertexConsumer buffer, MultiBufferSource bufferIn, ResourceLocation textureLocation, float pDegrees, float pX, float pY, float pZ, int packedLight)
    {
        if (bone.getName().equals("item"))
        {
            stack.pushPose();
            RenderUtils.translateToPivotPoint(stack, bone);
            stack.mulPose(Axis.XP.rotationDegrees(pDegrees));
            stack.translate(pX, pY, pZ);
            ItemStack itemstack = animatable.getMainHandItem();
            if (!itemstack.isEmpty())
            {
                Minecraft.getInstance().getItemRenderer().renderStatic(itemstack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, stack, bufferIn, animatable.level(), 0);
            }
            stack.popPose();
            buffer = bufferIn.getBuffer(RenderType.entityCutoutNoCull(textureLocation));
        }
    }

    public static void translateItemToHand(LivingEntity animatable, GeoBone bone, PoseStack stack, VertexConsumer buffer, MultiBufferSource bufferIn, ResourceLocation textureLocation, float pDegrees, int packedLight)
    {
        translateItemToHand(animatable, bone, stack, buffer, bufferIn, textureLocation, pDegrees, 0.0F, 0.0F, -0.1F, packedLight);
    }
}
