package net.asedev.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.asedev.client.model.HeadcrabModel;
import net.asedev.client.model.XenPortalModel;
import net.asedev.entities.generic.HeadcrabEntity;
import net.asedev.entities.special.XenPortal;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class HeadcrabRenderer extends GeoEntityRenderer<HeadcrabEntity>
{
    public HeadcrabRenderer(EntityRendererProvider.Context renderManagerIn)
    {
        super(renderManagerIn, new HeadcrabModel());
        shadowRadius = 0.0f;
    }

    @Override
    public void render(HeadcrabEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight)
    {
        poseStack.pushPose();

        if (entity.isLatched() || entity.isPassenger())
        {
            poseStack.translate(0.0, -0.5, 0.0);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
        poseStack.popPose();
    }
}
