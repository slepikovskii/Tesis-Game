package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntityListener
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.mygdx.game.ecs.component.*
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerMovementSystem(
        private val atlas: TextureAtlas
) : IteratingSystem(allOf(AnimationComponent::class, PlayerComponent::class, GraphicComponent::class, MoveComponent::class).get()), EntityListener {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[MoveComponent.mapper]?.let { move ->
            entity[GraphicComponent.mapper]?.let { graphic ->
                entity[AnimationComponent.mapper]?.let {
                    if (move.speed != 0f) {
                        it.stateTime += deltaTime
                    } else {
                        it.stateTime = 0f
                    }
                    graphic.sprite.setRegion(it.animation.getKeyFrame(it.stateTime))
                }
            }

        }
    }


    override fun addedToEngine(engine: Engine) {
        super.addedToEngine(engine)
        engine.addEntityListener(family, this)
    }

    override fun entityAdded(entity: Entity) {
        entity[AnimationComponent.mapper]?.let {
            it.animation = Animation2D(it.type, atlas.regions, it.type.playMode, it.type.speed)
            val frame = it.animation.getKeyFrame(it.stateTime)
            entity[GraphicComponent.mapper]?.sprite?.setRegion(frame)
        }
    }

    override fun entityRemoved(entity: Entity?) {
        TODO("Not yet implemented")
    }
}