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
    private final BattleEventListener eventListener;

    public interface BattleEventListener {
        void onBattleEvent(String event);
        void onCharacterDamaged(Character character, int damage);
        void onCharacterDied(Character character);
        void onBattleEnd(boolean heroesWon);
    }

    public BattleManager(Army heroes, Army beasts, BattleEventListener eventListener) {
        this.heroes = heroes;
        this.beasts = beasts;
        this.battleLog = new ArrayList<>();
        this.eventListener = eventListener;
    }

    public void startBattle(int maxTurns) {
        battleLog.clear();
        logEvent("A batalha começou!!");

        int turn = 1;
        while (turn <= maxTurns && heroes.hasAliveCharacters() && beasts.hasAliveCharacters()) {
            logEvent("\nTurno " + turn + ":");
            fightTurn();
            heroes.removeDeadCharacters();
            beasts.removeDeadCharacters();
            turn++;
        }

        boolean heroesWon = heroes.hasAliveCharacters();
        if (heroesWon) {
            logEvent("\nVITÓRIA DOS HERÓIS!");
        } else {
            logEvent("\nVITÓRIA DAS BESTAS!");
        }

        if (eventListener != null) {
            eventListener.onBattleEnd(heroesWon);
        }
    }

    private void fightTurn() {
        List<Character> heroesList = new ArrayList<>(heroes.getAliveCharacters());
        List<Character> beastsList = new ArrayList<>(beasts.getAliveCharacters());

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
        int turnCounter = 0;
        int maxTurns = 50;

        while (hero.isAlive() && beast.isAlive() && turnCounter < maxTurns) {
            turnCounter++;

            // Hero attack
            int heroAttack = hero.calculateAttack(beast);
            int damageToBeast = beast.calculateDefense(heroAttack, hero);

            if (damageToBeast > 0) {
                beast.takeDamage(damageToBeast);
                logEvent(String.format("%s ataca %s com %d de poder. %s sofre %d de dano.",
                        hero.getName(), beast.getName(), heroAttack, beast.getName(), damageToBeast));

                if (eventListener != null) {
                    eventListener.onCharacterDamaged(beast, damageToBeast);
                }
            } else {
                logEvent(String.format("%s ataca %s mas não consegue causar dano!",
                        hero.getName(), beast.getName()));
            }

            if (!beast.isAlive()) {
                logEvent(beast.getName() + " morreu!");
                if (eventListener != null) {
                    eventListener.onCharacterDied(beast);
                }
                break;
            }


            int beastAttack = beast.calculateAttack(hero);
            int damageToHero = hero.calculateDefense(beastAttack, beast);

            if (damageToHero > 0) {
                hero.takeDamage(damageToHero);
                logEvent(String.format("%s contra-ataca %s com %d de poder. %s sofre %d de dano.",
                        beast.getName(), hero.getName(), beastAttack, hero.getName(), damageToHero));

                if (eventListener != null) {
                    eventListener.onCharacterDamaged(hero, damageToHero);
                }
            } else {
                logEvent(String.format("%s contra-ataca %s mas não consegue causar dano!",
                        beast.getName(), hero.getName()));
            }

            if (!hero.isAlive()) {
                logEvent(hero.getName() + " morreu!");
                if (eventListener != null) {
                    eventListener.onCharacterDied(hero);
                }
            }


        }

        if (turnCounter == maxTurns) {
            logEvent("Batalha terminada por limite de turnos atingido.");
        }
    }

    private void logEvent(String event) {
        battleLog.add(event);
        if (eventListener != null) {
            eventListener.onBattleEvent(event);
        }
    }

    public List<String> getBattleLog() {
        return new ArrayList<>(battleLog);
    }
}