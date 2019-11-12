package com.backdoored.extensions;

import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.gui.FontRenderer;

public class FontRendererWrapper extends FontRenderer
{
    public FontRendererWrapper(final GameSettings gameSettingsIn, final ResourceLocation location, final TextureManager textureManagerIn, final boolean unicode) {
        super(gameSettingsIn, location, textureManagerIn, unicode);
    }
}
