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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class BattleController implements BattleManager.BattleEventListener {

    // Exércitos
    private Army heroes;
    private Army beasts;

    // Log da batalha
    private List<String> battleLog;

    // Campos de input de heróis
    @FXML private ComboBox<String> heroTypeCombo;
    @FXML private TextField heroNameField;
    @FXML private TextField heroHealthField;
    @FXML private TextField heroArmorField;

    // Campos de input de bestas
    @FXML private ComboBox<String> beastTypeCombo;
    @FXML private TextField beastNameField;
    @FXML private TextField beastHealthField;
    @FXML private TextField beastArmorField;

    // Listas de heróis e bestas
    @FXML private ListView<String> heroListView;
    @FXML private ListView<String> beastListView;

    // Área de visualização do log da batalha
    @FXML private TextFlow battleLogFlow;

    // Campo para definir o número máximo de turnos
    @FXML private TextField maxTurnsField;

    // Barras de vida e estatísticas
    @FXML private ProgressBar heroHealthBar;
    @FXML private ProgressBar beastHealthBar;
    @FXML private Label heroStatsLabel;
    @FXML private Label beastStatsLabel;

    // Botões de controlo
    @FXML private Button startBattleButton;
    @FXML private Button resetButton;

    // Áreas com detalhes do personagem selecionado
    @FXML private VBox heroDetailsBox;
    @FXML private VBox beastDetailsBox;

    // Lista observável para atualizar as ListViews
    private ObservableList<String> heroList = FXCollections.observableArrayList();
    private ObservableList<String> beastList = FXCollections.observableArrayList();

    // Referência para o personagem selecionado
    private Character selectedHero;
    private Character selectedBeast;


    /**
     * Método de inicialização chamado pelo JavaFX após carregar o FXML.
     * Configura os componentes iniciais da interface.
     */

    @FXML
    public void initialize() {
        heroes = new Army("Heróis");
        beasts = new Army("Bestas");
        battleLog = new ArrayList<>();

        // Preenche os Combobox com os tipos disponíveis
        heroTypeCombo.setItems(FXCollections.observableArrayList("Elfo", "Hobbit", "Humano"));
        beastTypeCombo.setItems(FXCollections.observableArrayList("Orque", "Troll"));

        heroTypeCombo.getSelectionModel().selectFirst();
        beastTypeCombo.getSelectionModel().selectFirst();

        // Liga as listas observáveis às ListViews
        heroListView.setItems(heroList);
        beastListView.setItems(beastList);

        // Listener para mostrar detalhes do herói selecionado
        heroListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int index = heroListView.getSelectionModel().getSelectedIndex();
                selectedHero = heroes.getCharacter(index);
                updateCharacterDetails(selectedHero, heroDetailsBox, heroHealthBar, heroStatsLabel);
            }
        });

        // Listener para mostrar detalhes da besta selecionada
        beastListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                int index = beastListView.getSelectionModel().getSelectedIndex();
                selectedBeast = beasts.getCharacter(index);
                updateCharacterDetails(selectedBeast, beastDetailsBox, beastHealthBar, beastStatsLabel);
            }
        });

        // Inicializa UI
        heroHealthBar.setProgress(0);
        beastHealthBar.setProgress(0);
        heroStatsLabel.setText("");
        beastStatsLabel.setText("");
    }

    // Atualiza os detalhes de um personagem
    private void updateCharacterDetails(Character character, VBox detailsBox, ProgressBar healthBar, Label statsLabel) {
        if (character != null) {
            healthBar.setProgress(character.getHealthPercentage());
            healthBar.setStyle(getHealthBarStyle(character.getHealthPercentage()));
            statsLabel.setText(String.format("%s\nVida: %d/%d\nArmadura: %d",
                    character.getName(), character.getHealth(), character.getMaxHealth(), character.getArmor()));
        }
    }

    // Define cor da barra de vida
    private String getHealthBarStyle(double percentage) {
        if (percentage > 0.6) return "-fx-accent: green;";
        if (percentage > 0.3) return "-fx-accent: #003366;";
        return "-fx-accent: red;";
    }
    /**
     * Método para adicionar um novo herói ao exército.
     * Valida os campos antes de criar o personagem.
     */
    // Adiciona um novo herói à lista
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

    /**
     * Método para adicionar uma nova besta ao exército.
     * Valida os campos antes de criar o personagem.
     */

    // Adiciona uma nova besta à lista
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

    // Remove herói selecionado
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

    // Remove besta selecionada
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

    // Inicia a batalha
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

        // Desativa botões durante a batalha
        startBattleButton.setDisable(true);
        resetButton.setDisable(true);

        // Limpa log anterior
        battleLogFlow.getChildren().clear();
        battleLog.clear();

        // Inicia batalha
        BattleManager manager = new BattleManager(heroes, beasts, this);
        manager.startBattle(maxTurns);
    }

    // Restaura o estado inicial
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

    // Recebe evento da batalha para mostrar no log
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

    // Atualiza estado visual quando personagem leva dano
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

    // Atualiza estado visual quando personagem morre
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

    // Finaliza a batalha
    @Override
    public void onBattleEnd(boolean heroesWon) {
        javafx.application.Platform.runLater(() -> {
            startBattleButton.setDisable(false);
            resetButton.setDisable(false);
            updateHeroList();
            updateBeastList();
        });
    }

    // Cria instância do herói
    private Character createHero(String type, String name, int health, int armor) {
        switch (type) {
            case "Elfo": return new Elf(name, health, armor);
            case "Hobbit": return new Hobbit(name, health, armor);
            case "Humano": return new Human(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de herói inválido");
        }
    }

    // Cria instância da besta
    private Character createBeast(String type, String name, int health, int armor) {
        switch (type) {
            case "Orque": return new Orc(name, health, armor);
            case "Troll": return new Troll(name, health, armor);
            default: throw new IllegalArgumentException("Tipo de besta inválido");
        }
    }

    // Valida e converte número positivo
    private int parsePositiveInt(String text, String fieldName) throws NumberFormatException {
        int value = Integer.parseInt(text);
        if (value <= 0) {
            throw new NumberFormatException("O valor de " + fieldName + " deve ser positivo.");
        }
        return value;
    }

    // Valida e converte número não-negativo
    private int parseNonNegativeInt(String text, String fieldName) throws NumberFormatException {
        int value = Integer.parseInt(text);
        if (value < 0) {
            throw new NumberFormatException("O valor de " + fieldName + " não pode ser negativo.");
        }
        return value;
    }

    // Atualiza lista de heróis
    private void updateHeroList() {
        heroList.setAll(heroes.getCharacterNames());
    }

    // Atualiza lista de bestas
    private void updateBeastList() {
        beastList.setAll(beasts.getCharacterNames());
    }

    // Limpa campos do formulário de herói
    private void clearHeroFields() {
        heroNameField.clear();
        heroHealthField.clear();
        heroArmorField.clear();
    }

    // Limpa campos do formulário de besta
    private void clearBeastFields() {
        beastNameField.clear();
        beastHealthField.clear();
        beastArmorField.clear();
    }

    // Mostra alerta
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
