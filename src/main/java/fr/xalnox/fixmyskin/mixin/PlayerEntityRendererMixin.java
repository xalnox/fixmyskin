package fr.xalnox.fixmyskin.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import fr.xalnox.fixmyskin.interfaces.LayerSkin;
import fr.xalnox.fixmyskin.interfaces.SlimPlayerHolder;
import fr.xalnox.fixmyskin.interfaces.SlimPlayerModelHolder;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BiPedModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.player.PlayerEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin implements SlimPlayerModelHolder {
    @Unique private BiPedModel slimArmorModel1;
    @Unique private BiPedModel slimArmorModel;
    @Unique private BiPedModel slimPlayerModel;
    @Inject(method = "<init>", at = @At("TAIL"))
    public void onBipedModelConstruct(CallbackInfo ci) {
        this.slimArmorModel1 = new BiPedModel(0.5f);
        this.slimArmorModel = new BiPedModel(1f);
        this.slimPlayerModel = new BiPedModel(0f);
        //((LayerSkin)this.slimArmorModel1).skinFixer$setSlim();
        //((LayerSkin)this.slimArmorModel).skinFixer$setSlim();
        ((LayerSkin)this.slimPlayerModel).skinFixer$setSlim();
    }

    @Override
    public BiPedModel skinFixer$getSlimModel() {
        return slimPlayerModel;
    }

    @Inject(method = "method_1564(Lnet/minecraft/entity/player/PlayerEntity;IF)I",at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;method_1556(Lnet/minecraft/client/render/entity/model/EntityModel;)V"))
    public void onModelQuery(PlayerEntity i, int f, float par3, CallbackInfoReturnable<Integer> cir, @Local LocalRef<BiPedModel> var7) {
        if (((SlimPlayerHolder)i).skinFixer$isSlim()) {
            var7.set(f == 2 ? this.slimArmorModel1 : this.slimArmorModel);
            resetVisibility(var7.get());
            var7.get().head.visible = f == 0;
            var7.get().hat.visible = f == 0;
            var7.get().body.visible = f == 1 || f == 2;
            var7.get().field_1476.visible = f == 1;
            var7.get().field_1477.visible = f == 1;
            var7.get().field_1478.visible = f == 2 || f == 3;
            var7.get().field_1479.visible = f == 2 || f == 3;
        }
    }


    @Redirect(method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;field_2134:Lnet/minecraft/client/render/entity/model/BiPedModel;", opcode = Opcodes.GETFIELD))
    public BiPedModel onModelGet(PlayerEntityRenderer instance,PlayerEntity playerEntity) {
        if (((SlimPlayerHolder)playerEntity).skinFixer$isSlim()) {
            return this.slimArmorModel1;
        }
        return instance.field_2134;
    }

    @Redirect(method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;field_2135:Lnet/minecraft/client/render/entity/model/BiPedModel;", opcode = Opcodes.GETFIELD))
    public BiPedModel onAnotherModelGet(PlayerEntityRenderer instance,PlayerEntity playerEntity) {
        if (((SlimPlayerHolder)playerEntity).skinFixer$isSlim()) {
            return this.slimArmorModel;
        }
        return instance.field_2135;
    }

    @Redirect(method = "render(Lnet/minecraft/entity/player/PlayerEntity;DDDFF)V",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;field_2133:Lnet/minecraft/client/render/entity/model/BiPedModel;", opcode = Opcodes.GETFIELD))
    public BiPedModel onAnotherAnotherModelGet(PlayerEntityRenderer instance,PlayerEntity playerEntity) {
        if (((SlimPlayerHolder)playerEntity).skinFixer$isSlim()) {
            return this.slimPlayerModel;
        }
        return instance.field_2133;
    }

    @Redirect(method = "method_1569(Lnet/minecraft/entity/player/PlayerEntity;F)V",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;field_2133:Lnet/minecraft/client/render/entity/model/BiPedModel;", opcode = Opcodes.GETFIELD))
    public BiPedModel onBasicModelGet(PlayerEntityRenderer instance,PlayerEntity playerEntity) {
        if (((SlimPlayerHolder)playerEntity).skinFixer$isSlim()) {
            return this.slimPlayerModel;
        }
        return instance.field_2133;
    }


    @Redirect(method = "method_4339",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;field_2133:Lnet/minecraft/client/render/entity/model/BiPedModel;", opcode = Opcodes.GETFIELD))
    public BiPedModel onModelGetNoIdea(PlayerEntityRenderer instance,PlayerEntity playerEntity) {
        if (((SlimPlayerHolder)playerEntity).skinFixer$isSlim()) {
            return this.slimPlayerModel;
        }
        return instance.field_2133;
    }

    @Redirect(method = "method_1564(Lnet/minecraft/entity/player/PlayerEntity;IF)I",at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/entity/PlayerEntityRenderer;field_2130:Lnet/minecraft/client/render/entity/model/EntityModel;", opcode = Opcodes.GETFIELD))
    public EntityModel onModelGetNoIdea2(PlayerEntityRenderer instance, PlayerEntity playerEntity) {
        if (((SlimPlayerHolder)playerEntity).skinFixer$isSlim()) {
            return this.slimPlayerModel;
        }
        return instance.field_2133;
    }
    @Unique
    private void resetVisibility(BiPedModel model) {
        model.head.visible = false;
        model.hat.visible = false;
        model.body.visible = false;
        model.field_1476.visible = false;
        model.field_1477.visible = false;
        model.field_1478.visible = false;
        model.field_1479.visible = false;
    }
}
