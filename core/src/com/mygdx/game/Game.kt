package com.mygdx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.screen.GameScreen
import com.mygdx.game.screen.MainScreen
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.freetype.async.registerFreeTypeFontLoaders
import ktx.inject.Context
import ktx.inject.register
import ktx.log.debug
import ktx.log.logger
import ktx.scene2d.Scene2DSkin
import ktx.style.label
import ktx.style.skin

private val log = logger<Game>()

class Game : KtxGame<KtxScreen>() {
    private val context = Context()
    private val assets: AssetStorage by lazy {
        KtxAsync.initiate()
        val assetStorage = AssetStorage()
        assetStorage.registerFreeTypeFontLoaders()
        assetStorage
    }
    private val gameViewport = FitViewport(16.toFloat(), 9.toFloat())
    private val stage: Stage by lazy {
        val result = Stage(FitViewport(640.toFloat(), 480.toFloat()))
        Gdx.input.inputProcessor = result
        result
    }

//    private val fontGenerator = runBlocking {
//        assets.load<FreeTypeFontGenerator>("fonts/LuckiestGuy.ttf")
//    }

    override fun create() {
        context.register {
            bindSingleton(this@Game)
            bindSingleton<Batch>(SpriteBatch())
//            bindSingleton(fontGenerator)
            bindSingleton(assets)
            bindSingleton(OrthographicCamera().apply { setToOrtho(false, 1280f, 720f) })
            bindSingleton(PooledEngine())
            bindSingleton(gameViewport)
            bindSingleton(stage)
            addScreen(MainScreen(inject(), inject(), inject(), inject(), inject()))
            addScreen(GameScreen(inject(), inject(), inject(), inject(), inject(), inject()))
        }
        val assetRefs = gdxArrayOf(
                TextureAtlasAssets.values().map { assets.loadAsync(it.descriptor) },
                FontAsset.values().map { assets.loadAsync(it.descriptor) },
        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()
        }
        Scene2DSkin.defaultSkin = skin {
            label("default") {
                font = BitmapFont()
            }
        }
        setScreen<MainScreen>()
        super.create()
    }

    override fun dispose() {
        context.dispose()
        super.dispose()
    }
}