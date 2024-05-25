import enums.Class;
import enums.GladiatorsPresents;
import enums.MonsterVariant;
import objects.Gladiator;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static MonsterVariant monster;

    public static void main(String[] args) {
        String characterName;
        Gladiator gladiator = null;
        fineTextOutput(String.format("Hello dear, %s \n", characterName = scanner.nextLine()));
        fineTextOutput("Would you like to choose character or create new one? \n");
        System.out.println("write 'new' or 'template'");
        switch (scanner.nextLine()) {
            case "new" -> {
                int randomClass = random.nextInt(0, 3);
                Class characterClass = Class.values()[randomClass > Class.values().length ? 0 : randomClass];
                switch (characterClass) {
                    case SHOOTER -> gladiator = new Gladiator(characterName,
                            1 + random.nextInt(),
                            2 + random.nextInt(0, 3),
                            0, 3 + random.nextInt(0, 5),
                            0, 2 + random.nextInt(0, 4),
                            6 + random.nextInt(0, 7), 10 * randNumber(1, 7), false);
                    case WARRIOR -> gladiator = new Gladiator(characterName,
                            3 + random.nextInt(0, 3),
                            2 + random.nextInt(0, 3),
                            0, 1 + random.nextInt(0, 5),
                            0, 1 + random.nextInt(0, 4),
                            6 + random.nextInt(0, 7), 10 * randNumber(1, 7), false);
                    case TANK -> gladiator = new Gladiator(characterName,
                            5 + random.nextInt(0, 4),
                            1 + random.nextInt(0, 3),
                            0, 1 + random.nextInt(0, 5),
                            0, 1 + random.nextInt(0, 4),
                            5 + random.nextInt(0, 5), 10 * randNumber(1, 7), true);
                }
            }
            default -> {
                fineTextOutput(String.format("Choose hero from template: \n %s \n %s \n %s \n write: 1, 2 or 3 \n", GladiatorsPresents.DECIMUS, GladiatorsPresents.MARCUS, GladiatorsPresents.AURELIUS));
                GladiatorsPresents selectedPresent = GladiatorsPresents.values()[scanner.nextInt() - 1];
                gladiator = new Gladiator(selectedPresent.name(), selectedPresent.getConstitution(), selectedPresent.getStrength(), 0, selectedPresent.getDexterity(), 0, selectedPresent.getCharisma(), selectedPresent.getBasicAttack(), selectedPresent.getBasicHp(), selectedPresent.isHaveShield());
            }
        }
        System.out.println(gladiator.getBasicHp());
        System.out.println(gladiator.getConstitution());
        fineTextOutput(String.format("\"You chose gladiator '%s'. Present stats of your gladiator are: HP - %s; DP - %s",characterName,gladiator.getActualHP(),gladiator.getBasicAttack() + gladiator.getStrength()*3));
        fineTextOutput("\n......\n");
        while (true) {
            if (gladiator != null) {
                fineTextOutput("Choose action to perform: \n");
                System.out.println("1.Travel");
                System.out.println("2.Observe your attributes");
                System.out.println("3.Save game and exit");
                switch (scanner.nextInt()){
                    case 1 -> travel(gladiator);
                    case 2 -> observeAttributes(gladiator);
                    case 3 -> saveGame();
                }
            }
        }
    }
    private static void saveGame(){
        System.exit(0);
    }
    private static void observeAttributes(Gladiator gladiator){
        System.out.println("Your current attributes are: ");
        fineTextOutput(String.format("Current xp: %s\n",gladiator.getXp()));
        fineTextOutput(String.format("Current level: %s\n",gladiator.getLevel()));
        fineTextOutput(String.format("Current strength: %s\n",gladiator.getStrength()));
        fineTextOutput(String.format("Current charisma: %s\n",gladiator.getCharisma()));
        fineTextOutput(String.format("Current dexterity: %s\n",gladiator.getDexterity()));
        fineTextOutput(String.format("Current constitution: %s\n",gladiator.getConstitution()));
    }
    private static void travel(Gladiator gladiator){
        monster = MonsterVariant.values()[random.nextInt(0,MonsterVariant.values().length)];
        if (gladiator.getLevel() > 0){
            monster.setConstitution(monster.getConstitution() * gladiator.getLevel());
            monster.setCharisma(monster.getCharisma() * gladiator.getLevel());
            monster.setStrength(monster.getStrength() * gladiator.getLevel());
        }
        fineTextOutput(String.format("You've met %s(HP = %s, DP = %s) \n",monster.name(),monster.getActualHP(),monster.getBasicAttack()));
        fineTextOutput("Choose action to perform: \n");
        System.out.println("1.Fight");
        System.out.println("2.Run away");
        System.out.println("3.Swap / Outwit");
        switch (scanner.nextInt()){
            case 1 -> fight(gladiator);
            case 2 -> gladiator.setXp(Math.max(gladiator.getXp() - 1, 0));
            case 3 -> agree(gladiator);
        }
    }
    private static void fight(Gladiator gladiator){
        while (monster.getActualHP() > 0){
            System.out.println("To attack '1' or block '2'?");
            switch (scanner.nextInt()){
                default -> {
                    try{
                        int damage = randNumber(gladiator.getBasicAttack(), gladiator.getBasicAttack() + gladiator.getStrength()*3) + 1;
                        if (new Random().nextBoolean()){
                            fineTextOutput(monster + " decided to block\n");
                            int tmpDamage = monster.isHaveShield() ? damage/4 : damage/2;
                            monster.hurt(tmpDamage);
                            fineTextOutput("You dealt " + tmpDamage + "points of damage \n");
                        }else {
                            monster.hurt(damage);
                            fineTextOutput("You dealt " + damage + "points of damage \n");
                        }
                        System.out.println(monster.name() + " HP remained - " + monster.getActualHP());
                        Thread.sleep(1000);
                        damage = randNumber(monster.getBasicAttack(),monster.getBasicAttack() + monster.getStrength()*2) + 1;
                        gladiator.hurt(damage);
                        System.out.println(gladiator.getName() + " HP remained - " + gladiator.getActualHP());
                        if (monster.getActualHP() <= 0){
                            fineTextOutput("You won \n");
                            int tmpXpReward = (int) Math.round(Math.sqrt(monster.getBasicHp()*monster.getBasicAttack() + monster.getConstitution()));
                            int xpReward = gladiator.getXp() + tmpXpReward;
                            gladiator.setXp(xpReward);
                            fineTextOutput("You earned " + tmpXpReward + " experience\n");
                            if (new Random().nextBoolean())
                                gladiator.healUp();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                case 2 ->{
                    int damage = randNumber(monster.getBasicAttack(),monster.getBasicAttack() + monster.getStrength()*2) + 1;
                    int tmpDamage = gladiator.isHaveShield() ? damage/4 : damage/2;
                    fineTextOutput(String.format("Damage dealt to you with block: %s \n",tmpDamage));
                    gladiator.hurt(tmpDamage);
                    System.out.println(gladiator.getName() + " HP remained - " + gladiator.getActualHP());
                    monster.hurt(tmpDamage/2);
                    fineTextOutput(String.format("And dealt %s points of damage \n",tmpDamage/2));
                }
            }
        }
        if (gladiator.getActualHP() < 0){
            fineTextOutput("You ran out of HP\n");
            System.out.println("You were defeated by " + monster + "\n");
            fineTextOutput("You want to rebirth '1' or end the game '2'?\n");
            switch (scanner.nextInt()){
                default -> {
                    monster = MonsterVariant.values()[random.nextInt(0,MonsterVariant.values().length)];
                    gladiator.healUpWithXpLoosing();
                }
                case 2 -> {
                    System.out.println("You decided to finish the game");
                    System.exit(0);
                }
            }
        }
    }
    private static void agree(Gladiator gladiator){
        int number = randNumber(0, 100);
        int number1 = randNumber(0, 50) + gladiator.getCharisma();
        if (number1 >= number) {
            System.out.println("You win. You get 10xp");
            gladiator.setXp(gladiator.getXp() + 10);
        } else {
            System.out.println("NOOOO. FIGHT!");
            fight(gladiator);
        }
    }
    private static void fineTextOutput(String s) {
        try {
            for (int i = 0; i < s.length(); i++) {
                System.out.print(s.charAt(i));
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static int randNumber(int min, int max) {
        int random = new Random().nextInt(max - min) + min;
        System.out.println(random);
        return Math.abs(random) > 1000 ? randNumber(min,max) : random;
    }
}