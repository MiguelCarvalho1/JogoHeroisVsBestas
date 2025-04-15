package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe abstrata que representa um Herói.
 * Serve como base para classes como Elfo, Humano, etc.
 * Heróis têm vantagem ao atacar, pois jogam dois dados e escolhem o maior valor.
 */
public abstract class Hero extends Character {

    /**
     * Construtor do herói.
     * @param name Nome do herói
     * @param health Vida inicial e máxima do herói
     * @param armor Armadura do herói
     */
    public Hero(String name, int health, int armor) {
        super(name, health, armor);
    }

    /**
     * Calcula o valor de ataque do herói.
     * Heróis jogam dois dados (0 a 100) e escolhem o maior valor.
     * Isso representa a sua vantagem ofensiva.
     * @param opponent Personagem oponente
     * @return Valor do ataque
     */
    @Override
    public int calculateAttack(Character opponent) {
        int dice1 = (int) (Math.random() * 101); // dado 1
        int dice2 = (int) (Math.random() * 101); // dado 2
        return Math.max(dice1, dice2); // retorna o maior dos dois
    }

    /**
     * Calcula o dano que o herói sofre após aplicar a armadura.
     * Se o ataque for maior que a armadura, o dano é a diferença.
     * Caso contrário, não sofre dano.
     * @param attackPower Poder de ataque do oponente
     * @param opponent Oponente que está atacando
     * @return Dano efetivo a ser aplicado ao herói
     */
    @Override
    public int calculateDefense(int attackPower, Character opponent) {
        return attackPower > armor ? attackPower - armor : 0;
    }
}
