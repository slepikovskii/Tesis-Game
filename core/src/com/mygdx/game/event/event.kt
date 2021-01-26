package com.mygdx.game.event

import com.badlogic.gdx.utils.ObjectMap
import ktx.collections.GdxSet
import kotlin.reflect.KClass

private const val INITIAL_LISTENER_CAPACITY = 8

enum class Item { LVL2, LVL3, BOOST }

sealed class GameEvent {
    object PaperThrown : GameEvent()
    object PaperHit : GameEvent()
    data class PlayerMoved(val speed: Float) : GameEvent()
    data class ItemBought(val item: Item) : GameEvent()
}

interface GameEventListener {
    fun onEvent(event: GameEvent)
}

class GameEventManager {
    private val listeners = ObjectMap<KClass<out GameEvent>, GdxSet<GameEventListener>>()

    fun addListener(type: KClass<out GameEvent>, listener: GameEventListener) {

        var eventListeners = listeners[type]
        if (eventListeners == null) {
            eventListeners = GdxSet(INITIAL_LISTENER_CAPACITY)
            listeners.put(type, eventListeners)
        }

        eventListeners.add(listener)
    }

    fun removeListener(type: KClass<out GameEvent>, listener: GameEventListener) {
        listeners[type].remove(listener)
    }

    /**
     * This function removes the [listener] from all [types][GameEvent]. It is
     * slightly more efficient to use [removeListener] if you know the exact type(s).
     */
    fun removeListener(listener: GameEventListener) {
        ObjectMap.Values(listeners).forEach { it.remove(listener) }
    }

    fun dispatchEvent(event: GameEvent) {
        listeners[event::class]?.forEach { it.onEvent(event) }
    }
}