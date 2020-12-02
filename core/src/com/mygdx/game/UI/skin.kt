package com.mygdx.game.UI

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import ktx.assets.async.AssetStorage
import ktx.scene2d.Scene2DSkin
import ktx.style.imageButton
import ktx.style.label
import ktx.style.skin

enum class SkinImageButton {
    PLAYBUTTON
}

enum class SkinImage(val atlaskey: String) {
    PLAY("play")
}

fun createSkin(assets: AssetStorage) {
    val atlas = assets[TextureAtlasAssets.Buttons.descriptor]
    Scene2DSkin.defaultSkin = skin(atlas) { skin ->
        createImageButtonStyles(skin)
        label("default") {
            font = assets[FontAsset.FONT_DEFAULT.descriptor]
        }
    }

}

private fun Skin.createImageButtonStyles(
        skin: Skin
) {
    imageButton(SkinImageButton.PLAYBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.PLAY.atlaskey)
        imageDown = imageUp
    }
}
