package com.mygdx.game.ui


import com.mygdx.game.asset.TextureAtlasAsset
import ktx.assets.async.AssetStorage
import ktx.scene2d.Scene2DSkin
import ktx.style.imageButton
import ktx.style.skin

fun createSkin(assets: AssetStorage) {
    val atlas = assets[TextureAtlasAsset.UI.descriptor]
    Scene2DSkin.defaultSkin = skin(atlas) { skin ->
        imageButton("Play")
        imageButton("Exit")
        imageButton("Settings")

    }
}
