package com.mygdx.game.ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.mygdx.game.assests.Animations
import com.mygdx.game.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with
import ktx.assets.async.AssetStorage

fun Engine.createPlayer(
        assets: AssetStorage
): Entity {

    return entity {
        with<PlayerComponent>()
        with<TransformComponent> {
            val atlas = assets[Animations.Lvl1.descriptor]
            val playerGraphicRegion = atlas.findRegion("bike_lvl", 0)
            size.set(
                    playerGraphicRegion.regionWidth.toFloat(),
                    playerGraphicRegion.regionHeight.toFloat()
            )
            setInitialPosition(
                    10f,
                    5f,
            )
        }
        with<MoveComponent>()
        with<GraphicComponent> { z = 1 }
        with<AnimationComponent> { type = AnimationType.BIKE_LVL_1 }
        with<FacingComponent>()
    }
}