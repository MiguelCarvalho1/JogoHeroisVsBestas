package com.miguel.jogoheroisvsbestas.model;

import java.util.*;
import java.util.stream.Collectors;

public class Army {
    private final List<Character> characters;
    private final String name;

    public Army(String name) {
        this.characters = new ArrayList<>();
        this.name = name;
    }

    public void addCharacter(Character character) {
        if (character != null) {
            characters.add(character);
        }
    }

    public boolean removeCharacter(Character character) {
        return characters.remove(character);
    }

    public void removeDeadCharacters() {
        characters.removeIf(c -> !c.isAlive());
    }

    public boolean hasAliveCharacters() {
        return characters.stream().anyMatch(Character::isAlive);
    }

    public List<Character> getCharacters() {
        return new ArrayList<>(characters);
    }

    public List<Character> getAliveCharacters() {
        return characters.stream()
                .filter(Character::isAlive)
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return characters.size();
    }

    public List<String> getCharacterNames() {
        return characters.stream()
                .map(c -> String.format("%s (%d/%d HP, %d ARM)",
                        c.getName(), c.getHealth(), c.getMaxHealth(), c.getArmor()))
                .collect(Collectors.toList());
    }

    public Character getCharacter(int index) {
        if (index >= 0 && index < characters.size()) {
            return characters.get(index);
        }
        return null;
    }
}
