package fr.xalnox.skinfixer.mixin;

import net.minecraft.client.render.entity.model.BiPedModel;
import net.minecraft.client.render.model.ModelPart;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BiPedModel.class)
public class BipedModelMixin {
    private ModelPart leftSleeve;
    private ModelPart rightSleeve;
    private ModelPart leftPants;
    private ModelPart rightPants;
    private ModelPart jacket;

    @Shadow public ModelPart body;
    @Shadow public ModelPart field_1476; // right arm
    @Shadow public ModelPart field_1477; // left arm
    @Shadow public ModelPart field_1478; // right leg
    @Shadow public ModelPart field_1479; // left leg

    @Redirect(method = "<init>(FFII)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/model/BiPedModel;textureHeight:I"))
    public void setTextureHeight(BiPedModel instance, int value) {
        instance.textureHeight = 64;
    }

    // Make sure the signature types/order match vanilla: (float f, float g, int i, int j)
    @Inject(method = "<init>(FFII)V", at = @At("TAIL"))
    public void initModel(float f, float g, int i, int j, CallbackInfo ci) {
        // f = padding/reduction used for addCuboid
        // left sleeve (mirrored)
        this.leftSleeve = new ModelPart((BiPedModel)(Object)this, 48, 48);
        this.leftSleeve.mirror = true;
        this.leftSleeve.setPivot(5.0F, 2.0F + g, 0.0F);
        this.leftSleeve.addCuboid(-1.0F, -2.0F, -2.0F, 4, 12, 4, f + 0.25F);
        // right sleeve
        this.rightSleeve = new ModelPart((BiPedModel)(Object)this, 40, 32);
        this.rightSleeve.setPivot(-5.0F, 2.0F + g, 0.0F);
        this.rightSleeve.addCuboid(-3.0F, -2.0F, -2.0F, 4, 12, 4, f + 0.25F);

        // left pants (mirrored)
        this.leftPants = new ModelPart((BiPedModel)(Object)this, 0, 48);
        this.leftPants.mirror = true;
        this.leftPants.setPivot(1.9F, 12.0F + g, 0.0F);
        this.leftPants.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, f + 0.25F);

        // right pants
        this.rightPants = new ModelPart((BiPedModel)(Object)this, 0, 32);
        this.rightPants.setPivot(-1.9F, 12.0F + g, 0.0F);
        this.rightPants.addCuboid(-2.0F, 0.0F, -2.0F, 4, 12, 4, f + 0.25F);

        // jacket: pivot at body origin (same +g offset as body)
        this.jacket = new ModelPart((BiPedModel)(Object)this, 16, 32);
        this.jacket.setPivot(0.0F, 0.0F + g, 0.0F);
        this.jacket.addCuboid(-4.0F, 0.0F, -2.0F, 8, 12, 4, f + 0.25F);

        this.leftSleeve.setTextureSize(64,64);
        this.rightSleeve.setTextureSize(64,64);
        this.leftPants.setTextureSize(64,64);
        this.rightPants.setTextureSize(64,64);
        this.jacket.setTextureSize(64,64);
        this.body.setTextureSize(64,64);
        this.field_1476.setTextureSize(64,64);
        this.field_1477.setTextureSize(64,64);
        this.field_1478.setTextureSize(64,64);
        this.field_1479.setTextureSize(64,64);

    }

    @Inject(method = "setAngles", at = @At("TAIL"))
    private void syncExtraParts(float handSwing, float handSwingAmount, float tickDelta, float age, float headPitch, float scale, Entity entity, CallbackInfo ci) {
        copyModelPartTransform(this.field_1477, this.leftSleeve);
        copyModelPartTransform(this.field_1476, this.rightSleeve);
        copyModelPartTransform(this.field_1479, this.leftPants);
        copyModelPartTransform(this.field_1478, this.rightPants);
        copyModelPartTransform(this.body, this.jacket);
    }

    @Unique
    private void copyModelPartTransform(ModelPart src, ModelPart dst) {
        if (src == null || dst == null) return;
        dst.pivotX = src.pivotX;
        dst.pivotY = src.pivotY;
        dst.pivotZ = src.pivotZ;

        dst.posX = src.posX;
        dst.posY = src.posY;
        dst.posZ = src.posZ;

        dst.offsetX = src.offsetX;
        dst.offsetY = src.offsetY;
        dst.offsetZ = src.offsetZ;

        dst.visible = src.visible;
        dst.hide = src.hide;
        dst.mirror = src.mirror;
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/model/ModelPart;render(F)V",
                    shift = At.Shift.AFTER,
                    ordinal = 6
            )
    )
    public void onSkinRender(Entity entity, float handSwing, float handSwingAmount, float tickDelta, float age, float headPitch, float scale, CallbackInfo ci) {
        this.leftSleeve.render(scale);
        this.rightSleeve.render(scale);
        this.leftPants.render(scale);
        this.rightPants.render(scale);
        this.jacket.render(scale);
    }

    @Inject(
            method = "render",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/model/ModelPart;render(F)V",
                    shift = At.Shift.AFTER,
                    ordinal = 13
            )
    )
    public void onChildSkinRender(Entity entity, float handSwing, float handSwingAmount, float tickDelta, float age, float headPitch, float scale, CallbackInfo ci) {
        this.leftSleeve.render(scale);
        this.rightSleeve.render(scale);
        this.leftPants.render(scale);
        this.rightPants.render(scale);
        this.jacket.render(scale);
    }
}
