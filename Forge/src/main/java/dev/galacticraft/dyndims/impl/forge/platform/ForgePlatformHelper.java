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

package dev.galacticraft.dyndims.impl.forge.platform;

import dev.galacticraft.dyndims.impl.config.DynamicDimensionsConfig;
import dev.galacticraft.dyndims.impl.forge.config.DynamicDimensionsConfigImpl;
import dev.galacticraft.dyndims.impl.forge.registry.UnfrozenRegistryImpl;
import dev.galacticraft.dyndims.impl.platform.services.PlatformHelper;
import dev.galacticraft.dyndims.impl.registry.UnfrozenRegistry;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public final class ForgePlatformHelper implements PlatformHelper {
    @Override
    public @NotNull DynamicDimensionsConfig getConfig() {
        return DynamicDimensionsConfigImpl.INSTANCE;
    }

    @Override
    public @NotNull <T> UnfrozenRegistry<T> unfreezeRegistry(@NotNull Registry<T> registry) {
        return UnfrozenRegistryImpl.create(registry);
    }
}