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
    private void addHero() {
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
        }catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }

    }

    @FXML
    private void addBeast(){
        try{
            String name = beastNameField.getText();
            int health = Integer.parseInt(beastHealthField.getText());
            int armor = Integer.parseInt(beastArmorField.getText());
            String type = beastTypeCombo.getValue();

            Character beast = createBeast(type, name, health, armor);
            //beast.addCharacter(beast);

        }catch (NumberFormatException e){
            showAlert("Erro!", "Por favor, insira valores numéricos válidos para vida e armadura.", Alert.AlertType.ERROR);
        }catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void startBattle(){
        battleLog.clear();
        battleLog.add("A batalha começou!!");
        updateBattleLog();

        int turn = 1;
        while(/*heroes.hasAliveCharacters() && beasts.hasAliveCharacters()*/){
            battleLog.add("\n Turno " + turn + ": ");
            fightTurn();
            //heroes.removeDeadCharacters();
            //beasts.removeDeadCharacters();
            updateBattleLog();
            turn++;
        }
        if(/*heroes.hasAliveCharacters()*/){
            battleLog.add("\nVITÓRIA DOS HERÓIS!");
        }else {
            battleLog.add("\nVITÓRIA DAS BESTAS!");
        }
        updateBattleLog();
    }

    @FXML
    private void rest(){
        heroes = new Army(/*"Heróis"*/);
        beasts = new Army(/*"Bestas"*/);
        battleLog.clear();
        battleLogArea.clear();
    }
    private Character createBeast(String type, String name, int health, int armor) {
        switch (type) {
            // case "Orque": return new Orc(name, health, armor);
            // case "Troll": return new Troll(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de besta inválido");
        }
    }
    private Character createHero(String type, String name, int health, int armor) {
        switch (type){
            // case "Elfo": return new Elf(name, health, armor);
            // case "Hobbit": return new Hobbit(name, health, armor);
            // case "Humano": return new Human(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de herói inválido");
        }
    }

    private void fightTurn() {
       // List<Character> heroesList = heroes.getCharacters();
       // List<Character> beastsList = beasts.getCharacters();

    }
    private void fight(Character hero, Character beast){
        int heroAttack = hero.calculateAttack();
        int damageToBeast = beast.calculateDefense(heroAttack, hero);
        beast.takeDamage(damageToBeast);

        String heroAttackMsg = String.format("%s ataca %s com %d de poder. %s sofre %d de dano.",
                hero.getName(), beast.getName(), heroAttack, beast.getName(), damageToBeast);
        battleLog.add(heroAttackMsg);

        if(beast.isAlive()){
            int beastAttack = beast.calculateAttack();
            int damageToHero = hero.calculateDefense(beastAttack, beast);
            hero.takeDamage(damageToHero);

            String beastAttackMsg = String.format("%s contra-ataca %s com %d de poder. %s sofre %d de dano.",
                    beast.getName(), hero.getName(), beastAttack, hero.getName(), damageToHero);
            battleLog.add(beastAttackMsg);
        }

        if (!hero.isAlive()) {
            battleLog.add(hero.getName() + " morreu!");
        }
        if (!beast.isAlive()) {
            battleLog.add(beast.getName() + " morreu!");
        }

    }

    private void updateBattleLog() {
        battleLog.clear();
        for(String log : battleLog){
            battleLogArea.appendText(log + "\n");
        }
    }



    private void clearHeroFields() {
    }



    private void showAlert(String s, String s1, Alert.AlertType alertType) {
    }
}
