package com.miguel.jogoheroisvsbestas.model;

public abstract class Character {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int armor;

    public Character(String name, int health, int armor) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.armor = armor;
    }

    public abstract int calculateAttack(Character opponent);
    public abstract int calculateDefense(int attackPower, Character opponent);

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }

    public void heal(int amount) {
        health = Math.min(maxHealth, health + amount);
    }

    // Getters and Setters
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

    public double getHealthPercentage() {
        return (double) health / maxHealth;
    }
}