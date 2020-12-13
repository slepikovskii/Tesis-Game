package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.FacingComponent
import com.mygdx.game.ecs.component.FacingDirection
import com.mygdx.game.ecs.component.MoveComponent
import com.mygdx.game.ecs.component.TransformComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get
import kotlin.math.min

private const val HOR_ACCELERATION = 50f
private const val MAX_SPEED = 200f

class MoveSystem(private val gameEventManager: GameEventManager, private val viewport: Viewport) : IteratingSystem(
        allOf(TransformComponent::class, MoveComponent::class).get()) {
    var previousDirecrion = FacingDirection.RIGHT

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[MoveComponent.mapper]?.let {
            when {
                Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> {
                    if (previousDirecrion == FacingDirection.LEFT) {
                        it.speed = 0f
                        previousDirecrion = FacingDirection.RIGHT
                    } else {
                        it.speed = min(MAX_SPEED, it.speed + HOR_ACCELERATION * deltaTime)
                    }

                    entity[FacingComponent.mapper]?.direction = FacingDirection.RIGHT
                    entity[TransformComponent.mapper]?.apply {
                        viewport.camera.translate(it.speed * deltaTime, 0f, 0f)
                    }
                    gameEventManager.dispatchEvent(GameEvent.PlayerMoved(it.speed * deltaTime))
                }
                Gdx.input.isKeyPressed(Input.Keys.LEFT) -> {
                    if (previousDirecrion == FacingDirection.RIGHT) {
                        it.speed = 0f
                        previousDirecrion = FacingDirection.LEFT
                    } else {
                        it.speed = min(MAX_SPEED, it.speed + HOR_ACCELERATION * deltaTime)
                    }
                    entity[FacingComponent.mapper]?.direction = FacingDirection.LEFT
                    entity[TransformComponent.mapper]?.apply {
                        viewport.camera.translate(-it.speed * deltaTime, 0f, 0f)
                    }
                    gameEventManager.dispatchEvent(GameEvent.PlayerMoved(-it.speed * deltaTime))
                }
                else -> {
                    it.speed = 0f
                    gameEventManager.dispatchEvent(GameEvent.PlayerMoved(0f))
                }
            }
        }
    }
}