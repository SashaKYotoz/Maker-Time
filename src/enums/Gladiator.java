package enums;

public record Gladiator(String uniqueId,int constitution, int strength,
                        int xp, int dexterity,
                        int level, int charisma,
                        int basicAttack, int basicHp,
                        boolean haveShield) {
}
