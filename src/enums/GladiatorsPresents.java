package enums;

public enum GladiatorsPresents {
    MARCUS(Class.TANK, 5, 4, 1, 1, 7, 20, true),
    AURELIUS(Class.WARRIOR, 5, 5, 3, 3, 8, 15, false),
    DECIMUS(Class.SHOOTER, 5, 2, 5, 5, 10, 10, false);

    private final Class gClass;
    private final int constitution;
    private final int strength;
    private final int dexterity;
    private final int charisma;
    private final int basicAttack;
    private final int basicHp;
    private final boolean haveShield;

    GladiatorsPresents(Class gClass, int constitution, int strength,
                       int dexterity,
                       int charisma,
                       int basicAttack, int basicHp,
                       boolean haveShield) {
        this.gClass = gClass;
        this.constitution = constitution;
        this.strength = strength;
        this.dexterity = dexterity;
        this.charisma = charisma;
        this.basicAttack = basicAttack;
        this.basicHp = basicHp;
        this.haveShield = haveShield;
    }

    public Class getGClass() {
        return gClass;
    }

    public int getConstitution() {
        return constitution;
    }

    public int getStrength() {
        return strength;
    }


    public int getDexterity() {
        return dexterity;
    }


    public int getCharisma() {
        return charisma;
    }

    public int getBasicAttack() {
        return basicAttack;
    }

    public int getBasicHp() {
        return basicHp;
    }

    public boolean isHaveShield() {
        return haveShield;
    }
}
