package com.miguel.jogoheroisvsbestas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Caminho correto para o FXML
        URL fxmlLocation = getClass().getResource("view/battle.fxml");
        if (fxmlLocation == null) {
            throw new IOException("FXML não encontrado em /com/miguel/jogoheroisvsbestas/view/battle.fxml");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();

        Scene scene = new Scene(root, 800, 600);

        // Verifica se o CSS existe antes de adicionar
        URL cssUrl = getClass().getResource("style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.out.println("Aviso: style.css não encontrado.");
        }

        primaryStage.setTitle("O Senhor dos Anéis - Batalha");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
