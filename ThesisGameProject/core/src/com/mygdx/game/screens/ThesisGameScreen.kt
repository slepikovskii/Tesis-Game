package com.mygdx.game.screens

import com.badlogic.ashley.core.Engine
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.ThesisGame
import ktx.app.KtxScreen

abstract class ThesisGameScreen(
        val game: ThesisGame,
        //val batch: Batch = game.batch,
        val gameViewport: Viewport = game.gameViewport,
        val uiViewport: Viewport = game.uiViewport,
        val engine : Engine = game.engine
): KtxScreen {

}