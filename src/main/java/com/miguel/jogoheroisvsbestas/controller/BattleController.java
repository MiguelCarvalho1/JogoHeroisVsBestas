package com.miguel.jogoheroisvsbestas.controller;

import com.miguel.jogoheroisvsbestas.model.*;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.*;


public class BattleController {

    private Army heroes;
    private Army beasts;
    private List<String> battleLog;

    @FXML private ComboBox<String> hereoTypeCombo;
    @FXML private TextField heroNameField;
    @FXML private TextField heroHealthField;
    @FXML private TextField heroArmorField;

    @FXML private ComboBox<String> beastTypeCombo;
    @FXML private TextField beastNameField;
    @FXML private TextField beastHealthField;
    @FXML private TextField beastArmorField;

    @FXML private TextArea battleLogArea;

}
