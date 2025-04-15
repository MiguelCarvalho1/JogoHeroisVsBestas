package com.miguel.jogoheroisvsbestas.battle;

import com.miguel.jogoheroisvsbestas.model.*;
import com.miguel.jogoheroisvsbestas.model.Character;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe responsável por gerir a batalha entre heróis e bestas.
 * Controla o fluxo da batalha, turnos, ataques e eventos relacionados.
 */
public class BattleManager {
    private final Army heroes; // Exército dos heróis
    private final Army beasts; // Exército das bestas
    private final List<String> battleLog; // Registo dos eventos da batalha
    private final BattleEventListener eventListener; // Ouvinte de eventos da batalha

    /**
     * Interface para ouvir eventos da batalha.
     * Permite que outras classes reajam aos eventos da batalha.
     */
    public interface BattleEventListener {
        void onBattleEvent(String event); // Evento genérico da batalha
        void onCharacterDamaged(Character character, int damage); // Personagem sofreu dano
        void onCharacterDied(Character character); // Personagem morreu
        void onBattleEnd(boolean heroesWon); // Batalha terminou
    }

    /**
     * Construtor do BattleManager.
     * @param heroes Exército dos heróis
     * @param beasts Exército das bestas
     * @param eventListener Ouvinte de eventos (pode ser null)
     */
    public BattleManager(Army heroes, Army beasts, BattleEventListener eventListener) {
        this.heroes = heroes;
        this.beasts = beasts;
        this.battleLog = new ArrayList<>();
        this.eventListener = eventListener;
    }

    /**
     * Inicia a batalha com um número máximo de turnos.
     * @param maxTurns Número máximo de turnos para a batalha
     */
    public void startBattle(int maxTurns) {
        battleLog.clear();
        logEvent("A batalha começou!!");

        int turn = 1;
        // Executa turnos até atingir o máximo ou um dos exércitos ser derrotado
        while (turn <= maxTurns && heroes.hasAliveCharacters() && beasts.hasAliveCharacters()) {
            logEvent("\nTurno " + turn + ":");
            fightTurn();
            // Remove personagens mortos após cada turno
            heroes.removeDeadCharacters();
            beasts.removeDeadCharacters();
            turn++;
        }

        // Determina o vencedor
        boolean heroesWon = heroes.hasAliveCharacters();
        if (heroesWon) {
            logEvent("\nVITÓRIA DOS HERÓIS!");
        } else {
            logEvent("\nVITÓRIA DAS BESTAS!");
        }

        // Notifica o fim da batalha
        if (eventListener != null) {
            eventListener.onBattleEnd(heroesWon);
        }
    }

    /**
     * Executa um turno de batalha, emparelhando heróis e bestas aleatoriamente.
     */
    private void fightTurn() {
        // Obtém listas aleatórias de personagens vivos
        List<Character> heroesList = new ArrayList<>(heroes.getAliveCharacters());
        List<Character> beastsList = new ArrayList<>(beasts.getAliveCharacters());

        Collections.shuffle(heroesList);
        Collections.shuffle(beastsList);

        // Determina quantos pares podem lutar neste turno
        int pairs = Math.min(heroesList.size(), beastsList.size());

        // Cada par luta individualmente
        for (int i = 0; i < pairs; i++) {
            Character hero = heroesList.get(i);
            Character beast = beastsList.get(i);
            fight(hero, beast);
        }
    }

    /**
     * Simula um combate individual entre um herói e uma besta.
     * @param hero O herói
     * @param beast A besta
     */
    private void fight(Character hero, Character beast) {
        int turnCounter = 0;
        int maxTurns = 50; // Limite de turnos para evitar ciclos infinitos

        // Continua até um morrer ou atingir o limite de turnos
        while (hero.isAlive() && beast.isAlive() && turnCounter < maxTurns) {
            turnCounter++;

            // Ataque do herói
            int heroAttack = hero.calculateAttack(beast);
            int damageToBeast = beast.calculateDefense(heroAttack, hero);

            if (damageToBeast > 0) {
                beast.takeDamage(damageToBeast);
                logEvent(String.format("%s ataca %s com %d de poder. %s sofre %d de dano.",
                        hero.getName(), beast.getName(), heroAttack, beast.getName(), damageToBeast));

                // Notifica dano
                if (eventListener != null) {
                    eventListener.onCharacterDamaged(beast, damageToBeast);
                }
            } else {
                logEvent(String.format("%s ataca %s mas não consegue causar dano!",
                        hero.getName(), beast.getName()));
            }

            // Verifica se a besta morreu
            if (!beast.isAlive()) {
                logEvent(beast.getName() + " morreu!");
                if (eventListener != null) {
                    eventListener.onCharacterDied(beast);
                }
                break;
            }

            // Ataque da besta
            int beastAttack = beast.calculateAttack(hero);
            int damageToHero = hero.calculateDefense(beastAttack, beast);

            if (damageToHero > 0) {
                hero.takeDamage(damageToHero);
                logEvent(String.format("%s contra-ataca %s com %d de poder. %s sofre %d de dano.",
                        beast.getName(), hero.getName(), beastAttack, hero.getName(), damageToHero));

                // Notifica dano
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

    /**
     * Regista um evento no log da batalha e notifica o ouvinte.
     * @param event Mensagem do evento a registar
     */
    private void logEvent(String event) {
        battleLog.add(event);
        if (eventListener != null) {
            eventListener.onBattleEvent(event);
        }
    }

    /**
     * Obtém o registo completo da batalha.
     * @return Lista com todos os eventos da batalha
     */
    public List<String> getBattleLog() {
        return new ArrayList<>(battleLog);
    }
}