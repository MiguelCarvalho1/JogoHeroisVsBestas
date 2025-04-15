package com.miguel.jogoheroisvsbestas.model;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que representa um exército (conjunto de personagens).
 * Pode ser utilizado tanto para o exército dos heróis como das bestas.
 */
public class Army {
    // Lista que guarda os personagens pertencentes a este exército
    private final List<Character> characters;
    // Nome do exército (ex: "Heróis" ou "Bestas")
    private final String name;

    /**
     * Construtor que inicializa o exército com um nome.
     * @param name Nome do exército
     */
    public Army(String name) {
        this.characters = new ArrayList<>();
        this.name = name;
    }

    /**
     * Adiciona um personagem ao exército.
     * @param character Personagem a adicionar
     */
    public void addCharacter(Character character) {
        if (character != null) {
            characters.add(character);
        }
    }

    /**
     * Remove um personagem específico do exército.
     * @param character Personagem a remover
     * @return true se o personagem foi removido, false caso contrário
     */
    public boolean removeCharacter(Character character) {
        return characters.remove(character);
    }

    /**
     * Remove todos os personagens que estão mortos (vida ≤ 0) do exército.
     */
    public void removeDeadCharacters() {
        characters.removeIf(c -> !c.isAlive());
    }

    /**
     * Verifica se o exército ainda tem personagens vivos.
     * @return true se pelo menos um personagem está vivo, false se todos estão mortos
     */
    public boolean hasAliveCharacters() {
        return characters.stream().anyMatch(Character::isAlive);
    }

    /**
     * Retorna uma cópia da lista de personagens (vivos e mortos).
     * @return Lista de personagens
     */
    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    /**
     * Retorna uma lista apenas com os personagens vivos.
     * @return Lista de personagens vivos
     */
    public List<Character> getAliveCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    /**
     * Obtém o nome do exército.
     * @return Nome do exército
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém o número total de personagens no exército.
     * @return Tamanho do exército
     */
    public int getSize() {
        return characters.size();
    }

    /**
     * Devolve uma lista com descrições dos personagens no formato:
     * "Nome (vida atual/vida máxima VD, armadura ARM)"
     * @return Lista de descrições dos personagens
     */
    public List<String> getCharacterNames() {
        return characters.stream()
                .map(c -> String.format("%s (%d/%d VD, %d ARM)",
                        c.getName(), c.getHealth(), c.getMaxHealth(), c.getArmor()))
                .collect(Collectors.toList());
    }

    /**
     * Obtém um personagem com base no índice fornecido.
     * @param index Índice do personagem
     * @return Personagem correspondente, ou null se o índice for inválido
     */
    public Character getCharacter(int index) {
        if (index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
}
