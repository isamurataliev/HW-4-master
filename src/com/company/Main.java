package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {160, 250, 170, 220, 100, 100, 210};
    public static int[] heroesDamage = {20, 15, 10, 0, 5, 15, 10};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            round();
        }
    }

    public static void medic(){
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == 3 && heroesHealth[3] > 0){
                continue;
            } else if (heroesHealth[i] <= 100 && heroesHealth[3] > 0){
                heroesHealth[i] = heroesHealth[i] + 10;
                System.out.println("The Medic helped: " + heroesAttackType[i]);
                break;
            }
        }
    }

    public static void golem () {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] == 4) {
                continue;
            }
            if (heroesHealth[i] > 0 && heroesHealth[4] > 0) {
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[4] -= bossDamage / 5;
            }

        }
        System.out.println("Голем компенсировал 1/5 урона");
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);// 0,1,2
        for (int i = randomIndex; i < heroesAttackType.length; i++) {
            if (randomIndex == 3){
                continue;
            } else {
                bossDefenceType = heroesAttackType[randomIndex];
            }
        }
        System.out.println("Boss chose: " + bossDefenceType);
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        medic();
        golem();
        printStatistics();
    }

    public static void bossHits() {
        Random random = new Random();
        boolean lucky = random.nextBoolean();
        Random random1 = new Random();
        boolean thor = random.nextBoolean();
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
        for (int i = 0; i < heroesHealth.length; i++) {
            if (lucky == true){
                heroesHealth[5] += 40;
                break;
            }
            for (int k = 0; k < heroesHealth.length; k++) {
                if (thor == true){
                    bossDamage = 0;
                }else {
                    bossDamage = 50;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesHealth[i] == 6) {
                    bossHealth = bossHealth - bossDamage / 2 * heroesDamage[i];
                }
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i]; //  bossHealth -= heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND -------------");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }
}
