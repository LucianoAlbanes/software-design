package e5;

public class CharacterExample {
    public static void main(String[] args) {
        // CREATORS
        CharacterCreator[] creators = {
            new WarriorCreator(),
            new MageCreator(),
            new ArcherCreator()
        };

        // TEST (With for each, to test inheritance)
        for (CharacterCreator creator : creators) {
            creator.createCharacter().showStats();
        }
    }
}


// Product

abstract class GameCharacter {
    protected String name;
    protected int health;
    protected int attack;
    protected int defense;
    protected String weapon;

    public void showStats() {
        System.out.println(name + " | HP: " + health + " | ATK: " + attack
            + " | DEF: " + defense + " | Weapon: " + weapon);
    }
}

class Warrior extends GameCharacter {
    Warrior() {
        name = "Warrior";
        health = 150;
        attack = 20;
        defense = 15;
        weapon = "Sword";
    }
}

class Mage extends GameCharacter {
    Mage() {
        name = "Mage";
        health = 80;
        attack = 30;
        defense = 5;
        weapon = "Staff";
    }
}

class Archer extends GameCharacter {
    Archer() {
        name = "Archer";
        health = 100;
        attack = 25;
        defense = 8;
        weapon = "Bow";
    }
}

// Creators

abstract class CharacterCreator {
    public abstract GameCharacter createCharacter();
}

class WarriorCreator extends CharacterCreator {
    @Override
    public GameCharacter createCharacter() {
        return new Warrior();
    }
}

class MageCreator extends CharacterCreator {
    @Override
    public GameCharacter createCharacter() {
        return new Mage();
    }
}

class ArcherCreator extends CharacterCreator {
    @Override
    public GameCharacter createCharacter() {
        return new Archer();
    }
}
