package practicaenequipocristianvictoraitornico.users.controller

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import org.lighthousegames.logging.logging
import org.mindrot.jbcrypt.BCrypt
import practicaenequipocristianvictoraitornico.users.exception.UsersException

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
            } else {
                loginMessage.text = "Usuario o contraseña incorrectos"
                loginMessage.style = "-fx-text-fill: red;"
            }
        }
    }
}