package com.miguel.jogoheroisvsbestas.controller;

import com.miguel.jogoheroisvsbestas.model.*;
import com.miguel.jogoheroisvsbestas.model.Character;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;


public class BattleController {

    private Army heroes;
    private Army beasts;
    private List<String> battleLog;

    @FXML
    private ComboBox<String> heroTypeCombo;
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
    private ListView<String> heroListView;    // ListView para exibir a lista de heróis
    @FXML
    private ListView<String> beastListView;   // ListView para exibir a lista de bestas

    @FXML
    private TextArea battleLogArea;

    @FXML
    private TextField maxTurnsField;

    private ObservableList<String> heroList = FXCollections.observableArrayList();
    private ObservableList<String> beastList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        heroes = new Army("Heróis");
        beasts = new Army("Bestas");
        battleLog = new ArrayList<>();

        heroTypeCombo.getSelectionModel().selectFirst();
        beastTypeCombo.getSelectionModel().selectFirst();

        heroListView.setItems(heroList);
        beastListView.setItems(beastList);


    }

    @FXML
    private void addHero() {
        try {
            // Verificar se os campos não estão vazios
            String name = heroNameField.getText().trim();
            if (name.isEmpty()) {
                showAlert("Erro!", "Por favor, insira o nome do herói.", Alert.AlertType.ERROR);
                return;
            }

            int health;
            try {
                health = Integer.parseInt(heroHealthField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert("Erro!", "Por favor, insira um valor numérico válido para a saúde do herói.", Alert.AlertType.ERROR);
                return;
            }

            int armor;
            try {
                armor = Integer.parseInt(heroArmorField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert("Erro!", "Por favor, insira um valor numérico válido para a armadura do herói.", Alert.AlertType.ERROR);
                return;
            }

            // Verificar se os valores de saúde e armadura são válidos
            if (health <= 0 || armor < 0) {
                showAlert("Erro!", "Os valores de saúde devem ser positivos e a armadura não pode ser negativa.", Alert.AlertType.ERROR);
                return;
            }

            String type = heroTypeCombo.getValue();
            Character hero = createHero(type, name, health, armor);
            heroes.addCharacter(hero);

            showAlert("Sucesso", "Herói adicionado: " + name, Alert.AlertType.INFORMATION);
            clearHeroFields();

            // Atualizar a lista de heróis na interface
            heroList.setAll(heroes.getCharacterNames());

        } catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void addBeast() {
        try {
            // Verificar se os campos não estão vazios
            String name = beastNameField.getText().trim();
            if (name.isEmpty()) {
                showAlert("Erro!", "Por favor, insira o nome da besta.", Alert.AlertType.ERROR);
                return;
            }

            int health;
            try {
                health = Integer.parseInt(beastHealthField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert("Erro!", "Por favor, insira um valor numérico válido para a saúde da besta.", Alert.AlertType.ERROR);
                return;
            }

            int armor;
            try {
                armor = Integer.parseInt(beastArmorField.getText().trim());
            } catch (NumberFormatException e) {
                showAlert("Erro!", "Por favor, insira um valor numérico válido para a armadura da besta.", Alert.AlertType.ERROR);
                return;
            }

            // Verificar se os valores de saúde e armadura são válidos
            if (health <= 0 || armor < 0) {
                showAlert("Erro!", "Os valores de saúde devem ser positivos e a armadura não pode ser negativa.", Alert.AlertType.ERROR);
                return;
            }

            String type = beastTypeCombo.getValue();
            Character beast = createBeast(type, name, health, armor);
            beasts.addCharacter(beast);

            showAlert("Sucesso", "Besta adicionada: " + name, Alert.AlertType.INFORMATION);
            clearBeastFields();

            // Atualizar a lista de bestas na interface
            beastList.setAll(beasts.getCharacterNames());

        } catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    private void startBattle(){
        battleLog.clear();
        battleLog.add("A batalha começou!!");
        updateBattleLog();

        int maxTurns = Integer.parseInt(maxTurnsField.getText().trim());
        int turn = 1;
        while(turn <= maxTurns && heroes.hasAliveCharacters() && beasts.hasAliveCharacters()){
            battleLog.add("\n Turno " + turn + ": ");
            fightTurn();
            heroes.removeDeadCharacters();
            beasts.removeDeadCharacters();
            updateBattleLog();
            turn++;
        }
        if(heroes.hasAliveCharacters()){
            battleLog.add("\nVITÓRIA DOS HERÓIS!");
        }else {
            battleLog.add("\nVITÓRIA DAS BESTAS!");
        }
        updateBattleLog();
    }

    @FXML
    private void reset(){
        heroes = new Army("Heróis");
        beasts = new Army("Bestas");
        battleLog.clear();
        battleLogArea.clear();
    }
    private Character createBeast(String type, String name, int health, int armor) {
        switch (type) {
             case "Orque": return new Orc(name, health, armor);
            case "Troll": return new Troll(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de besta inválido");
        }
    }
    private Character createHero(String type, String name, int health, int armor) {
        switch (type){
             case "Elfo": return new Elf(name, health, armor);
             case "Hobbit": return new Hobbit(name, health, armor);
            case "Humano": return new Human(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de herói inválido");
        }
    }

    private void fightTurn() {
        List<Character> heroesList = heroes.getCharacters();
        List<Character> beastsList = beasts.getCharacters();



        for (int i = 0; i < Math.min(heroesList.size(), beastsList.size()); i++) {
            Character hero = heroesList.get(i);
            Character beast = beastsList.get(i);


            fight(hero, beast);
        }
    }

    private void fight(Character hero, Character beast) {
        while (hero.isAlive() && beast.isAlive()) {
            // Hero attacks first
            int heroAttack = hero.calculateAttack(beast);
            int damageToBeast = beast.calculateDefense(heroAttack, hero);
            beast.takeDamage(damageToBeast);

            String heroAttackMsg = String.format("%s ataca %s com %d de poder. %s sofre %d de dano.",
                    hero.getName(), beast.getName(), heroAttack, beast.getName(), damageToBeast);
            battleLog.add(heroAttackMsg);

            if (!beast.isAlive()) {
                battleLog.add(beast.getName() + " morreu!");
                break;  // Fim da batalha, a besta morreu
            }

            // Beast attacks if still alive
            int beastAttack = beast.calculateAttack(hero);
            int damageToHero = hero.calculateDefense(beastAttack, beast);
            hero.takeDamage(damageToHero);

            String beastAttackMsg = String.format("%s contra-ataca %s com %d de poder. %s sofre %d de dano.",
                    beast.getName(), hero.getName(), beastAttack, hero.getName(), damageToHero);
            battleLog.add(beastAttackMsg);

            if (!hero.isAlive()) {
                battleLog.add(hero.getName() + " morreu!");
            }
        }
    }

    private void updateBattleLog() {
        battleLogArea.clear();  // Limpar a área de texto da batalha
        for (String logEntry : battleLog) {
            battleLogArea.appendText(logEntry + "\n");  // Adicionar nova entrada no log
        }
    }




    private void clearHeroFields() {
        heroNameField.clear();
        heroHealthField.clear();
        heroArmorField.clear();
    }

    private void clearBeastFields() {
        beastNameField.clear();
        beastHealthField.clear();
        beastArmorField.clear();
    }



    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
