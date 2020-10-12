package com.mygdx.game.assests

import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import ktx.assets.getAsset
import ktx.assets.load
import ktx.freetype.registerFreeTypeFontLoaders

fun initiateAssetManager(): AssetManager {
    val assetManager = AssetManager()
    // Calling registerFreeTypeFontLoaders is necessary in order to load TTF/OTF files:
    assetManager.registerFreeTypeFontLoaders()
    return assetManager
}

enum class Textures(val path: String) {
    Example("background/Example on how the platform looks like.png")
}

inline fun AssetManager.load(asset: Textures) = load<Texture>(asset.path)
inline operator fun AssetManager.get(asset: Textures) = getAsset<Texture>(asset.path)

enum class TextureAtlasAssets(val path: String) {
    Buttons("UI/Buttons.atlas"),
    Scales("UI/Scales.atlas")
}

inline fun AssetManager.load(asset: TextureAtlasAssets) = load<TextureAtlas>(asset.path)
inline operator fun AssetManager.get(asset: TextureAtlasAssets) = getAsset<TextureAtlas>(asset.path)