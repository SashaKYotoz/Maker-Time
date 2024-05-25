package objects;

import java.util.Random;
import java.util.Scanner;

public class Gladiator {
    public int XP_LEVEL_CAPACITY = 100;
    private String name;
    private int actualHP;
    private int constitution;
    private int strength;
    private int xp;
    private int dexterity;
    private int level;
    private int charisma;
    private final int basicAttack;
    private final int basicHp;
    private final boolean haveShield;

    public Gladiator(
            String name,
            int constitution, int strength,
            int xp, int dexterity,
            int level, int charisma,
            int basicAttack, int basicHp,
            boolean haveShield
    ) {
        this.name = name;
        this.constitution = constitution;
        this.strength = strength;
        this.xp = xp;
        this.dexterity = dexterity;
        this.level = level;
        this.charisma = charisma;
        this.basicAttack = basicAttack;
        this.basicHp = basicHp;
        this.haveShield = haveShield;
        this.actualHP = Math.round(this.basicHp * (this.constitution * 2.5f));
    }
    public String getName(){
        return this.name;
    }
    public int getConstitution() {
        return constitution;
    }

    public int getStrength() {
        return strength;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
        if (this.xp > this.XP_LEVEL_CAPACITY)
            levelUp();
    }

    public int getBasicHp() {
        return basicHp;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getLevel() {
        return level;
    }

    public void levelUp() {
        Scanner scanner = new Scanner(System.in);
        this.level++;
        this.xp = 0;
        int remainedBonusPoints = this.level;
        this.XP_LEVEL_CAPACITY = this.XP_LEVEL_CAPACITY * level;
        System.out.printf("\n Choose characteristic to improve (points of improvements remained: %s)%n",remainedBonusPoints);
        System.out.println("1.Constitution \n2.Strength \n3.Dexterity\n4.Charisma");
        while (remainedBonusPoints > 0){
            System.out.printf("\nPoints of improvements remained: %s)%n",remainedBonusPoints);
            switch (scanner.nextInt()){
                default -> constitution++;
                case 2 -> strength++;
                case 3 -> dexterity++;
                case 4-> charisma++;
            }
            remainedBonusPoints--;
        }
    }

    public int getCharisma() {
        return charisma;
    }

    public int getBasicAttack() {
        return basicAttack;
    }

    public int getActualHP() {
        return actualHP;
    }

    public boolean isHaveShield() {
        return haveShield;
    }
    public void hurt(int damageAmount){
        if (this.actualHP > 0)
            this.actualHP = this.actualHP - damageAmount;
    }
    public void healUpWithXpLoosing(){
        int tmpLoosingOfPoints = Math.round(new Random().nextFloat()*10);
        System.out.println(String.format("You lost %s experience points",tmpLoosingOfPoints));
        xp = Math.max(xp - tmpLoosingOfPoints, 0);
        actualHP = this.basicHp * (this.constitution * 5);
    }
    public void healUp(){
        int tmpHealing = Math.round(new Random().nextFloat()*20);
        actualHP = Math.max(actualHP + tmpHealing, Math.round(this.basicHp * (this.constitution * 2.5f)));
        System.out.println("HP remained: " + actualHP);
    }
}
