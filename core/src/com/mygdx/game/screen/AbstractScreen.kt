package com.mygdx.game.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.Screen
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.Game
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.log.debug
import ktx.log.logger

val LOG = logger<Screen>()

abstract class AbstractScreen(
        val game: Game
):KtxScreen {
    private val gameViewport: Viewport = game.gameViewport
    val stage: Stage = game.stage
    val engine: Engine = game.engine
    val assets: AssetStorage = game.assets


    override fun show() {
        LOG.debug { "Show ${this::class.simpleName}" }

    }

    override fun hide() {
        stage.clear()
        engine.removeAllEntities()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }


}