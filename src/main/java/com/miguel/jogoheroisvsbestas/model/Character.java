package com.miguel.jogoheroisvsbestas.model;

public abstract class Character {
    protected String name;
    protected int health;
    protected int armor;

    public Character(String name, int health, int armor) {
        this.name = name;
        this.health = health;
        this.armor = armor;
    }

    public abstract int calculateAttack(Character opponent);
    public abstract int calculateDefense(int attackPower, Character opponent);

    public boolean isAlive(){
        return health > 0;
    }
    public void takeDamage(int damage){
        health -= damage;
        if(health <= 0){
            health = 0;
        }
    }

    public String getName() {
        return name;
    }

    public int getArmor() {
        return armor;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
