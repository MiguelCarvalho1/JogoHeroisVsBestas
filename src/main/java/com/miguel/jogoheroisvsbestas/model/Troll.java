package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe que representa um Troll, um tipo de besta.
 * O Troll herda as funcionalidades básicas da classe Beast, mas não tem nenhuma modificação adicional.
 */
public class Troll extends Beast {

    /**
     * Construtor do Troll.
     * @param name Nome do Troll
     * @param health Vida inicial e máxima
     * @param armor Armadura
     */
    public Troll(String name, int health, int armor) {
        super(name, health, armor); // Chama o construtor da classe base Beast
    }
}
