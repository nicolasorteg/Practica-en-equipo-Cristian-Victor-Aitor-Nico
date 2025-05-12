package practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

<<<<<<< HEAD
import javafx.application.Application
import javafx.stage.Stage

class Main : Application() {
    override fun start(primaryStage: Stage) {
        SplashScreen().start(primaryStage)
=======
class PersonalApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PersonalApplication::class.java.getResource("practicaenequipocristiancixtoraitornico/hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Personal administrator"
        stage.scene = scene
        stage.show()
>>>>>>> 386ac75d2df570c5dd98f2be271dd21bd23dd0c0
    }
}

fun main() {
<<<<<<< HEAD
    Application.launch(Main::class.java)
=======
    Application.launch(PersonalApplication::class.java)
>>>>>>> 386ac75d2df570c5dd98f2be271dd21bd23dd0c0
}