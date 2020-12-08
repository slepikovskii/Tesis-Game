package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.Textures
import com.mygdx.game.ecs.createPlayer
import com.mygdx.game.ecs.system.AnimationSystem
import com.mygdx.game.ecs.system.MoveSystem
import com.mygdx.game.ecs.system.PlayerInputSystem
import com.mygdx.game.ecs.system.RenderSystem
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.GameEventManager
import com.mygdx.game.widget.parallaxBackground
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.table

class GameScreen(private val eventManager: GameEventManager,
        private val assets: AssetStorage,
        private val engine: PooledEngine,
        private val stage: Stage,
        private val gameViewport: FitViewport) : KtxScreen, GameEventListener {

    private lateinit var paperRemains: Label

    override fun render(delta: Float) {
        stage.run {
            viewport.apply()
            act()
            draw()
        }
        engine.update(delta)
    }

    override fun show() {
        super.show()
        // initialize entity engine
        engine.apply {
            addSystem(MoveSystem())
            addSystem(RenderSystem(assets, stage, gameViewport ))
            addSystem(AnimationSystem(assets[Animations.Lvl1.descriptor]))
            addSystem(PlayerInputSystem(eventManager))
        }
        engine.run {
            createPlayer(assets)
        }
        eventManager.addListener(GameEvent.PaperThrown::class, this)
        setupUI()
    }

    override fun hide() {
        super.hide()
        eventManager.removeListener(this)
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }

    private fun setupUI() {
        stage.actors {
            parallaxBackground(Array(1) { assets[Textures.Background.descriptor] }).apply  {
                heigth = 720f
                width = 1280f
            }
            table {
                label("Paper remains:") {
                    setAlignment(Align.left)
                    it.apply { padLeft(20f) }
                }
                paperRemains = label("0") {
                    setAlignment(Align.left)
                    it.apply {
                        padLeft(10f)
                        fillX()
                        expandX()
                    }
                }

                top()
                setFillParent(true)
                pack()
            }
        }

        paperRemains.setText(10)
    }

    override fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.PaperThrown -> {
                paperRemains.run {
                    setText(text.toString().toInt() - 1)
                }
            }
        }
    }
}