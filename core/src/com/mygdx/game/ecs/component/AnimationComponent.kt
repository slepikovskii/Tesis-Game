package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.utils.Pool
import ktx.ashley.mapperFor
import ktx.collections.GdxArray

private const val DEFAULT_FRAME_DURATION = 1 / 20f

enum class AnimationType(
        val atlasKey: String,
        val playMode: Animation.PlayMode = Animation.PlayMode.LOOP,
        val speed: Float = 1f
) {
    NONE(""),
    BIKE_LVL_1("bike_lvl"),
}

class Animation2D(
        val type: AnimationType,
        keyFrames: GdxArray<out TextureRegion>,
        playMode: PlayMode = PlayMode.LOOP,
        speed: Float = 1f
) : Animation<TextureRegion>((DEFAULT_FRAME_DURATION) / speed, keyFrames, playMode)

class AnimationComponent : Component, Pool.Poolable {
    var type = AnimationType.NONE
    var stateTime = 0f
    lateinit var animation: Animation2D

    override fun reset() {
        type = AnimationType.NONE
        stateTime = 0f
    }

    companion object {
        val mapper = mapperFor<AnimationComponent>()
    }
}