package com.mygdx.game.screen

import com.mygdx.game.Game
import com.mygdx.game.UI.MenuUI
import ktx.app.KtxScreen
import ktx.actors.onClick
import ktx.actors.plusAssign

class Menu(game: Game): AbstractScreen(game) {
    private val ui = MenuUI()

    override fun show() {
        super.show()
        engine.run {  }
        setupUI()
    }

    private fun setupUI() {
        ui.run {
           stage += this.table
        }
    }

    override fun hide() {
        super.hide()

    }

    override fun render(delta: Float) {
        super.render(delta)
        stage.run {
            viewport.apply()
            act(delta)
            draw()
        }
    }


}


