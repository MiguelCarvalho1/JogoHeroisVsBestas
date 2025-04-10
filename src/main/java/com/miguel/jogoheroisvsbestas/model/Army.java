package com.miguel.jogoheroisvsbestas.model;

import java.util.*;

public class Army {
    private List<Character> characters;
    private String name;

    public Army( String name) {
        this.characters = new ArrayList<>();
        this.name = name;
    }
    public void addCharacter(Character character) {
        if (character != null) {
            characters.add(character);
        }
    }
    public void removeDeadCharacters(){
        characters.removeIf(c -> !c.isAlive());
    }

    public boolean hasAliveCharacters(){
        return  characters.stream().anyMatch(Character::isAlive);
    }
    public List<Character> getCharacters() {
        return characters;
    }

    public String getName() {
        return name;
    }

    public List<String> getCharacterNames() {
        List<String> names = new ArrayList<>();
        for (Character character : characters) {
            names.add(character.getName());  // Chama o método getName() da classe Character
        }
        return names;
    }
}
