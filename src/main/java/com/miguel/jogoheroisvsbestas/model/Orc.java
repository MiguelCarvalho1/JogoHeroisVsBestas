package com.miguel.jogoheroisvsbestas.model;

public class Orc extends Beast{
    public Orc(String name, int health, int armor) {
        super(name, health, armor);
    }

    @Override
    public int calculateDefense(int attackPower, Character opponent) {
        int modifiedArmor = (int) (opponent.getArmor() * 0.9);
        return Math.max(0, attackPower - modifiedArmor);
    }
}
