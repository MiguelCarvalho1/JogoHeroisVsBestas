package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe que representa um Humano, um tipo de herói.
 * Humanos utilizam o comportamento padrão de ataque e defesa definido na classe Hero.
 */
public class Human extends Hero {

    /**
     * Construtor do Humano.
     * @param name Nome do Humano
     * @param health Vida inicial e máxima
     * @param armor Armadura
     */
    public Human(String name, int health, int armor) {
        super(name, health, armor);
    }

    // Não é necessário sobrescrever os métodos de ataque ou defesa,
    // pois este herói utiliza o comportamento padrão da classe Hero.
}
