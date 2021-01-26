package com.mygdx.game.ecs.component

import com.badlogic.ashley.core.Component
import com.badlogic.gdx.utils.Pool
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventListener
import com.mygdx.game.event.Item
import ktx.ashley.mapperFor

class PlayerComponent : Component, Pool.Poolable, GameEventListener {
    companion object {
        val mapper = mapperFor<PlayerComponent>()
    }

    var papers = 0
    var money = 0

    override fun reset() {
        papers = 0
        money = 0
    }

    override fun onEvent(event: GameEvent) {
        if (event is GameEvent.ItemBought) {
            when (event.item) {
                Item.LVL2 -> {
                    money -= 300
                }
                Item.LVL3 -> {
                    money -= 500
                }
            }
        }
    }
}