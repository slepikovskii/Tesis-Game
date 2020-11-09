package com.mygdx.game.screens

import com.badlogic.gdx.graphics.Texture
import com.mygdx.game.ThesisGame
import ktx.log.debug
import ktx.log.logger
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.UNIT_SCALE
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.with
import ktx.graphics.use

private val LOG = logger<MainMenu>()

class MainMenu(game: ThesisGame) : ThesisGameScreen(game){
    private val playerTexture by lazy { Texture(Gdx.files.internal("graphics/box.png")) }
    private val viewport = FitViewport(9f,16f)

    private val player = game.engine.entity{
        with<TransformComponent>{
            position.set(1f,1f,1f)
        }
        with<GraphicComponent>{
            sprite.run{

                setRegion(playerTexture)
            }

        }
    }

    override fun show() {
        LOG.debug { "Main menu is shown" }

        super.show()
    }

    override fun resize(width: Int, height: Int) {
        viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        engine.update(delta)
        
        batch.use(viewport.camera.combined) {
            player[GraphicComponent.mapper]?.sprite?.draw(it)
        }

        super.render(delta)
    }

    override fun dispose() {
        super.dispose()

    }
}