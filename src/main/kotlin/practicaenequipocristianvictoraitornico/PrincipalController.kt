package practicaenequipocristianvictoraitornico

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.TilePane
import javafx.scene.layout.VBox
import practicaenequipocristianvictoraitornico.routes.RoutesManager

class PrincipalController {
    @FXML
    private lateinit var btnJugadores: Button

    @FXML
    private lateinit var btnEntrenadores: Button

    @FXML
    private lateinit var estadisticasButton: Button

    @FXML
    private lateinit var anchorpane: AnchorPane

    @FXML
    private lateinit var tilePaneTarjetas: TilePane

    @FXML
    private lateinit var hboxposicion: VBox

    @FXML
    private lateinit var PosicionLabel: Label

    @FXML
    private lateinit var NewTeamlabel: Label

    @FXML
    private lateinit var LadoIzquierdo: VBox

    @FXML
    private lateinit var comboBoxPosicion: ComboBox<String>

    @FXML
    private lateinit var acercaDeButton: Button

    @FXML
    private lateinit var salirButton: Button

    @FXML
    fun initialize() {
        acercaDeButton.setOnAction { RoutesManager.showAcercaDe() }
        btnJugadores.setOnAction { RoutesManager.showTarjetasJugadores() }
        salirButton.setOnAction { RoutesManager.onAppExit() }
    }
}