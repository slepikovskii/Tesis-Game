package com.mygdx.game.screen

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.graphics.Colors.reset
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Align
import com.badlogic.gdx.utils.Null
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.Game
import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.Textures
import com.mygdx.game.ecs.createPlayer
import com.mygdx.game.ecs.system.AnimationSystem
import com.mygdx.game.ecs.system.MoveSystem
import com.mygdx.game.ecs.system.RenderSystem
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.ashley.entity
import ktx.ashley.get
import ktx.ashley.getSystem
import ktx.assets.async.AssetStorage
import ktx.scene2d.actors
import ktx.scene2d.imageButton
import ktx.scene2d.label
import ktx.scene2d.table
import java.nio.file.Files.exists
import kotlin.math.min
private const val MAX_DELTA_TIME = 1 / 30f

class GameScreen(private val game: Game,
                 private val assets: AssetStorage,
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
//            addSystem(PlayerMovementSystem(assets[Animations.Lvl1.descriptor]))
            addSystem(AnimationSystem(assets[Animations.Lvl1.descriptor]))
        }
        engine.run {

            createPlayer(assets)

        }

        setupUI()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }

    private fun setupUI() {
        stage.actors {
            table {
                defaults().fillX()
                setDebug(true)

                imageButton(SkinImageButton.MENUBUTTON.name){
                    imageCell.maxHeight(100f).maxWidth(100f)
                    right()
                    onClick {
                        hide()
                        game.setScreen<Menu>()

                    }
                }

                top()
                right()
                setFillParent(true)
                pack()
            }
        }

    }

    override fun dispose() {
        super.dispose()
    }
    override fun hide() {
        stage.clear()
        engine.run {
            getSystem<MoveSystem>().setProcessing(false)
            getSystem<RenderSystem>().setProcessing(false)
            getSystem<AnimationSystem>().setProcessing(false)



        }


    }

}