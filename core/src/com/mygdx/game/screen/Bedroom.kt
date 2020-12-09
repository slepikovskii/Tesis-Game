package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.Game
import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.UI.createSkin
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.assests.Textures
import com.mygdx.game.event.GameEventManager
import com.mygdx.game.widget.ParallaxBackground
import com.mygdx.game.widget.parallaxBackground
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.debug
import ktx.log.logger
import ktx.scene2d.actors
import ktx.scene2d.imageButton
import ktx.scene2d.table
import ktx.scene2d.verticalGroup

private val log = logger<Game>()

class Bedroom(
        private val game: Game,
        private val assets: AssetStorage,
        private val engine: PooledEngine,
        private val stage: Stage,
        private val gameViewport: FitViewport,
        private val eventManager: GameEventManager
): KtxScreen {
    private lateinit var background: ParallaxBackground

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
    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }
    private fun setupUI() {
        stage.actors {
            background = parallaxBackground(arrayOf(
                    assets[Textures.RoomBackground.descriptor]

            )).apply {
                heigth = 720f
                width = 1280f
            }
           table {
               defaults().fillX().expandX().fillY().expandY()
                setFillParent(true)
               setDebug(true)

               verticalGroup {
                   imageButton(SkinImageButton.MENUBUTTON.name){

                   imageCell.maxHeight(200f).maxWidth(200f)
                   onClick {
                       hide()
                       game.setScreen<Menu>()
                   }

               }
                   top()
                   left()

               }



               verticalGroup {
                   imageButton(SkinImageButton.GAMEBUTTON.name) {

                       imageCell.maxHeight(200f).maxWidth(200f)
                       onClick {
                           hide()
                           game.setScreen<GameScreen>()
                       }

                   }

                   imageButton(SkinImageButton.SHOPBUTTON.name){

                       imageCell.maxHeight(200f).maxWidth(200f)
//                   onClick {
//                       game.setScreen<ShopScreen>()
//                   }
                   }

                   imageButton(SkinImageButton.PIGGYBUTTON.name){
                       left()
                       imageCell.maxHeight(200f).maxWidth(200f)
                   }

                   right()
                   top()
               }


               top()


           }

            }
        }



    }
