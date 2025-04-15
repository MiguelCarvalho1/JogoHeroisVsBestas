package com.miguel.jogoheroisvsbestas.model;

/**
 * Classe abstrata que representa uma personagem (herói ou besta) no jogo.
 * Define atributos e comportamentos comuns a todas as personagens.
 */
public abstract class Character {
    protected String name;      // Nome da personagem
    protected int health;       // Vida atual
    protected int maxHealth;    // Vida máxima
    protected int armor;        // Valor da armadura

    /**
     * Construtor da classe Character.
     * @param name Nome da personagem
     * @param health Valor inicial e máximo de vida
     * @param armor Valor da armadura
     */
    public Character(String name, int health, int armor) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
    }

    /**
     * Método abstrato para calcular o ataque.
     * Deve ser implementado pelas subclasses (Hero, Beast).
     * @param opponent Oponente que vai receber o ataque
     * @return Valor do ataque
     */
    public abstract int calculateAttack(Character opponent);

    /**
     * Método abstrato para calcular a defesa.
     * Deve ser implementado pelas subclasses (Hero, Beast).
     * @param attackPower Valor do ataque recebido
     * @param opponent Oponente que atacou
     * @return Dano efetivo sofrido após aplicar a defesa
     */
    public abstract int calculateDefense(int attackPower, Character opponent);

    /**
     * Verifica se a personagem ainda está viva.
     * @return true se a vida for maior que 0, false caso contrário
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * Aplica dano à personagem, reduzindo a vida.
     * A vida nunca pode ser inferior a 0.
     * @param damage Valor do dano a sofrer
     */
    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    /**
     * Cura a personagem, aumentando a vida.
     * A vida nunca pode ultrapassar a vida máxima.
     * @param amount Quantidade de vida a recuperar
     */
    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    // Getters e Setters

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getArmor() {
        return armor;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    /**
     * Retorna a percentagem de vida atual da personagem.
     * @return Valor entre 0.0 e 1.0
     */
    public double getHealthPercentage() {
        return (double) health / maxHealth;
    }
}
