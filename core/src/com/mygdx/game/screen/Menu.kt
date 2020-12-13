package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.Game
import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.UI.createSkin
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.assests.Textures
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

private const val MENU_DEFAULT_PADDING = 25f
private val log = logger<Game>()

class Menu(private val game: Game,
           private val batch: Batch,
           private val assets: AssetStorage,
           private val stage: Stage,
           private val engine: PooledEngine,
           ) : KtxScreen {
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


    private fun setupUI() {
    stage.actors {
        background = parallaxBackground(arrayOf(
            assets[Textures.MenuBG.descriptor]

    )).apply {
        heigth = 720f
        width = 1280f
    }
        table {
            defaults().fillX().expandX().pad(MENU_DEFAULT_PADDING)
            setFillParent(true)
            setDebug(false)
           verticalGroup{
               imageButton(SkinImageButton.PLAYBUTTON.name){
                   imageCell.maxHeight(200f).maxWidth(200f)
                   onClick {
                       hide()
                       game.setScreen<Bedroom>()
                   }
               }
               imageButton(SkinImageButton.SETTINGSBUTTON.name){
                   imageCell.maxHeight(200f).maxWidth(200f)
               }
               imageButton(SkinImageButton.QUITBUTTON.name){
                   imageCell.maxHeight(200f).maxWidth(200f)
                   onClick { Gdx.app.exit() }
               }
           }
            top()
            center()

        }
    }
    }
//    override fun resize(width: Int, height: Int) {
//
//        stage.viewport.update(width, height, true)
//    }


    override fun hide() {
        stage.clear()

    }



}


