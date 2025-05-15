package practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage
import practicaenequipocristianvictoraitornico.routes.RoutesManager
import practicaenequipocristianvictoraitornico.splashScreen.SplashScreenController

class MainApp : Application() {
    override fun start(primaryStage: Stage) {
        RoutesManager.initApp( primaryStage, this)
    }
}

fun main() {
    Application.launch(MainApp::class.java)
}