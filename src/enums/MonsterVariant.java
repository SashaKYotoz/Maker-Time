package enums;

public enum MonsterVariant {
    DARL_OGR(Class.TANK,6,4,3,2,4,9,35,true),
    EVIL_WARRIOR(Class.WARRIOR, 5, 6, 2, 3, 5, 8, 30, false),
    DARK_ELF(Class.SHOOTER, 3, 8, 3, 2, 7, 8, 15, false),
    EVIL_GLADIATOR(Class.TANK, 6, 6, 1, 4, 4, 9, 30, true),
    DARK_ORK(Class.WARRIOR, 5, 7, 2, 1, 2, 7, 20, false);
    private int constitution;
    private int strength;
    private int xp;
    private int actualHP;
    private int dexterity;
    private int level;
    private int charisma;
    private final int basicAttack;
    private final int basicHp;
    private final boolean haveShield;

    MonsterVariant(Class mClass, int constitution, int strength,
                   int dexterity, int level,
                   int charisma, int basicAttack,
                   int basicHp, boolean haveShield) {
        this.constitution = constitution;
        this.strength = strength;
        this.dexterity = dexterity;
        this.level = level;
        this.charisma = charisma;
        this.basicAttack = basicAttack;
        this.basicHp = basicHp;
        this.haveShield = haveShield;
        this.actualHP = this.basicHp * this.constitution * 2;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getBasicAttack() {
        return basicAttack;
    }

    public int getActualHP() {
        return actualHP;
    }

    public int getBasicHp() {
        return basicHp;
    }

    public boolean isHaveShield() {
        return haveShield;
    }
    public void hurt(int damageAmount){
        if (this.actualHP > 0)
            this.actualHP = this.actualHP - damageAmount;
    }
}