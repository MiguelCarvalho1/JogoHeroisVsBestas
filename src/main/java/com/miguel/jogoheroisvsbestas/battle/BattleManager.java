package com.miguel.jogoheroisvsbestas.battle;

import com.miguel.jogoheroisvsbestas.model.*;
import com.miguel.jogoheroisvsbestas.model.Character;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleManager {

    private final Army heroes;
    private final Army beasts;
    private final List<String> battleLog;

    public BattleManager(Army heroes, Army beasts) {
        this.heroes = heroes;
        this.beasts = beasts;
        this.battleLog = new ArrayList<>();
    }

    public void startBattle(int maxTurns) {
        battleLog.clear();
        battleLog.add("A batalha começou!!");

        int turn = 1;
        while (turn <= maxTurns && heroes.hasAliveCharacters() && beasts.hasAliveCharacters()) {
            battleLog.add("\nTurno " + turn + ":");
            fightTurn();
            heroes.removeDeadCharacters();
            beasts.removeDeadCharacters();
            turn++;
        }

        if (heroes.hasAliveCharacters()) {
            battleLog.add("\nVITÓRIA DOS HERÓIS!");
        } else {
            battleLog.add("\nVITÓRIA DAS BESTAS!");
        }
    }

    private void fightTurn() {
        List<Character> heroesList = new ArrayList<>(heroes.getCharacters());
        List<Character> beastsList = new ArrayList<>(beasts.getCharacters());

        Collections.shuffle(heroesList);
        Collections.shuffle(beastsList);

        int pairs = Math.min(heroesList.size(), beastsList.size());

        for (int i = 0; i < pairs; i++) {
            Character hero = heroesList.get(i);
            Character beast = beastsList.get(i);
            fight(hero, beast);
        }
    }

    private void fight(Character hero, Character beast) {
        while (hero.isAlive() && beast.isAlive()) {
            int heroAttack = hero.calculateAttack(beast);
            int damageToBeast = beast.calculateDefense(heroAttack, hero);
            beast.takeDamage(damageToBeast);

            battleLog.add(String.format("%s ataca %s com %d de poder. %s sofre %d de dano.",
                    hero.getName(), beast.getName(), heroAttack, beast.getName(), damageToBeast));

            if (!beast.isAlive()) {
                battleLog.add(beast.getName() + " morreu!");
                break;
            }

            int beastAttack = beast.calculateAttack(hero);
            int damageToHero = hero.calculateDefense(beastAttack, beast);
            hero.takeDamage(damageToHero);

            battleLog.add(String.format("%s contra-ataca %s com %d de poder. %s sofre %d de dano.",
                    beast.getName(), hero.getName(), beastAttack, hero.getName(), damageToHero));

            if (!hero.isAlive()) {
                battleLog.add(hero.getName() + " morreu!");
            }
        }
    }

    public List<String> getBattleLog() {
        return battleLog;
    }
}
