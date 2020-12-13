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
        allOf(TransformComponent::class, GraphicComponent::class, FacingComponent::class).get(),
        compareBy { entity: Entity -> entity[GraphicComponent.mapper]?.z }), EntityListener {
    private val batch = stage.batch
    private val houses by lazy {
        engine.getEntitiesFor(
                allOf(TransformComponent::class, GraphicComponent::class).exclude(PlayerComponent::class).get())
    }

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun update(deltaTime: Float) {
        forceSort()
        gameViewport.apply()
        renderHouses()
        batch.use(stage.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val facingComponent = entity[FacingComponent.mapper]
        require(facingComponent != null) { "Entity |entity| must have an FacingComponent. entity=$entity" }

        entity[TransformComponent.mapper]?.let { transform ->
            entity[GraphicComponent.mapper]?.let {
                it.sprite.run {
                    setFlip(facingComponent.direction == FacingDirection.LEFT, false)
                    setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
                    draw(batch)
                }
            }
        }
    }

    private fun renderHouses() {
        batch.use(gameViewport.camera.combined) {
            val rightWorldCoordinate = gameViewport.camera.position.x + gameViewport.screenWidth / 2
            houses.last()[TransformComponent.mapper]?.let {
                val rightHouseCoordinate = it.position.x + it.size.x
                if (rightHouseCoordinate < rightWorldCoordinate) {
                    engine.createHouse(assets, rightWorldCoordinate + Random.nextInt(5, 10))
                }
            }
            houses.forEach { entity ->
                entity[TransformComponent.mapper]?.let { transform ->
                    entity[GraphicComponent.mapper]?.let {

                        it.sprite.run {
                            setBounds(transform.position.x, transform.position.y, transform.size.x, transform.size.y)
                            draw(batch)
                        }
                    }
                }
            }
        }
    }
}