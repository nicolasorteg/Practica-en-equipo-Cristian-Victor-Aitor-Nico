package practicaenequipocristianvictoraitornico.users.controller

import com.github.michaelbull.result.Err
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.stage.Stage
import org.lighthousegames.logging.logging
import org.mindrot.jbcrypt.BCrypt

val logger = logging()

class usersController {
    @FXML
    private lateinit var userName: TextField

    @FXML
    private lateinit var password: PasswordField

    @FXML
    private lateinit var loginButton: Button

    @FXML
    private lateinit var loginMessage: Label

    private val usersDB = mapOf(
        "admin" to BCrypt.hashpw("contraseñasegura", BCrypt.gensalt()),
    )

    fun initialize() {
        loginButton.setOnAction {
            val userName = userName.text.trim()
            val password = password.text

            if (userName.isBlank() || password.isBlank()) {
                loginMessage.text = "¡¡Porfavor rellene todos los campos!!"
                loginMessage.style = "-fx-text-fill: red;"
                return@setOnAction
            }

            val user = usersDB[userName]

            if (user != null && BCrypt.checkpw(password, user)) {
                loginMessage.text = "Inicio de sesión exitoso"
                loginMessage.style = "-fx-text-fill: green;"

                try {
                    val mainLoader = FXMLLoader(javaClass.getResource("/principal-view.fxml"))
                    val mainStage = Stage()
                    mainStage.scene = Scene(mainLoader.load())
                    mainStage.title = "Gestor del New Team"
                    mainStage.isResizable = false
                    mainStage.icons.add(Image(javaClass.getResourceAsStream("/LogoSinFondo.png")))
                    mainStage.show()

                    val stage = loginMessage.scene.window as Stage
                    stage.close()

                } catch (e: Error) {
                    loginMessage.text = "Error al cargar la vista principal"
                    loginMessage.style = "-fx-text-fill: red;"
                    Err("Error al cargar la vista principal")
                }
            } else {
                loginMessage.text = "Usuario o contraseña incorrectos"
                loginMessage.style = "-fx-text-fill: red;"
            }
        }
    }
}