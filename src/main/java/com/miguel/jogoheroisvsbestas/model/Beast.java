package com.miguel.jogoheroisvsbestas.model;

public abstract class Beast extends Character {
    public Beast(String name, int health, int armor) {
        super(name, health, armor);
    }

    @Override
    public int calculateAttack(Character opponent) {
        return  (int) (Math.random() * 91);
    }

    @Override
    public int calculateDefense(int attackPower, Character opponent) {
        return attackPower > armor ? attackPower - armor : 0;
    }
}
