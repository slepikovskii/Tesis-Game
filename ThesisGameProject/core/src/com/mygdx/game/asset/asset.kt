package com.mygdx.game.asset

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas

enum class TextureAtlasAsset(
        val isSkinAtlas: Boolean,
        fileName: String,
        directory: String = "graphics",
        val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor("$directory/$fileName", TextureAtlas::class.java)
) {
    GRAPHICS(false, "graphics.atlas"),
    UI(true, "ui.atlas", "ui")
}

enum class TextureAsset(
        fileName: String,
        directory: String = "graphics",
        val descriptor: AssetDescriptor<Texture> = AssetDescriptor("$directory/$fileName", Texture::class.java)
) {
    BACKGROUND("background.png")
}