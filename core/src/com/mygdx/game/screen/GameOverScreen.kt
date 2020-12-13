package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.Game
import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.UI.createSkin
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.assests.Textures
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.debug
import ktx.log.logger
import ktx.scene2d.*

private val log = logger<Game>()

class GameOverScreen(private val game: Game,
                     private val assets: AssetStorage,
                     private val engine: PooledEngine,
                     private val stage: Stage,
                     private val gameViewport: FitViewport): KtxScreen {
    private lateinit var moneyEarned: Label


    override fun render(delta: Float) {
        stage.run {
            viewport.apply()
            act()
            draw()
        }
        engine.update(delta)
    }
    override fun show() {
        log.debug { "${this.stage} is shown" }
        super.show()
        val assetRefs = gdxArrayOf(
                TextureAtlasAssets.values().map { assets.loadAsync(it.descriptor) },
                Textures.values().map { assets.loadAsync(it.descriptor) },
                FontAsset.values().map { assets.loadAsync(it.descriptor) },
                Animations.values().map { assets.loadAsync(it.descriptor) }
        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()
            createSkin(assets)

        }
        setupUI()

    }
    override fun hide() {
        stage.clear()

    }

    private fun setupUI() {
        stage.actors {

            table {
                defaults().expand().fill()
                center()
                setFillParent(true)
                debug=true
                verticalGroup {
                    center()
                    label("Time is Up!"){
                        setAlignment(Align.center)
                        setFontScale(5f)

                    }

                    label("You earned:"){
                        setAlignment(Align.center)
                    }
                    moneyEarned = label("0") {
                        setAlignment(Align.left)

                    }

                    imageButton(SkinImageButton.QUITBUTTON.name){
                       imageCell.maxHeight(200f).maxWidth(200f)
                        onClick {
                            hide()
                            game.setScreen<Bedroom>()
                        }
                    }
                }
            }

        }
       moneyEarned.setText("10")
    }


}