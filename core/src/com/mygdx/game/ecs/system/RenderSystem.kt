package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.assests.Textures
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.assets.async.AssetStorage
import ktx.graphics.use
import kotlin.math.min

class RenderSystem(
        private val assets: AssetStorage,
        private val stage: Stage,
        private val gameViewport: Viewport,
        backgroundTexture: Texture) : SortedIteratingSystem(
        allOf(TransformComponent::class, GraphicComponent::class).get(),
        compareBy { entity: Entity -> entity[GraphicComponent.mapper]?.z }) {
    private val batch = stage.batch
    private val background = Sprite(backgroundTexture)

    override fun update(deltaTime: Float) {
        stage.viewport.apply()
        renderBackground(deltaTime)
        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined) {
            super.update(deltaTime)
        }
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        entity[TransformComponent.mapper]?.let { transform ->
            entity[GraphicComponent.mapper]?.let { render ->
                batch.draw(render.sprite, transform.bounds.x, transform.bounds.y)
            }
        }
    }

    private fun renderBackground(deltaTime: Float) {
        batch.use(stage.camera.combined) {
            background.run {
                setSize(640f, 480f)
                draw(batch)
            }
        }
    }
}