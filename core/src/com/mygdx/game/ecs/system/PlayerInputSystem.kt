package com.mygdx.game.ecs.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ecs.component.AnimationComponent
import com.mygdx.game.ecs.component.AnimationType
import com.mygdx.game.ecs.component.PlayerComponent
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.GameEventManager
import ktx.ashley.allOf
import ktx.ashley.get

class PlayerInputSystem(private val gameEventManager: GameEventManager, viewport: Viewport,
        private val preferences: Preferences) : IteratingSystem(
        allOf(PlayerComponent::class, AnimationComponent::class).get()), GameEventListener {

    private val part = viewport.screenWidth / 3
    private val center = part..part * 2

    override fun addedToEngine(engine: Engine?) {
        super.addedToEngine(engine)
        gameEventManager.addListener(GameEvent.PaperHit::class, this)
    }

    override fun processEntity(entity: Entity, deltaTime: Float) {

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || (Gdx.input.justTouched() && Gdx.input.x in center)) {
            gameEventManager.dispatchEvent(GameEvent.PaperThrown)
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            preferences.putString("lvl", "Lvl2")
            entity[AnimationComponent.mapper]?.type = AnimationType.BIKE_LVL_2
            preferences.flush()
        }

    }

    override fun onEvent(event: GameEvent) {
        entities.forEach {
            it[PlayerComponent.mapper]?.apply {
                --papers
                money += 10
            }
        }
    }
}