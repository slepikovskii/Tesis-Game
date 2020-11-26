package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.ecs.system.RenderSystem
import com.mygdx.game.screens.MainMenu
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.debug
import ktx.log.logger


const val UNIT_SCALE = 1/16f
const val V_HEIGHT_PIXELS = 240
const val V_WIDTH_PIXELS = 135
const val V_WIDTH = 9
const val V_HEIGHT = 16
private val LOG = logger<ThesisGame>()

class ThesisGame : KtxGame<KtxScreen>() {
    val gameViewport = FitViewport(9f,16f)
    val uiViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    val batch by lazy { SpriteBatch() }
    val engine : Engine by lazy {  PooledEngine().apply {
        val atlas = assets[TextureAtlasAsset.GRAPHICS.descriptor]
        addSystem(RenderSystem(
                batch,
                gameViewport,
                uiViewport))
    } }

    val stage:Stage by lazy {
        val result = Stage(uiViewport, batch)
        Gdx.input.inputProcessor = result
        result

    }
    val assets: AssetStorage by lazy {
        KtxAsync.initiate()
        AssetStorage()
    }

    override fun create() {
     Gdx.app.logLevel= LOG_DEBUG
     LOG.debug { "Application launched" }

     val assetrefs = gdxArrayOf(
         TextureAtlasAsset.values().filter {it.isSkinAtlas}.map {assets.loadAsync(it.descriptor)}
     ).flatten()

      addScreen(MainMenu(this))
      setScreen<MainMenu>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()

        stage.dispose()
    }

}