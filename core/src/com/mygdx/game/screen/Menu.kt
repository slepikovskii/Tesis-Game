package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.Game
import com.mygdx.game.UI.MenuUI
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.assests.Textures
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.app.KtxScreen
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.graphics.use
import ktx.scene2d.Scene2DSkin
import ktx.scene2d.scene2d
import ktx.style.imageButton
import ktx.style.label
import ktx.style.skin

class Menu(private val game: Game,
           private val batch: Batch,
           private val assets: AssetStorage,
           private val stage: Stage,
           private val engine: PooledEngine): KtxScreen {
    private var img = assets[TextureAtlasAssets.Buttons.descriptor]
    private var  imgPlay = img.findRegion("pause button",-1)

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
            Scene2DSkin.defaultSkin = skin(assets[TextureAtlasAssets.Buttons.descriptor]){
                label  ("default") {

                }
                imageButton ("play_button" ) {
                    imageUp = it.getDrawable("pause button")
                    imageChecked = imageUp
                    imageDown = imageUp
                }
            }
        }
    }
    private fun setupUI() {
//        ui.run {
//           stage += this.table
//        }
    }

    override fun hide() {
        super.hide()

    }

    override fun render(delta: Float) {
        super.render(delta)
        batch.use {
            imgPlay
        }
    }


}


