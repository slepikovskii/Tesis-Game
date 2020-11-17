package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.math.MathUtils
import com.mygdx.game.ecs.component.MoveComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get

class MoveSystem : IteratingSystem(allOf(TransformComponent::class, MoveComponent::class).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            entity[MoveComponent.mapper]?.let { move ->
                transform.size.x = MathUtils.clamp(transform.size.x + move.speed * deltaTime, 0f, 800f - 64f)
            }
        }
    }
}