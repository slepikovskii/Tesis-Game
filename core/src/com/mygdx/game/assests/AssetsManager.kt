package com.mygdx.game.assests

import com.badlogic.gdx.assets.AssetDescriptor
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.freetype.freeTypeFontParameters

enum class Textures(val path: String,
        val descriptor: AssetDescriptor<Texture> = AssetDescriptor(path, Texture::class.java)) {
    Example("background/Example on how the platform looks like.png")
}

enum class TextureAtlasAssets(val path: String, val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor(path,
        TextureAtlas::class.java)) {
    Buttons("UI/Buttons.atlas"),
    Scales("UI/Scales.atlas")
}

enum class Animations(val path: String,
        val descriptor: AssetDescriptor<TextureAtlas> = AssetDescriptor(path, TextureAtlas::class.java)) {
    Lvl1("animation/Animation.atlas")
}

enum class FontAsset(
        val path: String,
        val descriptor: AssetDescriptor<BitmapFont> = AssetDescriptor(
                path,
                BitmapFont::class.java,
                freeTypeFontParameters(path) {
                    genMipMaps=true
                    size=30
                    minFilter = Texture.TextureFilter.MipMapLinearLinear
                    magFilter = Texture.TextureFilter.Linear
                }
        )) {
    FONT_DEFAULT("fonts/LuckiestGuy.ttf")
}