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

        // Verifica se o arquivo FXML foi encontrado
        if (fxmlLocation == null) {
            throw new IOException("FXML não encontrado em /com/miguel/jogoheroisvsbestas/view/battle.fxml");
        }

        // Carrega o arquivo FXML
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Parent root = fxmlLoader.load();

        // Cria a cena com as dimensões especificadas
        Scene scene = new Scene(root, 1200, 800);

        // Verifica se o CSS existe antes de adicionar
        URL cssUrl = getClass().getResource("/com/miguel/jogoheroisvsbestas/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm()); // Adiciona o CSS à cena
        } else {
            System.out.println("Aviso: style.css não encontrado.");
        }

        // Configura a janela principal
        primaryStage.setTitle("O Senhor dos Anéis - Batalha");
        primaryStage.setScene(scene);
        primaryStage.show(); // Exibe a janela
    }

    public static void main(String[] args) {
        launch(); // Inicia a aplicação JavaFX
    }
}
