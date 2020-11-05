package com.mygdx.game.ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.mygdx.game.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with

fun Engine.createPlayer(
): Entity {

    return entity {
            with<BikeComponent>()
            with<TransformComponent> { bounds.set(Gdx.graphics.width / 2f - 64f / 2f, 20f, 64f, 64f) }
            with<MoveComponent>()
            with<GraphicComponent>()
            with<AnimationComponent> { type = AnimationType.BIKE_LVL_1 }
        }
}