package com.mygdx.game.screens

import com.mygdx.game.ThesisGame
import ktx.log.debug
import ktx.log.logger
import ktx.app.KtxScreen

private val LOG = logger<MainMenu>()

class MainMenu(game: ThesisGame) : ThesisGameScreen(game){
    override fun show() {
        LOG.debug { "Main menu shown" }
        super.show()
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

    override fun dispose() {
        super.dispose()
    }
}