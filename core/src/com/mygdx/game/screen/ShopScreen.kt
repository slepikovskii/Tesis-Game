package com.mygdx.game.screen

import com.badlogic.ashley.core.PooledEngine
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.utils.Scaling
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.game.Game
import com.mygdx.game.UI.SkinImageButton
import com.mygdx.game.UI.createSkin
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.FontAsset
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.assests.Textures
import com.mygdx.game.event.GameEvent
import com.mygdx.game.event.GameEventManager
import com.mygdx.game.event.Item
import com.mygdx.game.widget.parallaxBackground
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import ktx.actors.onClick
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync
import ktx.collections.gdxArrayOf
import ktx.log.debug
import ktx.log.logger
import ktx.scene2d.*
import kotlin.properties.Delegates

private val log = logger<Game>()

class ShopScreen(
        private val game: Game,
        private val assets: AssetStorage,
        private val engine: PooledEngine,
        private val stage: Stage,
        private val gameViewport: FitViewport,
        private val preferences: Preferences,
        private val gameEventManager: GameEventManager
) : KtxScreen {
    private var money by Delegates.notNull<Int>()

    override fun render(delta: Float) {
        stage.run {
            viewport.apply()
            act()
            draw()
        }
        engine.update(delta)
    }

    override fun show() {
        log.debug { "${this.stage} is shown" }
        money = preferences.getInteger("money")
        super.show()
        val assetRefs = gdxArrayOf(
                TextureAtlasAssets.values().map { assets.loadAsync(it.descriptor) },
                Textures.values().map { assets.loadAsync(it.descriptor) },
                FontAsset.values().map { assets.loadAsync(it.descriptor) },
                Animations.values().map { assets.loadAsync(it.descriptor) }
        ).flatten()
        KtxAsync.launch {
            assetRefs.joinAll()
            createSkin(assets)
        }
        setupUI()
    }

    override fun hide() {
        stage.clear()
    }

    override fun resize(width: Int, height: Int) {
        gameViewport.update(width, height, true)
        stage.viewport.update(width, height, true)
    }

    private fun setupUI() {
        stage.actors {
            parallaxBackground(arrayOf(
                    assets[Textures.ShopBackground.descriptor]

            )).apply {
                heigth = 720f
                width = 1280f
            }
            table {
                defaults().expand()
                pad(20f)
                setFillParent(true)

                image(SkinImageButton.PPIGGYBUTTON.name) {
                    setScaling(Scaling.fit)
                    it.center().prefHeight(140f).prefWidth(210f)
                }

                imageButton(SkinImageButton.MENUBUTTON.name) {
                    it.right()
                    imageCell.maxWidth(150f).maxHeight(150f)
                    onClick {
                        game.setScreen<Menu>()
                    }
                }

                row().colspan(2)
                horizontalGroup {
                    imageButton(SkinImageButton.SHOPCARD1BUTTON.name) {
                        isDisabled = money < 300 || preferences.getInteger("lvl", 1) >= 2
                        imageCell.maxHeight(200f).maxWidth(200f).size(400f)
                        onClick {
                            if (!isDisabled) {
                                money -= 300
                                preferences.putInteger("lvl", 2).putInteger("money", money).flush()
                                gameEventManager.dispatchEvent(GameEvent.ItemBought(Item.LVL2))
                                isDisabled = true
                            }
                        }
                    }
                    imageButton(SkinImageButton.SHOPCARD2BUTTON.name) {
                        isDisabled = money < 500 || preferences.getInteger("lvl", 1) >= 3

                        imageCell.maxHeight(200f).maxWidth(200f).size(400f)
                        onClick {
                            if (!isDisabled) {
                                money -= 500
                                preferences.putInteger("lvl", 3).putInteger("money", money).flush()
                                gameEventManager.dispatchEvent(GameEvent.ItemBought(Item.LVL3))
                                isDisabled = true
                            }
                        }
                    }
                    imageButton(SkinImageButton.SHOPCARD3BUTTON.name) {
                        isDisabled = money < 100

                        imageCell.maxHeight(200f).maxWidth(200f).size(400f)
                        onClick {
                            if (!isDisabled) {
                                money -= 100
                                preferences.putInteger("money", money).flush()
                                gameEventManager.dispatchEvent(GameEvent.ItemBought(Item.BOOST))
                            }
                        }
                    }
                }
                top()
                pack()
            }
        }
    }
}


