package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerInputSystem(private val gameEventManager: GameEventManager) : IteratingSystem(
        allOf(PlayerComponent::class).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        val playerCmp = entity[PlayerComponent.mapper]
        require(playerCmp != null) { "Entity |entity| must have an PlayerComponent. entity=$entity" }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && playerCmp.papers > 0) {
            playerCmp.let {
                --it.papers
            }
            gameEventManager.dispatchEvent(GameEvent.PaperThrown)
        }
    }
}