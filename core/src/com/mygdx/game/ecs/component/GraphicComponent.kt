package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import ktx.ashley.mapperFor

class GraphicComponent : Component {
    companion object {
        val mapper = mapperFor<GraphicComponent>()
    }

    val sprite = Sprite()
    var z = 0
}