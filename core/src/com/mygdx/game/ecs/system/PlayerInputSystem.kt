package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerInputSystem(private val gameEventManager: GameEventManager) : IteratingSystem(
        allOf(PlayerComponent::class).get()), GameEventListener {

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        gameEventManager.addListener(GameEvent.PaperHit::class, this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            gameEventManager.dispatchEvent(GameEvent.PaperThrown)
        }
    }

    override fun onEvent(event: GameEvent) {
        entities.forEach {
            it[PlayerComponent.mapper]?.apply {
                --papers
            }
        }
    }
}