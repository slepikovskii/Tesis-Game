package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.FacingDirection
import com.mygdx.game.ecs.component.MoveComponent
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.ecs.component.TransformComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.log.logger
import kotlin.math.abs
import kotlin.math.min

private const val HOR_ACCELERATION = 50f
private const val MAX_SPEED = 200f

private val log = logger<MoveSystem>()

class MoveSystem(private val gameEventManager: GameEventManager, private val viewport: Viewport) : IteratingSystem(
        allOf(TransformComponent::class, MoveComponent::class, PlayerComponent::class).get()) {

    private val part = viewport.screenWidth / 3
    private val leftSide = 0..part
    private val rightSide = part * 2..part * 3

    override fun processEntity(entity: Entity, deltaTime: Float) {
        var newSpeed = 0f
        val oldSpeed = entity[MoveComponent.mapper]?.speed ?: 0f

        when {
            Gdx.input.isKeyPressed(Input.Keys.RIGHT) || (Gdx.input.isTouched && Gdx.input.x in rightSide) -> {
                entity[TransformComponent.mapper]?.let {
                    if (it.direction == FacingDirection.LEFT) {
                        it.switchDirection()
                    } else {
                        newSpeed = min(MAX_SPEED, oldSpeed + HOR_ACCELERATION * deltaTime)
                    }
                }
            }
            Gdx.input.isKeyPressed(Input.Keys.LEFT) || (Gdx.input.isTouched && Gdx.input.x in leftSide) -> {
                entity[TransformComponent.mapper]?.let {
                    if (it.direction == FacingDirection.RIGHT) {
                        it.switchDirection()
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
        viewport.camera.update()
        gameEventManager.dispatchEvent(GameEvent.PlayerMoved(newSpeed * deltaTime))
    }
}