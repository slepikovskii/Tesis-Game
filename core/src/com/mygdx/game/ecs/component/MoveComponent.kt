package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class MoveComponent : Component, Pool.Poolable {
    companion object {
        val mapper = mapperFor<MoveComponent>()
    }

    var speed = 0f

    override fun reset() {
        speed = 0f
    }
}