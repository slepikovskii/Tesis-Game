package com.mygdx.game.UI

import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.utils.I18NBundle

import com.mygdx.game.assests.TextureAtlasAssets
import ktx.scene2d.*
import ktx.scene2d.Scene2DSkin.defaultSkin


private const val GAME_UI_WIDTH = 20f
private const val GAME_UI_HEGHT = 20f

class MenuUI() {
    val table : KTableWidget
    val playMenuButton : ImageButton
//    val quitMenuButton : ImageButton
//    val settingsMenuButton : ImageButton


    init {

        table = scene2d.table {
            defaults().fillX().expandX()

            playMenuButton = imageButton(SkinImageButton.PLAYBUTTON.name)
            row()
//            settingsMenuButton = imageButton(SkinImageButton.SETTINGSBUTTON.name){
//                y = GAME_UI_HEGHT
//                x = GAME_UI_WIDTH
//            }
//            row()
//            quitMenuButton = imageButton(SkinImageButton.QUITBUTTON.name){
//                y = GAME_UI_HEGHT
//                x = GAME_UI_WIDTH
//            }

            top()
            setFillParent(true)
            pack()
        }
    }

}
