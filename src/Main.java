import com.google.gson.*;
import enums.Class;
import enums.GladiatorsPresents;
import enums.MonsterVariant;
import objects.Gladiator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static MonsterVariant monster;

    public static void main(String[] args) {
        String characterName;
        Gladiator gladiator = null;
        boolean keepGoing = true;
        fineTextOutput(String.format("Hello dear, %s \n", characterName = scanner.nextLine()));
        fineTextOutput("Would you like to choose character create new one or load from save? \n");
        while (keepGoing) {
            System.out.println("write 'new','template' or 'load'");
            switch (scanner.nextLine()) {
                case "new" -> {
                    if (loadFromFile(characterName) != null) {
                        fineTextOutput("Save found, do you want to delete it?\n");
                        System.out.println("Write 'save' to keep it else 'new' to delete");
                        switch (scanner.nextLine()) {
                            case "save" -> {
                                continue;
                            }
                            case "delete" -> updateProfile(characterName);
                        }
                    }
                    int randomClass = random.nextInt(Class.values().length);
                    Class characterClass = Class.values()[randomClass];
                    switch (characterClass) {
                        case SHOOTER -> gladiator = new Gladiator(characterName,
                                1 + randNumber(0, 3),
                                2 + random.nextInt(0, 3),
                                0, 3 + random.nextInt(0, 5),
                                0, 2 + random.nextInt(0, 4),
                                6 + random.nextInt(0, 7), 10 * randNumber(1, 7), false);
                        case WARRIOR -> gladiator = new Gladiator(characterName,
                                3 + randNumber(0, 4),
                                2 + random.nextInt(0, 3),
                                0, 1 + random.nextInt(0, 5),
                                0, 1 + random.nextInt(0, 4),
                                6 + random.nextInt(0, 7), 10 * randNumber(1, 7), false);
                        case TANK -> gladiator = new Gladiator(characterName,
                                5 + randNumber(0, 5),
                                1 + random.nextInt(0, 3),
                                0, 1 + random.nextInt(0, 5),
                                0, 1 + random.nextInt(0, 4),
                                5 + random.nextInt(0, 5), 10 * randNumber(1, 7), true);
                    }
                    keepGoing = false;
                }
                case "template" -> {
                    fineTextOutput(String.format("Choose hero from template: \n %s \n %s \n %s \n write: 1, 2 or 3 \n",
                            GladiatorsPresents.DECIMUS,
                            GladiatorsPresents.MARCUS,
                            GladiatorsPresents.AURELIUS));
                    GladiatorsPresents selectedPresent = GladiatorsPresents.values()[scanner.nextInt() - 1];
                    gladiator = new Gladiator(selectedPresent.name(),
                            selectedPresent.getConstitution(), selectedPresent.getStrength(),
                            0, selectedPresent.getDexterity(),
                            0, selectedPresent.getCharisma(),
                            selectedPresent.getBasicAttack(), selectedPresent.getBasicHp(), selectedPresent.isHaveShield());
                    keepGoing = false;
                }
                case "load" -> {
                    Gladiator loadedGladiator = loadFromFile(characterName);
                    if (loadedGladiator != null) {
                        gladiator = loadedGladiator;
                        fineTextOutput(String.format("You've successfully loaded gladiator %s with stats: \n", characterName));
                        fineTextOutput(String.format("Current xp: %s\n", gladiator.getXp()));
                        fineTextOutput(String.format("Current level: %s\n", gladiator.getLevel()));
                        fineTextOutput(String.format("Current strength: %s\n", gladiator.getStrength()));
                        fineTextOutput(String.format("Current charisma: %s\n", gladiator.getCharisma()));
                        fineTextOutput(String.format("Current dexterity: %s\n", gladiator.getDexterity()));
                        fineTextOutput(String.format("Current constitution: %s\n", gladiator.getConstitution()));
                        keepGoing = false;
                    } else {
                        fineTextOutput(String.format("Gladiator with name %s wasn't found \n", characterName));
                    }
                }
                default -> System.out.println("Invalid input. Please write 'new', 'template' or 'save'.");
            }
        }
        fineTextOutput(String.format("\"You chose gladiator '%s'. Present stats of your gladiator are: HP - %s; DP - %s", characterName,
                gladiator.getActualHP(),
                gladiator.getBasicAttack() + gladiator.getStrength() * 3));
        fineTextOutput("\n......\n");
        checkShutDown(gladiator);
        while (true) {
            if (gladiator != null) {
                fineTextOutput("Choose action to perform: \n");
                System.out.println("1.Travel");
                System.out.println("2.Observe your attributes");
                System.out.println("3.Save game and exit");
                switch (scanner.nextInt()) {
                    case 1 -> travel(gladiator);
                    case 2 -> observeAttributes(gladiator);
                    case 3 -> saveGame(gladiator);
                }
            }
        }
    }
    private static void checkShutDown(Gladiator gladiator){
        Runtime.getRuntime().addShutdownHook(new Thread(() -> saveToFile(gladiator)));
    }
    private static void saveGame(Gladiator gladiator) {
        saveToFile(gladiator);
        System.exit(0);
    }

    private static void observeAttributes(Gladiator gladiator) {
        System.out.println("Your current attributes are: ");
        fineTextOutput(String.format("Current xp: %s\n", gladiator.getXp()));
        fineTextOutput(String.format("Current level: %s\n", gladiator.getLevel()));
        fineTextOutput(String.format("Current strength: %s\n", gladiator.getStrength()));
        fineTextOutput(String.format("Current charisma: %s\n", gladiator.getCharisma()));
        fineTextOutput(String.format("Current dexterity: %s\n", gladiator.getDexterity()));
        fineTextOutput(String.format("Current constitution: %s\n", gladiator.getConstitution()));
    }

    private static void travel(Gladiator gladiator) {
        monster = MonsterVariant.values()[random.nextInt(0, MonsterVariant.values().length)];
        if (gladiator.getLevel() > 0) {
            monster.setConstitution(monster.getConstitution() * gladiator.getLevel());
            monster.setCharisma(monster.getCharisma() * gladiator.getLevel());
            monster.setStrength(monster.getStrength() * gladiator.getLevel());
        }
        fineTextOutput(String.format("You've met %s(HP = %s, DP = %s) \n", monster.name(), monster.getActualHP(), monster.getBasicAttack()));
        fineTextOutput("Choose action to perform: \n");
        System.out.println("1.Fight");
        System.out.println("2.Run away");
        System.out.println("3.Swap / Outwit");
        switch (scanner.nextInt()) {
            case 1 -> fight(gladiator);
            case 2 -> gladiator.setXp(Math.max(gladiator.getXp() - 1, 0));
            case 3 -> agree(gladiator);
        }
    }

    private static void fight(Gladiator gladiator) {
        while (monster.getActualHP() >= 0 && gladiator.getActualHP() >= 0) {
            System.out.println("To attack '1' or block '2'?");
            switch (scanner.nextInt()) {
                default -> {
                    try {
                        int damage = randNumber(gladiator.getBasicAttack(), gladiator.getBasicAttack() + gladiator.getStrength() * 3) + 1;
                        int dexterityBonus = gladiator.getDexterity() > 5 ? 5 + gladiator.getDexterity() / 2 : gladiator.getDexterity();
                        if (dexterityBonus / 10f > random.nextFloat()) {
                            damage = damage + 2 * gladiator.getDexterity();
                            fineTextOutput(String.format("Your dexterity blessed you, critical attack with: %s damage points\n", damage));
                        }
                        if (random.nextFloat() < 0.25) {
                            fineTextOutput(monster + " decided to block\n");
                            int tmpDamage = monster.isHaveShield() ? damage / 4 : damage / 2;
                            monster.hurt(tmpDamage);
                            fineTextOutput("You've dealt " + tmpDamage + "points of damage \n");
                        } else {
                            monster.hurt(damage);
                            fineTextOutput("You've dealt " + damage + "points of damage \n");
                        }
                        System.out.println(monster.name() + " HP remained - " + monster.getActualHP());
                        Thread.sleep(1000);
                        damage = randNumber(monster.getBasicAttack(), monster.getBasicAttack() + monster.getStrength() * 2) + 1;
                        if (monster.getDexterity() / 10f > random.nextFloat()) {
                            damage = damage + 2 + monster.getDexterity();
                            fineTextOutput(String.format("%s's dexterity blessed its, critical attack with: %s damage points\n", monster, damage));
                        }
                        gladiator.hurt(damage);
                        System.out.println(gladiator.getName() + " HP remained - " + gladiator.getActualHP());
                        if (monster.getActualHP() <= 0) {
                            fineTextOutput("You've won \n");
                            int tmpXpReward = (int) Math.round(Math.sqrt(monster.getBasicHp() * monster.getBasicAttack() + monster.getConstitution()));
                            int xpReward = gladiator.getXp() + tmpXpReward;
                            gladiator.setXp(xpReward);
                            fineTextOutput("You've earned " + tmpXpReward + " experience\n");
                            if (random.nextBoolean())
                                gladiator.healUp();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                case 2 -> {
                    int damage = randNumber(monster.getBasicAttack(), monster.getBasicAttack() + monster.getStrength() * 2) + 1;
                    int tmpDamage = gladiator.isHaveShield() ? damage / 4 : damage / 2;
                    fineTextOutput(String.format("Damage dealt to you with block: %s \n", tmpDamage));
                    gladiator.hurt(tmpDamage);
                    System.out.println(gladiator.getName() + " HP remained - " + gladiator.getActualHP());
                    monster.hurt(tmpDamage / 2);
                    fineTextOutput(String.format("And dealt %s points of damage \n", tmpDamage / 2));
                }
            }
        }
        if (gladiator.getActualHP() <= 0) {
            fineTextOutput("You've run out of HP\n");
            System.out.println("You were defeated by " + monster + "\n");
            fineTextOutput("You want to rebirth '1' or end the game '2'?\n");
            switch (scanner.nextInt()) {
                default -> {
                    monster = MonsterVariant.values()[random.nextInt(0, MonsterVariant.values().length)];
                    gladiator.healUpWithXpLoosing();
                }
                case 2 -> {
                    System.out.println("You decided to finish the game");
                    saveGame(gladiator);
                }
            }
        }
    }

    private static void agree(Gladiator gladiator) {
        int number = randNumber(0, 100);
        int number1 = randNumber(0, 50) + gladiator.getCharisma();
        if (number1 >= number) {
            System.out.println("You've swayed your enemy. You've got 10xp");
            gladiator.setXp(gladiator.getXp() + 10);
            gladiator.healUp();
        } else {
            System.out.println("NOOOO. FIGHT!");
            fight(gladiator);
        }
    }

    private static void updateProfile(String characterName) {
        try {
            JsonObject mainObject;
            Path configDir = Paths.get("src", "data");
            Path CONTROLLERS_PATH = configDir.resolve("data.json");
            if (Files.exists(CONTROLLERS_PATH)) {
                String jsonString = new String(Files.readAllBytes(CONTROLLERS_PATH));
                mainObject = JsonParser.parseString(jsonString).getAsJsonObject();
            } else {
                mainObject = new JsonObject();
                mainObject.add("entries", new JsonArray());
            }
            JsonArray entriesArray = mainObject.getAsJsonArray("entries");
            for (JsonElement entry : entriesArray) {
                JsonObject entryObject = entry.getAsJsonObject();
                if (entryObject.get("gladiatorName").getAsString().equals(characterName)) {
                    entriesArray.remove(entry);
                    Files.write(CONTROLLERS_PATH, mainObject.toString().getBytes());
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("Failed in removing gladiator");
        }
    }

    private static void saveToFile(Gladiator gladiator) {
        try {
            Path configDir = Paths.get("src", "data");
            if (!Files.exists(configDir))
                Files.createDirectories(configDir);
            Path CONTROLLERS_PATH = configDir.resolve("data.json");
            JsonObject mainObject;
            if (Files.exists(CONTROLLERS_PATH)) {
                String jsonString = new String(Files.readAllBytes(CONTROLLERS_PATH));
                mainObject = JsonParser.parseString(jsonString).getAsJsonObject();
            } else {
                mainObject = new JsonObject();
                mainObject.add("entries", new JsonArray());
            }

            JsonArray entriesArray = mainObject.getAsJsonArray("entries");
            boolean found = false;
            for (JsonElement entry : entriesArray) {
                JsonObject entryObject = entry.getAsJsonObject();
                if (entryObject.get("gladiatorName").getAsString().equals(gladiator.getName())) {
                    entryObject.addProperty("dexterity", gladiator.getDexterity());
                    entryObject.addProperty("strength", gladiator.getStrength());
                    entryObject.addProperty("basicAttack", gladiator.getBasicAttack());
                    entryObject.addProperty("charisma", gladiator.getCharisma());
                    entryObject.addProperty("constitution", gladiator.getConstitution());
                    entryObject.addProperty("basicHp", gladiator.getBasicHp());
                    entryObject.addProperty("currentXP", gladiator.getXp());
                    entryObject.addProperty("level", gladiator.getLevel());
                    entryObject.addProperty("hasShield", gladiator.isHaveShield());
                    found = true;
                    break;
                }
            }

            if (!found) {
                JsonObject newEntry = new JsonObject();
                newEntry.addProperty("gladiatorName", gladiator.getName());
                newEntry.addProperty("dexterity", gladiator.getDexterity());
                newEntry.addProperty("strength", gladiator.getStrength());
                newEntry.addProperty("basicAttack", gladiator.getBasicAttack());
                newEntry.addProperty("charisma", gladiator.getCharisma());
                newEntry.addProperty("constitution", gladiator.getConstitution());
                newEntry.addProperty("basicHp", gladiator.getBasicHp());
                newEntry.addProperty("currentXP", gladiator.getXp());
                newEntry.addProperty("level", gladiator.getLevel());
                newEntry.addProperty("hasShield", gladiator.isHaveShield());
                entriesArray.add(newEntry);
            }

            Files.write(CONTROLLERS_PATH, new Gson().toJson(mainObject).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Gladiator loadFromFile(String gladiatorName) {
        try {
            Path configDir = Paths.get("src", "data");
            Path CONTROLLERS_PATH = configDir.resolve("data.json");
            JsonObject mainObject;
            if (Files.exists(CONTROLLERS_PATH)) {
                String jsonString = new String(Files.readAllBytes(CONTROLLERS_PATH));
                mainObject = JsonParser.parseString(jsonString).getAsJsonObject();
            } else {
                mainObject = new JsonObject();
                mainObject.add("entries", new JsonArray());
            }
            JsonArray entriesArray = mainObject.getAsJsonArray("entries");
            for (JsonElement entry : entriesArray) {
                JsonObject entryObject = entry.getAsJsonObject();
                if (entryObject.get("gladiatorName").getAsString().equals(gladiatorName)) {
                    return new Gladiator(gladiatorName,
                            entryObject.get("constitution").getAsInt(),
                            entryObject.get("strength").getAsInt(),
                            entryObject.get("currentXP").getAsInt(),
                            entryObject.get("dexterity").getAsInt(),
                            entryObject.get("level").getAsInt(),
                            entryObject.get("charisma").getAsInt(),
                            entryObject.get("basicAttack").getAsInt(),
                            entryObject.get("basicHp").getAsInt(),
                            entryObject.get("hasShield").getAsBoolean());
                }
            }
        } catch (IOException e) {
            System.out.println("Failed in loading gladiator");
        }
        return null;
    }

    private static void fineTextOutput(String s) {
        try {
            for (int i = 0; i < s.length(); i++) {
                System.out.print(s.charAt(i));
                Thread.sleep(75);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int randNumber(int min, int max) {
        int random = new Random().nextInt(max - min) + min;
        System.out.println(random);
        return Math.abs(random) > 10000 ? randNumber(min, max) : random;
    }
}