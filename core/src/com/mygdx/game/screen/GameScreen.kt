package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.assests.Textures
import com.mygdx.game.ecs.createHouses
import com.mygdx.game.ecs.createPlayer
import com.mygdx.game.ecs.system.*
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.GameEventManager
import com.mygdx.game.widget.ParallaxBackground
import com.mygdx.game.widget.parallaxBackground
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.scene2d.actors
import ktx.scene2d.label
import ktx.scene2d.table
import java.util.*

const val DEFAULT_BACKGROUND_SPEED = 1

class GameScreen(private val eventManager: GameEventManager,
        private val assets: AssetStorage,
        private val engine: PooledEngine,
        private val stage: Stage,
        private val gameViewport: FitViewport) : KtxScreen, GameEventListener {

    private lateinit var paperRemains: Label
    private lateinit var background: ParallaxBackground

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

        engine.run {
            addSystem(MoveSystem(eventManager, gameViewport))
            addSystem(RenderSystem(assets, stage, gameViewport))
            addSystem(AnimationSystem(assets[Animations.Lvl1.descriptor], eventManager))
            addSystem(PlayerInputSystem(eventManager, gameViewport))
            addSystem(CollisionSystem(eventManager, gameViewport, assets[TextureAtlasAssets.GameObjects.descriptor]))
            createPlayer(assets, gameViewport)
            createHouses(assets, gameViewport)
        }
        eventManager.run {
            addListener(GameEvent.PaperHit::class, this@GameScreen)
            addListener(GameEvent.PlayerMoved::class, this@GameScreen)
        }
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
            background = parallaxBackground(arrayOf(
                    assets[Textures.Background.descriptor],
                    assets[Textures.HousesBackground.descriptor],
                    assets[Textures.Road.descriptor]
            )).apply {
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

        paperRemains.setText(50)
    }

    override fun onEvent(event: GameEvent) {
        when (event) {
            is GameEvent.PaperHit -> {
                paperRemains.run {
                    setText(text.toString().toInt() - 1)
                }
            }
            is GameEvent.PlayerMoved -> {
                background.setSpeed(event.speed)
            }
        }
    }
}