package com.mygdx.game.screens

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.ThesisGame
import ktx.log.debug
import ktx.log.logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.UNIT_SCALE
import ktx.app.KtxScreen
import ktx.graphics.use

private val LOG = logger<MainMenu>()

class MainMenu(game: ThesisGame) : ThesisGameScreen(game){

    private val viewport = FitViewport(9f,16f)
    private val texture by lazy { Texture(Gdx.files.internal("graphics/box.png")) }
    private val sprite = Sprite(texture).apply{
        setSize(9f,16f)
    }
    override fun show() {
        LOG.debug { "Main menu shown" }
        sprite.setPosition(0f,0f)
        super.show()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        batch.use(viewport.camera.combined) {
            sprite.draw(batch)
        }

        super.render(delta)
    }

    override fun dispose() {
        super.dispose()
        texture.dispose()
    }
}