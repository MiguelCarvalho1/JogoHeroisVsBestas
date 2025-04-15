package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe que representa um Elfo, uma subclasse de Hero.
 * Elfos têm vantagem contra Orcs, causando mais dano a eles.
 */
public class Elf extends Hero {

    /**
     * Construtor do Elfo.
     * @param name Nome do elfo
     * @param health Vida inicial e máxima do elfo
     * @param armor Armadura do elfo
     */
    public Elf(String name, int health, int armor) {
        super(name, health, armor);
    }

    /**
     * Calcula o ataque do elfo.
     * Se o oponente for um Orc, o ataque é aumentado em 10 unidades.
     * @param opponent Personagem oponente
     * @return Valor do ataque
     */
    @Override
    public int calculateAttack(Character opponent) {
        int baseAttack = super.calculateAttack(opponent); // ataque base do herói
        if (opponent instanceof Orc) {
            return baseAttack + 10; // bônus contra Orcs
        }
        return baseAttack;
    }
}
