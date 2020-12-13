package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.math.vec2

class TransformComponent : Component, Pool.Poolable {
    companion object {
        val mapper = mapperFor<TransformComponent>()
    }

    val size = vec2(1f, 1f)
    val position = vec2()

    override fun reset() {
        position.set(Vector2.Zero)
        size.set(1f, 1f)
    }

    fun setInitialPosition(x: Float, y: Float) {
        position.set(x, y)
    }
}