package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.*
import com.mygdx.game.ecs.createHouse
import ktx.ashley.allOf
import ktx.ashley.exclude
import ktx.ashley.get
import ktx.assets.async.AssetStorage
import ktx.graphics.use
import ktx.log.logger
import kotlin.random.Random

private val log = logger<RenderSystem>()

class RenderSystem(
        private val assets: AssetStorage,
        private val stage: Stage,
        private val gameViewport: Viewport) : SortedIteratingSystem(
        allOf(TransformComponent::class, GraphicComponent::class).exclude(PlayerComponent::class).get(),
        compareBy { entity: Entity -> entity[GraphicComponent.mapper] }), EntityListener {
    private val batch = stage.batch
    private val players by lazy {
        engine.getEntitiesFor(allOf(PlayerComponent::class).get())
    }
    private val houses by lazy {
        engine.getEntitiesFor(
                allOf(TransformComponent::class, GraphicComponent::class).exclude(PlayerComponent::class,
                                                                                  CollisionComponent::class).get())
    }

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun update(deltaTime: Float) {
        stage.viewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
        renderPlayer()
        houses.last()[TransformComponent.mapper]?.let {
            val rightWorldCoordinate = gameViewport.camera.position.x + gameViewport.screenWidth / 2
            val rightHouseCoordinate = it.position.x + it.size.x
            if (rightHouseCoordinate < rightWorldCoordinate) {
                engine.createHouse(assets, gameViewport, rightHouseCoordinate + Random.nextInt(20, 30))
            }
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            entity[GraphicComponent.mapper]?.let {
                it.sprite.run {
                    setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
                    draw(batch)
                }
            }
        }
    }

    private fun renderPlayer() {
        batch.use(stage.camera.combined) {
            players.forEach { entity ->
                entity[TransformComponent.mapper]?.let { transform ->
                    entity[GraphicComponent.mapper]?.let {
                        it.sprite.run {
                            setFlip(transform.direction == FacingDirection.LEFT, false)
                            setPosition(transform.position.x, transform.position.y)
                            draw(batch)
                        }
                    }
                }
            }
        }
    }
}