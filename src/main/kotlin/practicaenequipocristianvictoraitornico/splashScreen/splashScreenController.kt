package practicaenequipocristianvictoraitornico.splashScreen

import javafx.animation.PauseTransition
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import javafx.util.Duration

class SplashScreenController : Application() {
    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/splashScreen-view.fxml"))
        val scene = Scene(loader.load())

        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.show()

        val pause = PauseTransition(Duration.seconds(3.0))
        pause.setOnFinished {
            val mainLoader = FXMLLoader(javaClass.getResource("/principal-view.fxml"))
            val mainStage = Stage()
            mainStage.scene = Scene(mainLoader.load())
            mainStage.title = "Gestor del New Team"
            mainStage.show()
            primaryStage.close()
        }
        pause.play()
    }
}