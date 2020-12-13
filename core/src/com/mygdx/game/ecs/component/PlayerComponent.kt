package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class PlayerComponent: Component, Pool.Poolable {
    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }

    var papers = 0

    override fun reset() {
        papers = 0
    }
}