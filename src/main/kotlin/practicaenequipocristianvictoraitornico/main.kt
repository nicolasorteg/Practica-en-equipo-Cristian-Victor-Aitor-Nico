package practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

import javafx.application.Application
import javafx.stage.Stage

class Main : Application() {
    override fun start(primaryStage: Stage) {
        SplashScreen().start(primaryStage)
    }
}

fun main() {
    Application.launch(Main::class.java)
}