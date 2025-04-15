package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe que representa um Hobbit, um tipo de herói.
 * Hobbits têm desvantagem ao atacar Trolls (menos 5 no ataque).
 */
public class Hobbit extends Hero {

    /**
     * Construtor do Hobbit.
     * @param name Nome do Hobbit
     * @param health Vida inicial e máxima
     * @param armor Armadura
     */
    public Hobbit(String name, int health, int armor) {
        super(name, health, armor);
    }

    /**
     * Calcula o valor de ataque do Hobbit.
     * Usa o ataque base da classe Hero, mas se o oponente for um Troll,
     * o ataque é reduzido em 5 pontos (com mínimo de 0).
     * @param opponent Oponente a ser atacado
     * @return Valor final do ataque
     */
    @Override
    public int calculateAttack(Character opponent) {
        int baseAttack = super.calculateAttack(opponent);

        // Desvantagem contra Trolls: perde 5 de ataque
        if (opponent instanceof Troll) {
            return Math.max(0, baseAttack - 5);
        }

        return baseAttack;
    }
}
