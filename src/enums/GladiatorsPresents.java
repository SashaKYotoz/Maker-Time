package enums;

public enum GladiatorsPresents {
    MARCUS(Class.TANK,5,4,0,1,0,1,7,20,true),
    AURELIUS (Class.WARRIOR,5,5,0,3,0,3,8,15,false),
    DECIMUS(Class.SHOOTER,5,2,0,5,0,5,10,10,false);
    GladiatorsPresents(Class gClass, int constitution, int strength,
                       int xp, int dexterity,
                       int level, int charisma,
                       int basicAttack, int basicHp,
                       boolean haveShield){
    }
}
