package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Cell.defaults
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Value.maxHeight
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Align.center
import com.badlogic.gdx.utils.I18NBundle
import com.mygdx.game.Game

import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.assests.*
import com.sun.awt.SecurityWarning.setPosition
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.graphics.color
import ktx.scene2d.*
import javax.swing.text.StyleConstants.setAlignment

private const val MENU_DEFAULT_PADDING = 25f

class Menu(private val game: Game,
           private val batch: Batch,
           private val assets: AssetStorage,
           private val stage: Stage,
           private val engine: PooledEngine,
           ) : KtxScreen {



    override fun render(delta: Float) {
        engine.update(delta)
        stage.run {
            viewport.apply()
            act()
            draw()
        }
    }

    override fun show() {
        super.show()
        val assetRefs = gdxArrayOf(
                TextureAtlasAssets.values().map { assets.loadAsync(it.descriptor) },
                Textures.values().map { assets.loadAsync(it.descriptor) },
                FontAsset.values().map { assets.loadAsync(it.descriptor) },
                Animations.values().map { assets.loadAsync(it.descriptor) }
        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()

        }
        setupUI()
    }


    private fun setupUI() {
    stage.actors {
        table {
            defaults().fillX().expandX().pad(MENU_DEFAULT_PADDING)
            setFillParent(true)

           imageButton(SkinImageButton.PLAYBUTTON.name){
                imageCell.maxHeight(200f).maxWidth(200f)
                onClick {
                    game.removeScreen<Menu>()
                    dispose()
                    game.setScreen<GameScreen>() }

            }
            row()
            imageButton(SkinImageButton.SETTINGSBUTTON.name){
                imageCell.maxHeight(200f).maxWidth(200f)
            }
            row()
            imageButton(SkinImageButton.QUITBUTTON.name){
                imageCell.maxHeight(200f).maxWidth(200f)
                onClick { game.dispose() }
            }
            top()
            center()
            dispose()
        }
    }
    }



}


