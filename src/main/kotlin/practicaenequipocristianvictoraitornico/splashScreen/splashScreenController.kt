package practicaenequipocristianvictoraitornico.splashScreen

import javafx.animation.PauseTransition
import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.util.Duration

class SplashScreenController : Application() {

    @FXML
    private lateinit var mensajeCarga: Label

    override fun start(primaryStage: Stage) {
        val loader = FXMLLoader(javaClass.getResource("/splashScreen-view.fxml"))
        val root = loader.load<javafx.scene.Parent>()
        val controller = loader.getController<SplashScreenController>()

        val scene = Scene(root)
        primaryStage.scene = scene
        primaryStage.isResizable = false
        primaryStage.show()

        // Esperamos 3 segundos
        val pause = PauseTransition(Duration.seconds(3.0))

        pause.setOnFinished {
            controller.mensajeCarga.text = "¡Listo!"

            val mainLoader = FXMLLoader(javaClass.getResource("/user-view.fxml"))
            val mainStage = Stage()
            mainStage.scene = Scene(mainLoader.load())
            mainStage.title = "Gestor del New Team"
            mainStage.isResizable = false
            mainStage.icons.add(Image(javaClass.getResourceAsStream("/LogoSinFondo.png")))
            mainStage.show()

            primaryStage.close()
        }

        pause.play()
    }
}