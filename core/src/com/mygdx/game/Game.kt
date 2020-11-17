package com.mygdx.game

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.screen.GameScreen
import com.mygdx.game.screen.MainScreen
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.app.KtxGame
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.freetype.async.registerFreeTypeFontLoaders
import ktx.inject.Context
import ktx.inject.register
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
        assetStorage.registerFreeTypeFontLoaders(replaceDefaultBitmapFontLoader = true)
        assetStorage
    }
    private val gameViewport = FitViewport(1280.toFloat(), 720.toFloat())
    private val stage: Stage by lazy {
        val result = Stage(gameViewport)
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
            bindSingleton(assets)
            bindSingleton(PooledEngine())
            bindSingleton(gameViewport)
            bindSingleton(stage)
            addScreen(MainScreen(inject(), inject(), inject(), inject(), inject()))
            addScreen(GameScreen(inject(), inject(), inject(), inject(), inject()))
        }
        val assetRefs = gdxArrayOf(
                TextureAtlasAssets.values().map { assets.loadAsync(it.descriptor) },
                FontAsset.values().map { assets.loadAsync(it.descriptor) },
        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()
            Scene2DSkin.defaultSkin = skin {
                label("default") {
                    font = assets[FontAsset.FONT_DEFAULT.descriptor]
                }
            }
        }
        setScreen<MainScreen>()
        super.create()
    }

    override fun dispose() {
        super.dispose()
    }
}