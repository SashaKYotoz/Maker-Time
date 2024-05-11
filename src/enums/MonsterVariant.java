package enums;

public enum MonsterVariant {
    EVIL_WARRIOR(Class.WARRIOR,5,5,2,3,5,6,20,false),
    DARK_ELF(Class.SHOOTER,3,6,3,2,7,7,12,false),
    EVIL_GLADIATOR(Class.TANK,6,4,1,4,4,7,20,true),
    DARK_ORK(Class.WARRIOR,5,5,2,1,2,6,16,false);
    MonsterVariant(Class mClass,int constitution, int strength,
                   int dexterity, int level,
                   int charisma, int basicAttack,
                   int basicHp, boolean haveShield){
    }
}