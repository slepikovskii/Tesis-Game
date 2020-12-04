package com.mygdx.game.UI

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.utils.Align
import com.mygdx.game.assests.TextureAtlasAssets
import ktx.assets.async.AssetStorage
import ktx.scene2d.*
import ktx.style.skin

class MenuUI() {
    val table : KTableWidget
    val playGameButton : ImageButton

    init {
        table = scene2d.table {
            defaults().expandX().fillX()
            row()
            playGameButton = imageButton(SkinImageButton.PLAYBUTTON.name)
            row()

        }
    }

}
