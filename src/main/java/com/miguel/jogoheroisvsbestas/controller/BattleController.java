package com.miguel.jogoheroisvsbestas.controller;

import com.miguel.jogoheroisvsbestas.model.*;
import com.miguel.jogoheroisvsbestas.model.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;


public class BattleController {

    private Army heroes;
    private Army beasts;
    private List<String> battleLog;

    @FXML
    private ComboBox<String> hereoTypeCombo;
    @FXML
    private TextField heroNameField;
    @FXML
    private TextField heroHealthField;
    @FXML
    private TextField heroArmorField;

    @FXML
    private ComboBox<String> beastTypeCombo;
    @FXML
    private TextField beastNameField;
    @FXML
    private TextField beastHealthField;
    @FXML
    private TextField beastArmorField;

    @FXML
    private TextArea battleLogArea;

    @FXML
    public void initialize() {
        heroes = new Army(/*"Heróis"*/);
        beasts = new Army(/*"Bestas"*/);
        battleLog = new ArrayList<>();

        hereoTypeCombo.getSelectionModel().selectFirst();
        beastTypeCombo.getSelectionModel().selectFirst();


    }

    @FXML
    public void addHero() {
        try {

            String name = heroNameField.getText();
            int health = Integer.parseInt(heroHealthField.getText());
            int armor = Integer.parseInt(heroArmorField.getText());
            String type = hereoTypeCombo.getValue();

            Character hero = createHero(type, name, health,armor);
            //hero.addCharacter(hero);

            showAlert("Sucesso", "Herói adicionado: " + name, Alert.AlertType.INFORMATION);
            clearHeroFields();

        } catch (NumberFormatException e) {
            showAlert("Erro!", "Por favor, insira valores númericos válidos para vida e armadura", Alert.AlertType.ERROR);
        }

    }

    private void clearHeroFields() {
    }

    private Character createHero(String type, String name, int health, int armor) {
        return null;
    }

    private void showAlert(String s, String s1, Alert.AlertType alertType) {
    }
}
