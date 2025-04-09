package com.miguel.jogoheroisvsbestas.model;

public abstract class Hero  extends Character{
    public Hero(String name, int health, int armor) {
        super(name, health, armor);
    }

    @Override
    public int calculateAttack() {
        int dice1 = (int) (Math.random() * 101);
        int dice2 = (int) (Math.random() * 101);
        return Math.max(dice1, dice2);
    }

    @Override
    public int calculateDefense(int attackPower, Character opponent) {
       return attackPower > armor ? attackPower - armor : 0;
    }
}
