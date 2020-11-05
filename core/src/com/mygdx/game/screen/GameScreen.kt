package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.Textures
import com.mygdx.game.ecs.createPlayer
import com.mygdx.game.ecs.system.MoveSystem
import com.mygdx.game.ecs.system.PlayerMovementSystem
import com.mygdx.game.ecs.system.RenderSystem
import com.sun.xml.internal.fastinfoset.alphabet.BuiltInRestrictedAlphabets.table
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.scene2d.*

class GameScreen(private val batch: Batch,
//                 private val font: BitmapFont,
        private val assets: AssetStorage,
        private val camera: OrthographicCamera,
        private val engine: PooledEngine,
        private val stage: Stage,
        private val gameViewport: FitViewport) : KtxScreen {

    override fun render(delta: Float) {
        engine.update(delta)
        stage.run {
            viewport.apply()
            act()
            draw()
        }
    }

    override fun show() {
        super.show()
        // initialize entity engine
        engine.apply {
            addSystem(MoveSystem())
            addSystem(RenderSystem(assets, stage, gameViewport, assets[Textures.Example.descriptor]))
            addSystem(PlayerMovementSystem(assets[Animations.Lvl1.descriptor]))
        }
        engine.run {
            createPlayer()
        }
        setupUI()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }

    private fun setupUI(): Unit {
        stage.actors {
            table {
                defaults().fillX().expandX()

                label("GAME") { cell ->
                    setAlignment(Align.center)
                    cell.apply {
                        padTop(20f)
                    }
                }

                top()
                setFillParent(true)
                pack()
            }
        }
    }
}