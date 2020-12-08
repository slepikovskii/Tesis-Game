package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.MathUtils
import com.mygdx.game.ecs.component.FacingComponent
import com.mygdx.game.ecs.component.FacingDirection
import com.mygdx.game.ecs.component.MoveComponent
import com.mygdx.game.ecs.component.TransformComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get

private const val HOR_ACCELERATION = 50f

class MoveSystem(private val gameEventManager: GameEventManager) : IteratingSystem(
        allOf(TransformComponent::class, MoveComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[MoveComponent.mapper]?.let {
            when {
                Gdx.input.isKeyPressed(Input.Keys.RIGHT) -> {
                    it.speed += HOR_ACCELERATION * deltaTime
                    entity[FacingComponent.mapper]?.direction = FacingDirection.RIGHT
                    entity[TransformComponent.mapper]?.apply {
                        position.x = MathUtils.clamp(
                                position.x + it.speed * deltaTime,
                                -40f,
                                Gdx.graphics.width + 40 - size.x
                        )
                    }
                    gameEventManager.dispatchEvent(GameEvent.PlayerMoved(FacingDirection.RIGHT))
                }
                Gdx.input.isKeyPressed(Input.Keys.LEFT) -> {
                    it.speed += HOR_ACCELERATION * deltaTime
                    entity[FacingComponent.mapper]?.direction = FacingDirection.LEFT
                    entity[TransformComponent.mapper]?.apply {
                        position.x = MathUtils.clamp(
                                position.x - it.speed * deltaTime,
                                -40f,
                                Gdx.graphics.width + 40 - size.x
                        )
                    }
                    gameEventManager.dispatchEvent(GameEvent.PlayerMoved(FacingDirection.LEFT))
                }
                else -> {
                    it.speed = 0f
                    gameEventManager.dispatchEvent(GameEvent.PlayerMoved(null))
                }
            }
        }
    }
}