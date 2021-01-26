package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.Item
import ktx.ashley.mapperFor

const val BASE_MAX_SPEED = 200f
const val BASE_HOR_ACCELERATION = 50f

class MoveComponent : Component, Pool.Poolable, GameEventListener {
    companion object {
        val mapper = mapperFor<MoveComponent>()
    }
    var maxSpeed = BASE_MAX_SPEED
    var horAcceleration = BASE_HOR_ACCELERATION
    var speed = 0f

    override fun reset() {
        speed = 0f
        maxSpeed = BASE_MAX_SPEED
        horAcceleration = BASE_HOR_ACCELERATION
    }

    override fun onEvent(event: GameEvent) {
        if (event is GameEvent.ItemBought) {
            when (event.item) {
                Item.LVL2 -> {
                    maxSpeed *= 1.5f
                    horAcceleration *= 1.2f
                }
                Item.LVL3 -> {
                    maxSpeed *= 1.5f
                    horAcceleration *= 1.2f
                }
            }
        }
    }
}