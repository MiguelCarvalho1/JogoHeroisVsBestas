module com.miguel.jogoheroisvsbestas {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.miguel.jogoheroisvsbestas.controller to javafx.fxml;
    exports com.miguel.jogoheroisvsbestas;
    exports com.miguel.jogoheroisvsbestas.controller;
    exports com.miguel.jogoheroisvsbestas.battle;
    opens com.miguel.jogoheroisvsbestas.battle to javafx.fxml;
}
