package com.mygdx.game

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.PooledEngine
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.debug
import com.badlogic.gdx.Gdx
import ktx.log.logger
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.ecs.system.RenderSystem
import com.mygdx.game.screens.MainMenu

const val UNIT_SCALE = 1/16f
const val V_HEIGHT_PIXELS = 240
const val V_WIDTH_PIXELS = 135
const val V_WIDTH = 9
const val V_HEIGHT = 16
private val LOG = logger<ThesisGame>()

class ThesisGame : KtxGame<KtxScreen>() {
    val gameViewport = FitViewport(9f,16f)
    val uiViewport = FitViewport(V_WIDTH_PIXELS.toFloat(), V_HEIGHT_PIXELS.toFloat())
    val Batch by lazy { SpriteBatch() }
    val engine : Engine by lazy {  PooledEngine().apply {
        addSystem(RenderSystem(
                Batch,
                gameViewport,
                uiViewport))
    } }

    val stage.Stage by lazy {
        val result = Stage(uiViewport, batch)
        Gdx.input.inputProcessor = result
        result

    }

    override fun create() {
     Gdx.app.logLevel= LOG_DEBUG
     LOG.debug { "Application launched" }
      addScreen(MainMenu(this))
      setScreen<MainMenu>()
    }

    override fun dispose() {
        super.dispose()
        batch.dispose()
        assets.dispose()
        stage.dispose()
    }

}