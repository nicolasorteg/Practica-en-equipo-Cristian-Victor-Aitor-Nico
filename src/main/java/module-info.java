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
    requires open;
    requires org.jdbi.v3.kotlin;
    requires jbcrypt;
    requires net.devrieze.xmlutil.core;
    requires java.desktop;


    opens practicaenequipocristianvictoraitornico to javafx.fxml;
    exports practicaenequipocristianvictoraitornico;

}