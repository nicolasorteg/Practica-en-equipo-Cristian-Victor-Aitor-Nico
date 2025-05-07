package practicaenequipocristianvictoraitornico

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.stage.Stage

class PersonalApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(PersonalApplication::class.java.getResource("practicaenequipocristiancixtoraitornico/hello-view.fxml"))
        val scene = Scene(fxmlLoader.load(), 320.0, 240.0)
        stage.title = "Personal administrator"
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(PersonalApplication::class.java)
}