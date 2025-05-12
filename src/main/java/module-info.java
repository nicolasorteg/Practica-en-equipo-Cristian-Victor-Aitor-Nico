module org.example.practicaenequipocristianvictoraitornico {
    requires javafx.controls;
    requires javafx.fxml;
    requires logging.jvm;
    requires net.devrieze.xmlutil.serialization;
    requires kotlin.result.jvm;
    requires kotlinx.serialization.json;
    requires com.github.benmanes.caffeine;
    requires org.jdbi.v3.sqlobject;
    requires org.jdbi.v3.core;
<<<<<<< HEAD
    requires open;
=======
    requires org.jdbi.v3.kotlin;
>>>>>>> 3079b65e4e8805aef7967930d060bf1b6a3ca7cc


    opens practicaenequipocristianvictoraitornico to javafx.fxml;
    exports practicaenequipocristianvictoraitornico;

}