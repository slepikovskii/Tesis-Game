package com.mygdx.game.screens

import com.badlogic.gdx.graphics.g2d.Batch
import com.mygdx.game.ThesisGame
import ktx.app.KtxScreen

abstract class ThesisGameScreen(val game: ThesisGame): KtxScreen {
    val batch = game.Batch
}