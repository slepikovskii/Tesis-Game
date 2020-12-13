package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.CollisionComponent
import com.mygdx.game.ecs.component.TransformComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get


class CollisionSystem(private val eventManager: GameEventManager, private val gameViewport: Viewport) : IteratingSystem(
        allOf(CollisionComponent::class, TransformComponent::class).get()), GameEventListener {
    private var checkCollision = false

    override fun update(deltaTime: Float) {
        if (checkCollision) {
            super.update(deltaTime)
            checkCollision = false
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val cameraX = gameViewport.camera.position.x
        entity[TransformComponent.mapper]?.let {
            if (cameraX in it.position.x..it.position.x + it.size.x) {
                eventManager.dispatchEvent(GameEvent.PaperHit)
            }
        }
    }

    override fun onEvent(event: GameEvent) {
        if (event is GameEvent.PaperThrown) {
            checkCollision = true
        }
    }

    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        eventManager.addListener(GameEvent.PaperThrown::class, this)
    }
}