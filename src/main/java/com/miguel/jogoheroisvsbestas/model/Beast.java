package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe abstrata que representa uma besta no jogo.
 * Herda da classe Character e define o comportamento comum a todas as bestas.
 */
public abstract class Beast extends Character {

    /**
     * Construtor da classe Beast.
     * @param name Nome da besta
     * @param health Vida inicial da besta
     * @param armor Armadura da besta
     */
    public Beast(String name, int health, int armor) {
        super(name, health, armor);
    }

    /**
     * Calcula o valor de ataque da besta de forma aleatória entre 0 e 90 (inclusive).
     * @param opponent Oponente contra o qual o ataque está a ser realizado
     * @return Valor de ataque
     */
    @Override
    public int calculateAttack(Character opponent) {
        return (int) (Math.random() * 91); // Valor entre 0 e 90
    }

    /**
     * Calcula a defesa da besta com base na sua armadura.
     * Se o poder de ataque do inimigo for superior à armadura, retorna o dano efetivo.
     * Caso contrário, o dano é 0.
     * @param attackPower Poder de ataque do oponente
     * @param opponent Oponente que está a atacar
     * @return Dano real causado à besta
     */
    @Override
    public int calculateDefense(int attackPower, Character opponent) {
        return attackPower > armor ? attackPower - armor : 0;
    }
}
