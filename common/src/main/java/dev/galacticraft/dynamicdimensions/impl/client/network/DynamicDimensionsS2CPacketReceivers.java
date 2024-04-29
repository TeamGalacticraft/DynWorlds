/*
 * Copyright (c) 2021-2024 Team Galacticraft
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

package dev.galacticraft.dynamicdimensions.impl.client.network;

import dev.galacticraft.dynamicdimensions.impl.Constants;
import dev.galacticraft.dynamicdimensions.impl.registry.RegistryUtil;
import lol.bai.badpackets.api.play.ClientPlayContext;
import lol.bai.badpackets.api.play.PlayPackets;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.dimension.DimensionType;

public final class DynamicDimensionsS2CPacketReceivers {
    public static void registerReceivers() {
        PlayPackets.registerClientChannel(Constants.CREATE_WORLD_PACKET);
        PlayPackets.registerClientChannel(Constants.DELETE_WORLD_PACKET);
        PlayPackets.registerClientReceiver(Constants.CREATE_WORLD_PACKET, DynamicDimensionsS2CPacketReceivers::createDynamicWorld);
        PlayPackets.registerClientReceiver(Constants.DELETE_WORLD_PACKET, DynamicDimensionsS2CPacketReceivers::deleteDynamicWorld);
    }

    private static void createDynamicWorld(ClientPlayContext context, FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        DimensionType type = DimensionType.DIRECT_CODEC.decode(NbtOps.INSTANCE, buf.readNbt()).getOrThrow().getFirst();
        context.client().execute(() -> {
            RegistryUtil.registerUnfreezeExact(context.handler().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE), id, type);
            context.handler().levels().add(ResourceKey.create(Registries.DIMENSION, id));
        });
    }

    private static void deleteDynamicWorld(ClientPlayContext context, FriendlyByteBuf buf) {
        ResourceLocation id = buf.readResourceLocation();
        context.client().execute(() -> {
            RegistryUtil.unregister(context.handler().registryAccess().registryOrThrow(Registries.DIMENSION_TYPE), id);
            context.handler().levels().remove(ResourceKey.create(Registries.DIMENSION, id));
        });
    }

}
