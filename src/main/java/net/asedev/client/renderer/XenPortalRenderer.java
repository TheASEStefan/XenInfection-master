package net.asedev.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.asedev.client.model.XenPortalModel;
import net.asedev.entities.special.XenPortal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class XenPortalRenderer extends GeoEntityRenderer<XenPortal>
{
    public XenPortalRenderer(EntityRendererProvider.Context renderManagerIn)
    {
        super(renderManagerIn, new XenPortalModel());
        shadowRadius = 0.0f;
    }

    @Override
    public void render(XenPortal entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        poseStack.pushPose();
        poseStack.scale(2.25F, 2.25F, 2.25F);
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
