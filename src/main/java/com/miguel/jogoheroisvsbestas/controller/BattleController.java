package com.miguel.jogoheroisvsbestas.controller;

import com.miguel.jogoheroisvsbestas.battle.BattleManager;
import com.miguel.jogoheroisvsbestas.model.*;
import com.miguel.jogoheroisvsbestas.model.Character;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BattleController implements BattleManager.BattleEventListener {
    private Army heroes;
    private Army beasts;
    private List<String> battleLog;

    @FXML private ComboBox<String> heroTypeCombo;
    @FXML private TextField heroNameField;
    @FXML private TextField heroHealthField;
    @FXML private TextField heroArmorField;

    @FXML private ComboBox<String> beastTypeCombo;
    @FXML private TextField beastNameField;
    @FXML private TextField beastHealthField;
    @FXML private TextField beastArmorField;

    @FXML private ListView<String> heroListView;
    @FXML private ListView<String> beastListView;
    @FXML private TextFlow battleLogFlow;
    @FXML private TextField maxTurnsField;
    @FXML private ProgressBar heroHealthBar;
    @FXML private ProgressBar beastHealthBar;
    @FXML private Label heroStatsLabel;
    @FXML private Label beastStatsLabel;
    @FXML private Button startBattleButton;
    @FXML private Button resetButton;
    @FXML private VBox heroDetailsBox;
    @FXML private VBox beastDetailsBox;

    private ObservableList<String> heroList = FXCollections.observableArrayList();
    private ObservableList<String> beastList = FXCollections.observableArrayList();
    private Timeline animationTimeline;
    private Character selectedHero;
    private Character selectedBeast;

    @FXML
    public void initialize() {
        heroes = new Army("Heróis");
        beasts = new Army("Bestas");
        battleLog = new ArrayList<>();

        heroTypeCombo.setItems(FXCollections.observableArrayList("Elfo", "Hobbit", "Humano"));
        beastTypeCombo.setItems(FXCollections.observableArrayList("Orque", "Troll"));

        heroTypeCombo.getSelectionModel().selectFirst();
        beastTypeCombo.getSelectionModel().selectFirst();

        heroListView.setItems(heroList);
        beastListView.setItems(beastList);

        // Selection listeners for character details
        heroListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int index = heroListView.getSelectionModel().getSelectedIndex();
                selectedHero = heroes.getCharacter(index);
                updateCharacterDetails(selectedHero, heroDetailsBox, heroHealthBar, heroStatsLabel);
            }
        });

        beastListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int index = beastListView.getSelectionModel().getSelectedIndex();
                selectedBeast = beasts.getCharacter(index);
                updateCharacterDetails(selectedBeast, beastDetailsBox, beastHealthBar, beastStatsLabel);
            }
        });

        // Initialize UI elements
        heroHealthBar.setProgress(0);
        beastHealthBar.setProgress(0);
        heroStatsLabel.setText("");
        beastStatsLabel.setText("");
    }

    private void updateCharacterDetails(Character character, VBox detailsBox, ProgressBar healthBar, Label statsLabel) {
        if (character != null) {
            healthBar.setProgress(character.getHealthPercentage());
            healthBar.setStyle(getHealthBarStyle(character.getHealthPercentage()));
            statsLabel.setText(String.format("%s\nVida: %d/%d\nArmadura: %d",
                    character.getName(), character.getHealth(), character.getMaxHealth(), character.getArmor()));
        }
    }

    private String getHealthBarStyle(double percentage) {
        if (percentage > 0.6) return "-fx-accent: green;";
        if (percentage > 0.3) return "-fx-accent: #003366;";
        return "-fx-accent: red;";
    }

    @FXML
    private void addHero() {
        try {
            String name = heroNameField.getText().trim();
            if (name.isEmpty()) {
                showAlert("Erro!", "Por favor, insira o nome do herói.", Alert.AlertType.ERROR);
                return;
            }

            int health = parsePositiveInt(heroHealthField.getText().trim(), "saúde do herói");
            int armor = parseNonNegativeInt(heroArmorField.getText().trim(), "armadura do herói");

            String type = heroTypeCombo.getValue();
            Character hero = createHero(type, name, health, armor);
            heroes.addCharacter(hero);

            showAlert("Sucesso", "Herói adicionado: " + name, Alert.AlertType.INFORMATION);
            clearHeroFields();
            updateHeroList();
        } catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void addBeast() {
        try {
            String name = beastNameField.getText().trim();
            if (name.isEmpty()) {
                showAlert("Erro!", "Por favor, insira o nome da besta.", Alert.AlertType.ERROR);
                return;
            }

            int health = parsePositiveInt(beastHealthField.getText().trim(), "saúde da besta");
            int armor = parseNonNegativeInt(beastArmorField.getText().trim(), "armadura da besta");

            String type = beastTypeCombo.getValue();
            Character beast = createBeast(type, name, health, armor);
            beasts.addCharacter(beast);

            showAlert("Sucesso", "Besta adicionada: " + name, Alert.AlertType.INFORMATION);
            clearBeastFields();
            updateBeastList();
        } catch (Exception e) {
            showAlert("Erro!", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void removeSelectedHero() {
        int index = heroListView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Character hero = heroes.getCharacter(index);
            if (hero != null) {
                heroes.removeCharacter(hero);
                updateHeroList();
            }
        }
    }

    @FXML
    private void removeSelectedBeast() {
        int index = beastListView.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            Character beast = beasts.getCharacter(index);
            if (beast != null) {
                beasts.removeCharacter(beast);
                updateBeastList();
            }
        }
    }

    @FXML
    private void startBattle() {
        if (heroes.getSize() == 0 || beasts.getSize() == 0) {
            showAlert("Erro!", "Ambos os exércitos precisam ter pelo menos um personagem.", Alert.AlertType.ERROR);
            return;
        }

        String maxTurnsText = maxTurnsField.getText().trim();
        if (maxTurnsText.isEmpty()) {
            showAlert("Erro!", "Por favor, insira o número máximo de turnos.", Alert.AlertType.ERROR);
            return;
        }

        int maxTurns;
        try {
            maxTurns = Integer.parseInt(maxTurnsText);
            if (maxTurns < 1) {
                showAlert("Erro!", "O número de turnos deve ser maior que zero.", Alert.AlertType.ERROR);
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erro!", "Por favor, insira um número válido para os turnos.", Alert.AlertType.ERROR);
            return;
        }

        // Disable buttons during battle
        startBattleButton.setDisable(true);
        resetButton.setDisable(true);

        // Clear previous battle log
        battleLogFlow.getChildren().clear();
        battleLog.clear();

        BattleManager manager = new BattleManager(heroes, beasts, this);
        manager.startBattle(maxTurns);
    }

    @FXML
    private void reset() {
        heroes = new Army("Heróis");
        beasts = new Army("Bestas");
        battleLog.clear();
        battleLogFlow.getChildren().clear();
        heroList.clear();
        beastList.clear();
        heroHealthBar.setProgress(0);
        beastHealthBar.setProgress(0);
        heroStatsLabel.setText("");
        beastStatsLabel.setText("");
        startBattleButton.setDisable(false);
        resetButton.setDisable(false);
    }

    // BattleEventListener implementation
    @Override
    public void onBattleEvent(String event) {
        javafx.application.Platform.runLater(() -> {
            Text text = new Text(event + "\n");

            if (event.contains("morreu")) {
                text.setFill(Color.RED);
                text.setStyle("-fx-font-weight: bold;");
            } else if (event.contains("ataca") || event.contains("contra-ataca")) {
                text.setFill(Color.DARKBLUE);
            } else if (event.contains("VITÓRIA")) {
                text.setFill(Color.GREEN);
                text.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
            } else {
                text.setFill(Color.BLACK);
            }

            battleLogFlow.getChildren().add(text);
        });
    }

    @Override
    public void onCharacterDamaged(Character character, int damage) {
        javafx.application.Platform.runLater(() -> {
            if (character == selectedHero) {
                updateCharacterDetails(character, heroDetailsBox, heroHealthBar, heroStatsLabel);
            } else if (character == selectedBeast) {
                updateCharacterDetails(character, beastDetailsBox, beastHealthBar, beastStatsLabel);
            }
        });
    }

    @Override
    public void onCharacterDied(Character character) {
        javafx.application.Platform.runLater(() -> {
            if (character == selectedHero) {
                selectedHero = null;
                heroHealthBar.setProgress(0);
                heroStatsLabel.setText(character.getName() + " (MORTO)");
            } else if (character == selectedBeast) {
                selectedBeast = null;
                beastHealthBar.setProgress(0);
                beastStatsLabel.setText(character.getName() + " (MORTO)");
            }
        });
    }

    @Override
    public void onBattleEnd(boolean heroesWon) {
        javafx.application.Platform.runLater(() -> {
            startBattleButton.setDisable(false);
            resetButton.setDisable(false);
            updateHeroList();
            updateBeastList();
        });
    }

    private Character createHero(String type, String name, int health, int armor) {
        switch (type) {
            case "Elfo": return new Elf(name, health, armor);
            case "Hobbit": return new Hobbit(name, health, armor);
            case "Humano": return new Human(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de herói inválido");
        }
    }

    private Character createBeast(String type, String name, int health, int armor) {
        switch (type) {
            case "Orque": return new Orc(name, health, armor);
            case "Troll": return new Troll(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de besta inválido");
        }
    }

    private int parsePositiveInt(String text, String fieldName) throws NumberFormatException {
        int value = Integer.parseInt(text);
        if (value <= 0) {
            throw new NumberFormatException("O valor de " + fieldName + " deve ser positivo.");
        }
        return value;
    }

    private int parseNonNegativeInt(String text, String fieldName) throws NumberFormatException {
        int value = Integer.parseInt(text);
        if (value < 0) {
            throw new NumberFormatException("O valor de " + fieldName + " não pode ser negativo.");
        }
        return value;
    }

    private void updateHeroList() {
        heroList.setAll(heroes.getCharacterNames());
    }

    private void updateBeastList() {
        beastList.setAll(beasts.getCharacterNames());
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