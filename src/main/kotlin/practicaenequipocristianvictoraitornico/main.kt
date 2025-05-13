package practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import practicaenequipocristianvictoraitornico.splashScreen.SplashScreenController

class Main : Application() {
    override fun start(primaryStage: Stage) {
        SplashScreenController().start(primaryStage)

class PersonalApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PersonalApplication::class.java.getResource("practicaenequipocristiancixtoraitornico/principal-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Personal administrator"
        stage.scene = scene
        stage.show()

    }
}

fun main() {
    Application.launch(Main::class.java)

    Application.launch(PersonalApplication::class.java)
}
    }
}