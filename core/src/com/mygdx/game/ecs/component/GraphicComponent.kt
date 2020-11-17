package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor

class GraphicComponent : Component, Pool.Poolable {
    companion object {
        val mapper = mapperFor<GraphicComponent>()
    }

    val sprite = Sprite()
    var z = 0

    override fun reset() {
        sprite.texture = null
    }

    fun setSpriteRegion(region: TextureRegion) {
        sprite.run {
            setRegion(region)
            setSize(region.regionWidth.toFloat(), region.regionHeight.toFloat())
            setOriginCenter()
        }
    }
}