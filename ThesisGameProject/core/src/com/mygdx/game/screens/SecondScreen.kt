package com.mygdx.game.screens

import com.mygdx.game.ThesisGame
import ktx.log.debug
import ktx.log.logger

private val LOG = logger<SecondScreen>()

class SecondScreen(game: ThesisGame) : ThesisGameScreen(game) {
    override fun show() {
        LOG.debug { "Second screen shown" }
        super.show()
    }
}