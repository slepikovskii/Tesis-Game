package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.I18NBundle
import com.mygdx.game.Game

import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.assests.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.scene2d.actors
import ktx.scene2d.imageButton
import ktx.scene2d.scene2d
import ktx.scene2d.table
import javax.swing.text.StyleConstants.setAlignment

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
            defaults().expandX().fill()
            setDebug(true)
            row()
            imageButton(SkinImageButton.PLAYBUTTON.name){cell ->
                x = 50f
                y = 50f
                cell.apply {
                    padTop(20f)

                    maxWidth(200f)
                    maxHeight(200f)
                    expandX()
                    top()
                }
            }
            row()
            imageButton(SkinImageButton.SETTINGSBUTTON.name){cell ->

                cell.apply {
                    padTop(30f)

                    maxWidth(200f)
                    maxHeight(200f)
                    center()
                }
            }
            row()
            imageButton(SkinImageButton.QUITBUTTON.name){cell ->

                cell.apply {
                    padTop(30f)

                    maxWidth(200f)
                    maxHeight(200f)
                    expandX()
                    bottom()
                }
            }

            top()
            setFillParent(true)
            pack()
        }
    }
    }



}


