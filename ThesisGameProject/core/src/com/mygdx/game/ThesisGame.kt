package com.mygdx.game

import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.debug
import com.badlogic.gdx.Gdx
import ktx.log.logger
import com.badlogic.gdx.Application.LOG_DEBUG
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.mygdx.game.screens.MainMenu

const val UNIT_SCALE = 1/16f
private val LOG = logger<ThesisGame>()

class ThesisGame : KtxGame<KtxScreen>() {
    val Batch by lazy { SpriteBatch() }

    override fun create() {
     Gdx.app.logLevel= LOG_DEBUG
     LOG.debug { "Application launched" }
      addScreen(MainMenu(this))
      setScreen<MainMenu>()
    }

}