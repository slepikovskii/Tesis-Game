package screens

import com.mygdx.game.ThesisGame
import ktx.log.debug
import ktx.log.logger
import ktx.app.KtxScreen as KtxScreen1

private val LOG = logger<MainMenu>()

class MainMenu : KtxScreen1 {
    override fun show() {
        LOG.debug { "Main menu shown" }
        super.show()
    }

    override fun render(delta: Float) {
        super.render(delta)
    }

    override fun dispose() {
        super.dispose()
    }
}