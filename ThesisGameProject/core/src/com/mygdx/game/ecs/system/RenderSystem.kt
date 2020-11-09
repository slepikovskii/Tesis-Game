package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.SortedIteratingSystem
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.FitViewport
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.GraphicComponent
import com.mygdx.game.ecs.component.TransformComponent
import ktx.ashley.allOf
import ktx.ashley.get
import ktx.graphics.use
import ktx.log.error
import ktx.log.logger

private val LOG = logger<RenderSystem>()

class RenderSystem(
        private val batch: Batch,
        private val gameViewport: Viewport,
        private val uiViewport: FitViewport
) : SortedIteratingSystem(

        allOf(TransformComponent::class, GraphicComponent::class).get(),
        compareBy{entity -> entity[TransformComponent.mapper]}

) {
    override fun update(deltaTime: Float) {
        uiViewport.apply()

        forceSort()
        gameViewport.apply()
        batch.use(gameViewport.camera.combined){
            super.update(deltaTime)
        }

    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
       val transform = entity[TransformComponent.mapper]
        require(transform != null) {"Entity |entity| must have TransformComponent!"}
        val graphic = entity[GraphicComponent.mapper]
        require(graphic != null){"Entity |entity| must have GraphicComponent"}

        if (graphic.sprite.texture ==null) {
            LOG.error({ "Entity has no texture" })
        }
    }
}