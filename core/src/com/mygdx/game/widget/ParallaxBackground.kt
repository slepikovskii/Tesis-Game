package com.mygdx.game.widget

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import ktx.scene2d.KWidget
import ktx.scene2d.Scene2dDsl
import ktx.scene2d.actor

@Scene2dDsl
class ParallaxBackground(textures: Array<Texture>) : Actor() {
    private var scroll: Int
    private val layers: Array<Texture> = textures
    private val LAYER_SPEED_DIFFERENCE = 2
    var heigth: Float
    var originX: Int
    var originY: Int
    var rotation: Int
    var srcX = 0
    var srcY: Int
    var flipX: Boolean
    var flipY: Boolean
    private var speed: Int
    fun setSpeed(newSpeed: Int) {
        speed = newSpeed
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha)
        scroll += speed
        for (i in layers.indices) {
            srcX = scroll + i * LAYER_SPEED_DIFFERENCE * scroll
            batch.draw(layers[i], x, y, originX.toFloat(), originY.toFloat(), width, heigth, scaleX, scaleY, rotation.toFloat(), srcX, srcY, layers[i].width, layers[i].height, flipX, flipY)
        }
    }

    init {
        for (element in textures) {
            element.setWrap(Texture.TextureWrap.MirroredRepeat, Texture.TextureWrap.MirroredRepeat)
        }
        scroll = 0
        speed = 1
        srcY = 0
        rotation = 0
        originY = 0
        originX = 0
        y = 0f
        x = 0f
        width = Gdx.graphics.width.toFloat()
        heigth = Gdx.graphics.height.toFloat()
        scaleY = 1f
        scaleX = 1f
        flipY = false
        flipX = false
        zIndex = 0
    }
}

@Scene2dDsl
inline fun <S> KWidget<S>.parallaxBackground(
        textures: Array<Texture>,
//        skin: Skin = Scene2DSkin.defaultSkin,
//        style: String = defaultStyle,
        init: ParallaxBackground.(S) -> Unit = {}
): ParallaxBackground = actor(ParallaxBackground(textures), init)