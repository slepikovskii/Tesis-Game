package com.mygdx.game

import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.log.debug

import ktx.log.logger
import screens.MainMenu


private val LOG = logger<ThesisGame>()

class ThesisGame : KtxGame<KtxScreen>() {

 override  fun create() {
     LOG.debug { "Application launched" }
    addScreen(MainMenu())

    setScreen<MainMenu>()
    }

}