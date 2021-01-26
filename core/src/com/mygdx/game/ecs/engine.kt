package com.mygdx.game.ecs

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Preferences
import com.badlogic.gdx.utils.viewport.Viewport
import com.mygdx.game.assests.Animations
import com.mygdx.game.assests.TextureAtlasAssets
import com.mygdx.game.ecs.component.*
import ktx.ashley.entity
import ktx.ashley.with
import ktx.assets.async.AssetStorage
import kotlin.math.pow
import kotlin.random.Random

val backgroundObjects = arrayOf("lamp", "tree1", "tree2")

fun Engine.createPlayer(
        assets: AssetStorage,
        viewport: Viewport,
        preferences: Preferences
): Entity {
    val level = preferences.getInteger("lvl", 1)

    return entity {
        with<PlayerComponent> {
            papers = 50
            money = preferences.getInteger("money", 0)
        }
        with<TransformComponent> {
            val atlas = assets[Animations.Lvl1.descriptor]
            val playerGraphicRegion = atlas.regions.first()
            size.set(
                    playerGraphicRegion.regionWidth.toFloat(),
                    playerGraphicRegion.regionHeight.toFloat()
            )
            setInitialPosition(
                    viewport.worldWidth / 2f - playerGraphicRegion.regionWidth / 2f,
                    50f,
            )
        }
        with<MoveComponent> {
            maxSpeed *= 1.5.pow(level).toFloat()
            horAcceleration *= 1.2.pow(level).toFloat()
        }
        with<GraphicComponent> { z = 3 }
        with<AnimationComponent> { type = AnimationType.byAssetName("Lvl$level") }
    }
}

fun Engine.createHouses(assets: AssetStorage, viewport: Viewport) {
    var totalWidth = 0f
    while (totalWidth < viewport.worldWidth) {
        val offset = Random.nextInt(20, 30)
        createHouse(assets, viewport, totalWidth + offset).also {
            totalWidth += it.getComponent(TransformComponent::class.java).size.x + offset
        }
    }
}

fun Engine.createHouse(assets: AssetStorage, viewport: Viewport, offsetX: Float = 0f): Entity {
    val atlas = assets[TextureAtlasAssets.Houses.descriptor]
    val house = atlas.regions.get(Random.nextInt(10))
    val positionY = viewport.worldHeight / 3f

    if (Random.nextBoolean()) {
        createMailbox(assets, offsetX + house.regionWidth.toFloat(), positionY)
    } else {
        createBackgroundObject(assets, offsetX + house.regionWidth.toFloat(), positionY)
    }

    return entity {
        with<GraphicComponent> {
            z = 1
            setSpriteRegion(house)
        }
        with<TransformComponent> {
            size.set(
                    house.regionWidth.toFloat(),
                    house.regionHeight.toFloat()
            )
            setInitialPosition(
                    offsetX,
                    positionY - 11, // TODO: Get rid off magic numbers
            )
        }
    }
}

fun Engine.createMailbox(assets: AssetStorage, positionX: Float, positionY: Float): Entity {
    return entity {
        val atlas = assets[TextureAtlasAssets.GameObjects.descriptor]
        val mailbox = atlas.findRegion("open_mailbox")
        with<GraphicComponent> {
            z = 2
            setSpriteRegion(mailbox)
        }
        with<TransformComponent> {
            size.set(
                    mailbox.originalWidth.toFloat() / 5,
                    mailbox.originalHeight.toFloat() / 5
            )
            setInitialPosition(
                    positionX - mailbox.regionWidth.toFloat() / 5,
                    positionY - 20, // TODO: Get rid off magic numbers
            )
        }
        with<CollisionComponent>()
    }
}

fun Engine.createBackgroundObject(assets: AssetStorage, positionX: Float, positionY: Float): Entity {
    return entity {
        val atlas = assets[TextureAtlasAssets.GameObjects.descriptor]
        val lamp = atlas.findRegion(backgroundObjects.random())
        with<GraphicComponent> {
            z = 2
            setSpriteRegion(lamp)
        }
        with<TransformComponent> {
            size.set(
                    lamp.originalWidth.toFloat() / 2,
                    lamp.originalHeight.toFloat() / 2
            )
            setInitialPosition(
                    positionX - lamp.regionWidth / 4f,
                    positionY - 60
            )
        }
    }
}