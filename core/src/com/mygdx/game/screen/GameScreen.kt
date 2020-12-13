package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.Game
import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.Textures
import com.mygdx.game.ecs.component.FacingDirection
import com.mygdx.game.ecs.createHouses
import com.mygdx.game.ecs.createPlayer
import com.mygdx.game.ecs.system.AnimationSystem
import com.mygdx.game.ecs.system.MoveSystem
import com.mygdx.game.ecs.system.PlayerInputSystem
import com.mygdx.game.ecs.system.RenderSystem
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.GameEventManager
import com.mygdx.game.widget.ParallaxBackground
import com.mygdx.game.widget.parallaxBackground
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.scene2d.*

val DEFAULT_BACKGROUND_SPEED = 1

class GameScreen(private val eventManager: GameEventManager,
        private val assets: AssetStorage,
        private val engine: PooledEngine,
        private val stage: Stage,
        private val game: Game,
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
        // initialize entity engine
        engine.apply {
            addSystem(MoveSystem(eventManager))
            addSystem(RenderSystem(assets, stage, gameViewport))
            addSystem(AnimationSystem(assets[Animations.Lvl1.descriptor]))
            addSystem(PlayerInputSystem(eventManager))
        }

        engine.run {
            createPlayer(assets)
            createHouses(assets)
        }
        eventManager.run {
            addListener(GameEvent.PaperThrown::class, this@GameScreen)
            addListener(GameEvent.PlayerMoved::class, this@GameScreen)
        }
        setupUI()
    }

    override fun hide() {
        super.hide()
        stage.clear()
        engine.removeAllEntities()
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
                defaults().fillX().expandX()
                debug = true

                verticalGroup {
                    left()
                    top()
                    horizontalGroup{
                        padLeft(10f)
                        label("Paper remains:") {
                            setAlignment(Align.left)

                        }
                        paperRemains = label("0") {
                            setAlignment(Align.left)

                        }
                    }
                }


                verticalGroup {
                    top()
                    right()
                    imageButton(SkinImageButton.MENUBUTTON.name){
                        imageCell.maxWidth(100f).maxHeight(100f)
                        onClick {
                            hide()
                            game.setScreen<Menu>()
                        }
                    }
                }




                top()
                setFillParent(true)


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
            is GameEvent.PlayerMoved -> {
                when (event.direction) {
                    FacingDirection.RIGHT -> {
                        background.setSpeed(DEFAULT_BACKGROUND_SPEED)
                    }
                    FacingDirection.LEFT -> {
                        background.setSpeed(-DEFAULT_BACKGROUND_SPEED)
                    }
                    else -> {
                        background.setSpeed(0)
                    }
                }
            }
        }
    }
}