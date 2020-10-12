package com.mygdx.game

import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.debug
import com.badlogic.gdx.Gdx
import ktx.log.logger
import com.badlogic.gdx.Application.LOG_DEBUG
import com.mygdx.game.screens.MainMenu


private val LOG = logger<ThesisGame>()

class ThesisGame : KtxGame<KtxScreen>() {

 override fun create() {
     Gdx.app.logLevel= LOG_DEBUG
     LOG.debug { "Application launched" }
    addScreen(MainMenu(this))

    setScreen<MainMenu>()
    }

}