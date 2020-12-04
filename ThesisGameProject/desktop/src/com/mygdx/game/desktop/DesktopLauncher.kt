package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.mygdx.game.ThesisGame



fun main() {
    LwjglApplication(
            ThesisGame(),
            LwjglApplicationConfiguration().apply {
                title = "Thesis Game vol.1"
                width = 9*32
                height = 16 *32
            })


}
