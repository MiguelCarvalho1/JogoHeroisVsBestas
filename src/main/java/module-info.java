module com.miguel.jogoheroisvsbestas {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.miguel.jogoheroisvsbestas to javafx.fxml;
    exports com.miguel.jogoheroisvsbestas;
}