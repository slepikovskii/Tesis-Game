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
    PLAYBUTTON, QUITBUTTON, SETTINGSBUTTON, MENUBUTTON, SHOPBUTTON, GAMEBUTTON, HOMEBUTTON,GPIGGYBUTTON, SHOPCARD1BUTTON, SHOPCARD2BUTTON, SHOPCARD3BUTTON, PPIGGYBUTTON, COINBUTTON, CALENDARBUTTON, NEWSPAPERBUTTON
}

enum class SkinImage(val atlaskey: String) {
    PLAY("play button"),
    QUIT("X button"),
    SETTINGS("Settings button"),
    MENU("Menu button"),
    SHOP("shop"),
    GAME("work"),
    HOME("Homeshop button"),
    GPIGGY("Gpig"),
    SHOPCARD1("Shop_card1"),
    SHOPCARD2("Shop_card2"),
    SHOPCARD3("Shop_card3"),
    PPIGGY("Ppig"),
    COIN("Coin"),
    CALENDAR("calendar1"),
    NEWSPAPER("newspaper")

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
    imageButton(SkinImageButton.QUITBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.QUIT.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.SETTINGSBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.SETTINGS.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.MENUBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.MENU.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.SHOPBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.SHOP.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.GAMEBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.GAME.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.HOMEBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.HOME.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.GPIGGYBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.GPIGGY.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.SHOPCARD1BUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.SHOPCARD1.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.SHOPCARD2BUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.SHOPCARD2.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.SHOPCARD3BUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.SHOPCARD3.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.PPIGGYBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.PPIGGY.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.COINBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.COIN.atlaskey)
        imageDown = imageUp
    }

    imageButton(SkinImageButton.CALENDARBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.CALENDAR.atlaskey)
        imageDown = imageUp
    }
    imageButton(SkinImageButton.NEWSPAPERBUTTON.name) {
        imageUp = skin.getDrawable(SkinImage.NEWSPAPER.atlaskey)
        imageDown = imageUp
    }
}
