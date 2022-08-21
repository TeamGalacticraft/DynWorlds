/*
 * Copyright (c) 2021-2022 Team Galacticraft
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package dev.galacticraft.dyndims.impl.mixin;

import com.mojang.datafixers.DataFixer;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.Lifecycle;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.levelgen.WorldGenSettings;
import net.minecraft.world.level.storage.LevelStorageSource;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LevelStorageSource.class)
public abstract class LevelStorageSourceMixin {
    @Inject(method = "readWorldGenSettings", at = @At("HEAD"))
    private static <T> void readDynamicDimensions(@NotNull Dynamic<T> dynamic, DataFixer dataFixer, int i, CallbackInfoReturnable<Pair<WorldGenSettings, Lifecycle>> cir) {
        if (dynamic.get("DynamicDimensions").result().isPresent()) {
            if (dynamic.getOps() instanceof RegistryOps<T> registryOps) {
                Map<ResourceLocation, DimensionType> map = dynamic.get("DynamicDimensions").asMap(e -> new ResourceLocation(e.asString().get().orThrow()), e -> e.get("dimension_type").decode(DimensionType.DIRECT_CODEC).get().orThrow().getFirst());
                for (Map.Entry<ResourceLocation, DimensionType> entry : map.entrySet()) {
                    ((MappedRegistry<DimensionType>) registryOps.registry(Registry.DIMENSION_TYPE_REGISTRY).get()).register(ResourceKey.create(Registry.DIMENSION_TYPE_REGISTRY, entry.getKey()), entry.getValue(), Lifecycle.stable());
                }
            }
        }
    }
}
