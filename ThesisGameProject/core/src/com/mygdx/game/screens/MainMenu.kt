package com.mygdx.game.screens

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.ThesisGame
import ktx.log.debug
import ktx.log.logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.UNIT_SCALE
import com.mygdx.game.asset.TextureAsset
import com.mygdx.game.asset.TextureAtlasAsset
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.TransformComponent
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.graphics.use
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.table

private val LOG = logger<MainMenu>()

class MainMenu(game: ThesisGame) : ThesisGameScreen(game){
    private val playerTexture by lazy { Texture(Gdx.files.internal("graphics/box.png")) }
    private val viewport = FitViewport(9f,16f)
    private val assets: AssetStorage = game.assets

    private val player = game.engine.entity{
        with<TransformComponent>{
            position.set(1f,1f,1f)
        }
        with<GraphicComponent>{
            sprite.run{

                setRegion(playerTexture)
            }

        }
    }

    override fun show() {
        LOG.debug { "Main menu is shown" }

        val old = System.currentTimeMillis()
        val assetRefs = gdxArrayOf(
                TextureAtlasAsset.values().filter { !it.isSkinAtlas }.map { assets.loadAsync(it.descriptor) },
                TextureAsset.values().map { assets.loadAsync(it.descriptor) }

        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()
            LOG.debug { "It took ${(System.currentTimeMillis() - old) * 0.001f} seconds to load assets and initialize" }
            assetsLoaded()
        }
        setupUI()
        super.show()
    }
    private fun setupUI(){
        stage.actors {
            table {
               defaults().fillX().expandX()
                label("Main Menu") {
                    wrap = true
                    setAlignment(Align.center)
                }
                row()

            }
        }
    }
    private fun assetsLoaded() {

    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        engine.update(delta)
        


        super.render(delta)
    }

}