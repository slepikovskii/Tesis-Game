package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import ktx.ashley.mapperFor

class BikeComponent: Component {
    companion object {
        val mapper = mapperFor<BikeComponent>()
    }

    var papers = 0
}