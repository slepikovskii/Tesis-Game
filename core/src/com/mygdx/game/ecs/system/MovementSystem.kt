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
import kotlin.math.abs
import kotlin.math.min

private const val HOR_ACCELERATION = 50f
private const val MAX_SPEED = 200f

class MoveSystem(private val gameEventManager: GameEventManager, private val viewport: Viewport) : IteratingSystem(
        allOf(TransformComponent::class, MoveComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        var newSpeed = 0f
        val oldSpeed = entity[MoveComponent.mapper]?.speed ?: 0f
        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> {
                entity[FacingComponent.mapper]?.apply {
                    if (direction == FacingDirection.LEFT) {
                        switchDirection()
                    } else {
                        newSpeed = min(MAX_SPEED, oldSpeed + HOR_ACCELERATION * deltaTime)
                    }
                }
            }
            Gdx.input.isKeyPressed(Input.Keys.LEFT) -> {
                entity[FacingComponent.mapper]?.apply {
                    if (direction == FacingDirection.RIGHT) {
                        switchDirection()
                    } else if (viewport.camera.position.x > viewport.screenWidth / 2) {
                        newSpeed = -min(MAX_SPEED, oldSpeed + HOR_ACCELERATION * deltaTime)
                    }
                }
            }
        }

        entity[MoveComponent.mapper]?.let {
            it.speed = abs(newSpeed)
            moveEntity(newSpeed, deltaTime)
        }
    }

    private fun moveEntity(newSpeed: Float, deltaTime: Float) {
        viewport.camera.translate(newSpeed * deltaTime, 0f, 0f)
        gameEventManager.dispatchEvent(GameEvent.PlayerMoved(newSpeed * deltaTime))
    }
}