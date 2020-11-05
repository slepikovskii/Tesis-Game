package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class MoveComponent : Component {
    companion object {
        val mapper = mapperFor<MoveComponent>()
    }

    var speed = 0f
}