package com.miguel.jogoheroisvsbestas.model;

public class Hobbit extends Hero{
    public Hobbit(String name, int health, int armor) {
        super(name, health, armor);
    }

    @Override
    public int calculateAttack(Character opponent) {
        int baseAttack = super.calculateAttack(opponent);
        if(opponent instanceof Troll){
            return Math.max(0, baseAttack - 5);
        }
        return baseAttack;
    }
}
