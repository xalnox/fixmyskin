package fr.xalnox.fixmyskin.mixin;

import fr.xalnox.fixmyskin.interfaces.SlimPlayerHolder;
import fr.xalnox.fixmyskin.interfaces.SlimPlayerModelHolder;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MobEntityRenderer.class)
public class MobEntityRendererMixin {

    @Redirect(method = "render(Lnet/minecraft/entity/mob/MobEntity;DDDFF)V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;field_2130:Lnet/minecraft/client/render/entity/model/EntityModel;", opcode = Opcodes.GETFIELD))
    public EntityModel onModelGet(MobEntityRenderer instance, MobEntity d, double e, double f, double g, float h, float par6) {
        if (d instanceof PlayerEntity && instance instanceof PlayerEntityRenderer && ((SlimPlayerHolder) d).skinFixer$isSlim()) {
            return ((SlimPlayerModelHolder)instance).skinFixer$getSlimModel();
        }
        return instance.field_2130;
    }

    @Redirect(method = "method_1563", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;field_2130:Lnet/minecraft/client/render/entity/model/EntityModel;", opcode = Opcodes.GETFIELD))
    public EntityModel onModelAnotherGet(MobEntityRenderer instance,MobEntity d) {
        if (d instanceof PlayerEntity && instance instanceof PlayerEntityRenderer && ((SlimPlayerHolder) d).skinFixer$isSlim()) {
            return ((SlimPlayerModelHolder)instance).skinFixer$getSlimModel();
        }
        return instance.field_2130;
    }

    @Redirect(method = "method_4338", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;field_2130:Lnet/minecraft/client/render/entity/model/EntityModel;", opcode = Opcodes.GETFIELD))
    public EntityModel onModelAnotherAnotherGet(MobEntityRenderer instance,MobEntity d) {
        if (d instanceof PlayerEntity && instance instanceof PlayerEntityRenderer && ((SlimPlayerHolder) d).skinFixer$isSlim()) {
            return ((SlimPlayerModelHolder)instance).skinFixer$getSlimModel();
        }
        return instance.field_2130;
    }


}
