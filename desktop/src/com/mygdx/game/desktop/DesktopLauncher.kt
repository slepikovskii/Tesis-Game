package com.mygdx.game.desktop

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.mygdx.game.Game

object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        Lwjgl3Application(Game(), Lwjgl3ApplicationConfiguration().apply {
            setTitle("Thesis game")
            setWindowSizeLimits(640, 360, -1, -1)
            setWindowedMode(1280, 720)
        })
    }
}