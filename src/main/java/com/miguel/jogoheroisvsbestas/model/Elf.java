package com.miguel.jogoheroisvsbestas.model;

public class Elf extends Hero{
    public Elf(String name, int health, int armor) {
        super(name, health, armor);
    }

    @Override
    public int calculateAttack(Character opponent) {
        int baseAttack = super.calculateAttack(opponent);
       if(opponent instanceof Orc){
           return baseAttack + 10;
       }
       return baseAttack;
    }
}
