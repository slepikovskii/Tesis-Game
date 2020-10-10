package com.mygdx.game.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.Array
import com.badlogic.gdx.utils.TimeUtils
import com.mygdx.game.Game
import ktx.app.KtxScreen
import ktx.collections.iterate
import ktx.graphics.use

class GameScreen(val game: Game) : KtxScreen {
    // load the images for the droplet & bucket, 64x64 pixels each
    private val dropImage = Texture(Gdx.files.internal("images/drop.png"))
    private val bucketImage = Texture(Gdx.files.internal("images/bucket.png"))
    // load the drop sound effect and the rain background music
    private val dropSound = Gdx.audio.newSound(Gdx.files.internal("sounds/drop.wav"))
    private val rainMusic = Gdx.audio.newMusic(Gdx.files.internal("music/rain.mp3")).apply { isLooping = true }
    // The camera ensures we can render using our target resolution of 800x480
    //    pixels no matter what the screen resolution is.
    private val camera = OrthographicCamera().apply { setToOrtho(false, 800f, 480f) }
    // create a Rectangle to logically represent the bucket
    // center the bucket horizontally
    // bottom left bucket corner is 20px above
    private val bucket = Rectangle(800f / 2f - 64f / 2f, 20f, 64f, 64f)
    // create the touchPos to store mouse click position
    private val touchPos = Vector3()
    // create the raindrops array and spawn the first raindrop
    private val raindrops = Array<Rectangle>() // gdx, not Kotlin Array
    private var lastDropTime = 0L
    private var dropsGathered = 0

    private fun spawnRaindrop() {
        raindrops.add(Rectangle(MathUtils.random(0f, 800f - 64f), 480f, 64f, 64f))
        lastDropTime = TimeUtils.nanoTime()
    }

    override fun render(delta: Float) {
        // generally good practice to update the camera's matrices once per frame
        camera.update()

        // tell the SpriteBatch to render in the coordinate system specified by the camera.
        game.batch.projectionMatrix = camera.combined

        // begin a new batch and draw the bucket and all drops
        game.batch.use { batch ->
            game.font.draw(batch, "Drops Collected: $dropsGathered", 0f, 480f)
            batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height)
            raindrops.forEach { raindrop -> batch.draw(dropImage, raindrop.x, raindrop.y) }
        }

        // process user input
        // ...

        // move the raindrops, remove any that are beneath the bottom edge of the
        //    screen or that hit the bucket.  In the latter case, play back a sound
        //    effect also
        raindrops.iterate { raindrop, iterator ->
            raindrop.y -= 200 * delta
            if (raindrop.y + 64 < 0) {
                iterator.remove()
            }
            if (raindrop.overlaps(bucket)) {
                dropsGathered++
                dropSound.play()
                iterator.remove()
            }
        }
    }

    override fun show() {
        // start the playback of the background music when the screen is shown
        rainMusic.play()
        spawnRaindrop()
    }

    override fun dispose() {
        dropImage.dispose()
        bucketImage.dispose()
        dropSound.dispose()
        rainMusic.dispose()
    }
}