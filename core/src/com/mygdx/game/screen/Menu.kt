package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.I18NBundle
import com.mygdx.game.Game
import com.mygdx.game.UI.MenuUI
import com.mygdx.game.assests.*
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf

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


    //    private var img = assets[TextureAtlasAssets.Buttons.descriptor]
//    private var imgPlay = img.findRegion("play", -1)
    private val ui = MenuUI().apply {
        playMenuButton.onClick { game.setScreen<GameScreen>() }

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
        ui.run {  }

    }



}


