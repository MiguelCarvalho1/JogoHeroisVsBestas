package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe que representa um Orc, um tipo de besta.
 * Os Orcs têm uma defesa especial que reduz a armadura do oponente em 10% ao calcular o dano.
 */
public class Orc extends Beast {

    /**
     * Construtor do Orc.
     * @param name Nome do Orc
     * @param health Vida inicial e máxima
     * @param armor Armadura
     */
    public Orc(String name, int health, int armor) {
        super(name, health, armor);
    }

    /**
     * Calcula a defesa do Orc contra um ataque.
     * Neste caso, reduz a armadura do oponente em 10% ao calcular o dano recebido.
     *
     * @param attackPower Poder de ataque do inimigo
     * @param opponent Personagem que está a atacar
     * @return Dano efetivo após a defesa
     */
    @Override
    public int calculateDefense(int attackPower, Character opponent) {
        int modifiedArmor = (int) (opponent.getArmor() * 0.9); // Reduz armadura do oponente em 10%
        return Math.max(0, attackPower - modifiedArmor); // Garante que o dano não seja negativo
    }
}
